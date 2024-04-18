package com.example.prodjectformc.viewmodels

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.prodjectformc.api.PostsRepository
import com.example.prodjectformc.api.Result
import kotlinx.coroutines.launch


class MainViewModel (private val repository: PostsRepository) : ViewModel() {

    private val _state = mutableStateOf(PostsState())
    val state: State<PostsState> = _state

    fun sendCodeToEmail(email:String) {
        viewModelScope.launch {
            repository.sendCodeEmail(email).collect{ result ->
                when(result) {
                    is Result.Error -> {
                        _state.value = PostsState(posts = null, loading = false, error = result.message)
                    }
                    is Result.Success -> {
                        _state.value = PostsState(posts = result.data!!, loading = false, error = null)
                    }
                }
            }
        }
    }

    fun checkCodeEmail(email: String, code: String) {
        viewModelScope.launch {
            repository.checkCodeEmail(email, code).collect { result ->
                when(result) {
                    is Result.Error -> {
                        _state.value = PostsState(posts = null, loading = false, error = result.message)
                    }
                    is Result.Success -> {
                        _state.value = PostsState(posts = result.data!!, loading = false, error = null)
                        Log.d("token?", _state.value.posts.toString())
                    }
                }
            }
        }
    }
}