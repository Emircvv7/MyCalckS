package com.example.mycalck.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalck.character.CharacterZ
import com.example.mycalck.data.RickAndMortyApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val retrofit: Retrofit
) : ViewModel() {
    private val _characters = MutableLiveData<List<CharacterZ>>()
    val characters: LiveData<List<CharacterZ>> get() = _characters

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            val api = retrofit.create(RickAndMortyApiService::class.java)

            try {
                val response = withContext(Dispatchers.IO) { api.getCharacters() }
                _characters.value = response.results
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
