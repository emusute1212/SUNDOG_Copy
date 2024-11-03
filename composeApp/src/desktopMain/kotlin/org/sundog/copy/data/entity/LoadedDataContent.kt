package org.sundog.copy.data.entity

data class LoadedDataContent(
    val fromLoad: FromLoad,
    val copyContent: List<CopyContent>,
)

enum class FromLoad {
    BACKUP_FILE,
    SAVED_FILE,
}