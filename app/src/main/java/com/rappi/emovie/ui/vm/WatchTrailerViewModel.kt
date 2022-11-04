package com.rappi.emovie.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.model.Video
import com.rappi.emovie.domain.uc.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class WatchTrailerViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
    private val gson: Gson
): ViewModel()  {

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel> get() = _model

    sealed class UiModel{
        class Loading(val value: Boolean) : UiModel()
        class MovieData(val data: Movie) : UiModel()
        class VideoData(val data: Video?) : UiModel()
    }

    fun onCreate(movieData: String){
        getMovieData(movieData)
    }

    private fun getMovieData(movieData: String) = viewModelScope.launch(Dispatchers.Main){
        val data: Movie = gson.fromJson(movieData, Movie::class.java)
        _model.value = UiModel.Loading(true)
        _model.value = UiModel.MovieData(data)
        getVideos(data.id)
    }

    private fun getVideos(id: Int) = viewModelScope.launch(Dispatchers.IO){
        val result = getVideosUseCase(id)
        withContext(Dispatchers.Main) {
            _model.value = UiModel.Loading(false)
            _model.value = UiModel.VideoData(result)
        }
    }

}