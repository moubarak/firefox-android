/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.mozilla.fenix.components.toolbar

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import org.mozilla.fenix.components.toolbar.InferenceModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class ChatViewModel(
    private val inferenceModel: InferenceModel,
) : ViewModel() {

    private val _uiState = MutableLiveData(GemmaUiState())
    val uiState: LiveData<GemmaUiState> get() = _uiState

    fun summarize(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val fullPrompt = _uiState.value?.fullPrompt
                inferenceModel.generateResponseAsync(fullPrompt.toString() + " $url")
                inferenceModel.partialResults
                    .collect { (partialResult) ->
                        _uiState.postValue(GemmaUiState(summary = uiState.value?.summary + partialResult))
                    }
            } catch (e: Exception) {
                _uiState.postValue(GemmaUiState(e.localizedMessage ?: "Unknown Error"))
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getFactory(context: Context) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val inferenceModel = InferenceModel.getInstance(context)
                return ChatViewModel(inferenceModel) as T
            }
        }
    }
}
