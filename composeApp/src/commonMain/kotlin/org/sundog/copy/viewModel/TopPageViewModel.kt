package org.sundog.copy.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.sundog.copy.data.entity.LoadedDataContent
import org.sundog.copy.data.repository.CopyContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TopPageViewModel(
    private val copyContentRepository: CopyContentRepository,
) : ViewModel() {
    private val _loadedDataContent = MutableStateFlow<LoadedDataContent?>(value = null)
    val loadedDataContent: StateFlow<LoadedDataContent?> = _loadedDataContent.asStateFlow()

    fun initialize() {
        viewModelScope.launch(
            Dispatchers.IO
        ) {
            _loadedDataContent.value = copyContentRepository.loadCopyContent()
        }
    }
}