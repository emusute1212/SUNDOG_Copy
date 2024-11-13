package org.sundog.copy.viewModel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.sundog.copy.data.entity.CopyContent
import org.sundog.copy.data.repository.CopyContentRepository

class SettingPageViewModel(
    private val copyContentRepository: CopyContentRepository,
) : ViewModel() {
    private val _currentCopyContents = MutableStateFlow<List<CopyContent>>(value = emptyList())
    val currentCopyContents: StateFlow<List<CopyContent>> = _currentCopyContents.asStateFlow()

    fun initialize(
        currentCopyContents: List<CopyContent>,
    ) {
        _currentCopyContents.value = currentCopyContents
    }

    fun onAddCopyContent() {
        _currentCopyContents.value += CopyContent(
            label = "",
            copyText = ""
        )
    }

    fun onChangeCopyContent(
        targetIndex: Int,
        targetCopyContent: CopyContent,
    ) {
        _currentCopyContents.value = _currentCopyContents.value.mapIndexed { index, copyContent ->
            return@mapIndexed if (index == targetIndex) {
                targetCopyContent
            } else {
                copyContent
            }
        }
    }

    fun onDeleteCopyContent(copyContent: CopyContent) {
        _currentCopyContents.value -= copyContent
    }

    fun onSaveCopyContents() {
        copyContentRepository.saveCopyContent(_currentCopyContents.value)
    }
}