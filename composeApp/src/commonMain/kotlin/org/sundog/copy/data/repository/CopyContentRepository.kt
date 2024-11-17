package org.sundog.copy.data.repository

import androidx.annotation.WorkerThread
import androidx.compose.ui.util.fastForEach
import okio.use
import org.sundog.copy.data.entity.CopyContent
import org.sundog.copy.data.entity.FromLoad
import org.sundog.copy.data.entity.LoadedDataContent
import org.sundog.copy.extensions.child
import java.io.File

interface CopyContentRepository {
    @WorkerThread
    fun loadCopyContent(): LoadedDataContent

    @WorkerThread
    fun saveCopyContent(copyContents: List<CopyContent>)
}

class CopyContentRepositoryImpl : CopyContentRepository {
    override fun loadCopyContent(): LoadedDataContent {
        val targetFile = File(DIRECTORY_NAME)
            .child(COPY_CONTENT_FILE_NAME)
        return try {
            LoadedDataContent(
                fromLoad = FromLoad.SAVED_FILE,
                copyContent = targetFile.loadCopyContent()
            )
        } catch (e: Exception) {
            LoadedDataContent(
                fromLoad = FromLoad.BACKUP_FILE,
                copyContent = loadBackupFile()
            )
        }
    }

    override fun saveCopyContent(copyContents: List<CopyContent>) {
        val targetFile = File(DIRECTORY_NAME)
            .child(COPY_CONTENT_FILE_NAME)
        saveBackupFile(copyContents)
        targetFile.saveCopyContent(copyContents)
    }

    private fun loadBackupFile(): List<CopyContent> {
        val targetFile = File(BACKUP_DIRECTORY_NAME)
            .child(COPY_CONTENT_FILE_NAME)
        if (targetFile.exists().not()) {
            return getInitialCopyContent().also {
                saveCopyContent(it)
            }
        }
        return targetFile.loadCopyContent()
    }

    private fun File.loadCopyContent(): List<CopyContent> {
        return bufferedReader().use { reader ->
            reader.readLines().joinToString("\n") { it }.toCopyContents()
        }
    }

    private fun saveBackupFile(copyContents: List<CopyContent>) {
        val targetFile = File(BACKUP_DIRECTORY_NAME)
            .child(COPY_CONTENT_FILE_NAME)
        targetFile.saveCopyContent(copyContents)
    }

    private fun File.saveCopyContent(copyContents: List<CopyContent>) {
        parentFile.mkdirs()
        bufferedWriter().use { writer ->
            writer.write(copyContents.toCsvText())
        }
    }

    private fun List<CopyContent>.toCsvText(): String {
        return joinToString("\n") {
            "\"${it.label}\",\"${it.copyText}\""
        }
    }

    private fun String.toCopyContents(): List<CopyContent> {
        return splitByLineDelimiter().map { line ->
            """(?:^|,)(?:"((?:[^"]|"")*)"|([^",\n]*))""".toRegex()
                .findAll(line)
                .map { matchResult ->
                    matchResult.groups[1]?.value?.replace("\"\"", "\"")
                        ?: matchResult.groups[2]?.value.orEmpty()
                }
                .toList()
                .let { (label, copyText) ->
                    CopyContent(
                        label = label,
                        copyText = copyText,
                    )
                }
        }
    }

    private fun String.splitByLineDelimiter(): List<String> {
        val adjustLines = mutableListOf<String>()
        var temporaryStringBuilder = StringBuilder()
        split("\n").fastForEach { line ->
            temporaryStringBuilder.append(line)
            if (line.startsWith("\"") && line.endsWith("\"")) {
                adjustLines += temporaryStringBuilder.toString()
                temporaryStringBuilder = StringBuilder()
            } else if (line.endsWith("\"")) {
                adjustLines += temporaryStringBuilder.toString()
                temporaryStringBuilder = StringBuilder()
            } else {
                temporaryStringBuilder.append("\n")
            }
        }
        return adjustLines
    }

    private fun getInitialCopyContent(): List<CopyContent> {
        return (1..5).map {
            CopyContent(
                label = "ボタン${it}",
                copyText = "コピー${it}",
            )
        }
    }

    companion object {
        private const val BACKUP_DIRECTORY_NAME = "SUNDOG_Copy_Backup"
        private const val DIRECTORY_NAME = "CopyData"
        private const val COPY_CONTENT_FILE_NAME = "copy_content.csv"
    }
}