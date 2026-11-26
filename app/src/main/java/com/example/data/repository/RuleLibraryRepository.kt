package com.example.data.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.example.data.local.dao.RuleLibraryDao
import com.example.data.local.entity.RuleDocumentEntity
import com.example.data.local.entity.RuleSectionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RuleLibraryRepository(
    private val context: Context,
    private val ruleLibraryDao: RuleLibraryDao
) {
    val documents: Flow<List<RuleDocumentEntity>> = ruleLibraryDao.observeDocuments()

    fun searchSections(query: String): Flow<List<RuleSectionEntity>> {
        val trimmed = query.trim()
        return if (trimmed.isEmpty()) {
            ruleLibraryDao.observeRecentSections()
        } else {
            ruleLibraryDao.searchSections(trimmed)
        }
    }

    suspend fun registerPdfDocument(uri: Uri): Long = withContext(Dispatchers.IO) {
        var displayName = "Documento_Cyberpunk_RED.pdf"
        var sizeBytes = 0L

        context.contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            val nameIdx = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            val sizeIdx = cursor.getColumnIndex(OpenableColumns.SIZE)
            if (cursor.moveToFirst()) {
                if (nameIdx >= 0) displayName = cursor.getString(nameIdx) ?: displayName
                if (sizeIdx >= 0) sizeBytes = cursor.getLong(sizeIdx)
            }
        }

        val sizeLabel = if (sizeBytes > 0) {
            val kb = sizeBytes / 1024.0
            if (kb >= 1024) String.format("%.1f MB", kb / 1024.0) else String.format("%.0f KB", kb)
        } else {
            "Tamanho via SAF"
        }

        val docId = ruleLibraryDao.insertDocument(
            RuleDocumentEntity(
                title = displayName,
                uriString = uri.toString(),
                fileSizeLabel = sizeLabel,
                indexedStatus = "PDF vinculado • Pronto para extração/anotação"
            )
        )

        ruleLibraryDao.insertSections(
            listOf(
                RuleSectionEntity(
                    documentId = docId,
                    sectionTitle = "Documento Importado: $displayName",
                    pageRef = "Índice Inicial",
                    contentText = "Arquivo oficial vinculado via Storage Access Framework ($sizeLabel). " +
                        "Você pode adicionar notas e trechos oficiais deste PDF abaixo para consulta rápida e contexto de sessão."
                )
            )
        )
        docId
    }

    suspend fun addRuleExcerpt(
        documentId: Long,
        sectionTitle: String,
        pageRef: String,
        contentText: String
    ) {
        ruleLibraryDao.insertSections(
            listOf(
                RuleSectionEntity(
                    documentId = documentId,
                    sectionTitle = sectionTitle.trim(),
                    pageRef = pageRef.trim().ifEmpty { "p. —" },
                    contentText = contentText.trim()
                )
            )
        )
    }

    suspend fun deleteDocument(docId: Long) {
        ruleLibraryDao.deleteDocumentById(docId)
    }
}
