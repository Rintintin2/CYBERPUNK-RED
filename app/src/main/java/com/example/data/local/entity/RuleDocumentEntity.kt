package com.example.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "rule_documents")
data class RuleDocumentEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val uriString: String,
    val fileSizeLabel: String,
    val indexedStatus: String = "Registrado para indexação PDF",
    val importedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "rule_sections",
    foreignKeys = [
        ForeignKey(
            entity = RuleDocumentEntity::class,
            parentColumns = ["id"],
            childColumns = ["documentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("documentId")]
)
data class RuleSectionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val documentId: Long,
    val sectionTitle: String,
    val pageRef: String,
    val contentText: String
)
