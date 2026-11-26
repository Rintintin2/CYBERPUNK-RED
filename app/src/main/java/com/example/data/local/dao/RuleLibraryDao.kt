package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entity.RuleDocumentEntity
import com.example.data.local.entity.RuleSectionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RuleLibraryDao {
    @Query("SELECT * FROM rule_documents ORDER BY importedAt DESC")
    fun observeDocuments(): Flow<List<RuleDocumentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDocument(doc: RuleDocumentEntity): Long

    @Query("DELETE FROM rule_documents WHERE id = :docId")
    suspend fun deleteDocumentById(docId: Long)

    @Query(
        """
        SELECT * FROM rule_sections
        WHERE sectionTitle LIKE '%' || :query || '%'
           OR contentText LIKE '%' || :query || '%'
        ORDER BY id ASC
        LIMIT 50
        """
    )
    fun searchSections(query: String): Flow<List<RuleSectionEntity>>

    @Query("SELECT * FROM rule_sections ORDER BY documentId ASC, id ASC LIMIT 50")
    fun observeRecentSections(): Flow<List<RuleSectionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSections(sections: List<RuleSectionEntity>)
}
