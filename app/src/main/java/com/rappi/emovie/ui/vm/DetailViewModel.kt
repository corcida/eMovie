package com.rappi.emovie.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rappi.emovie.domain.model.Genre
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetGenresUseCase
import com.rappi.emovie.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val gson: Gson
): ViewModel(){

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel> get() = _model

    sealed class UiModel{
        class Genres(val data: List<Genre>) : UiModel()
        class MovieData(val data: Movie) : UiModel()
        class WatchTrailer(val data: String) : UiModel()
    }

    fun onCreate(movieData: String){
        setLoadingGenres()
        getMovieData(movieData)
    }

    private fun getMovieData(movieData: String){
        val data: Movie = gson.fromJson(movieData, Movie::class.java)
        _model.value = UiModel.MovieData(data)
        getGenres(data.genre_ids)
    }

    private fun setLoadingGenres(){
        val loadingGenres = listOf(Genre(Constants.loading), Genre(Constants.loading),
            Genre(Constants.loading))
        _model.value = UiModel.Genres(loadingGenres)
    }

    private fun getGenres(ids: List<Int>) = viewModelScope.launch(Dispatchers.IO){
        val result = getGenresUseCase(ids)
        withContext(Dispatchers.Main) {
            _model.value = UiModel.Genres(result)
        }
    }

    fun watchTrailer(movie: Movie){
        _model.value = UiModel.WatchTrailer(gson.toJson(movie))
    }

}