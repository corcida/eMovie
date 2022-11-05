package com.rappi.emovie.ui.vm

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetDateFilteredMoviesUseCase
import com.rappi.emovie.domain.uc.GetEnglishMoviesUseCase
import com.rappi.emovie.domain.uc.GetTopRatedMoviesUseCase
import com.rappi.emovie.domain.uc.GetUpcomingMoviesUseCase
import com.rappi.emovie.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase,
    private val getEnglishMoviesUseCase: GetEnglishMoviesUseCase,
    private val getDateFilteredMoviesUseCase: GetDateFilteredMoviesUseCase,
    private val gson: Gson
): ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel> get() = _model

    sealed class UiModel{
        class Error(val value: String) : UiModel()
        class TopRatedData(val data: List<Movie>) : UiModel()
        class UpcomingData(val data: List<Movie>) : UiModel()
        class RecommendedData(val data: List<Movie>) : UiModel()
        class MovieSelected(val data: String, val view: View) : UiModel()
    }

    fun onCreate(){
        setLoadingData()
        getTopRatedMovies().also { getEnglishMovies() }
        getUpcomingMovies()
    }

    private fun setLoadingData()= viewModelScope.launch (Dispatchers.Main){
        val loadingData = listOf(Movie(Constants.loading),Movie(Constants.loading)
            ,Movie(Constants.loading),Movie(Constants.loading))
        _model.value = UiModel.TopRatedData(loadingData)
        _model.value = UiModel.UpcomingData(loadingData)
        _model.value = UiModel.RecommendedData(loadingData)
    }

    fun getTopRatedMovies() = viewModelScope.launch{
        val result = getTopRatedMoviesUseCase()
        withContext(Dispatchers.Main) {
            if (!result.isNullOrEmpty()) {
                _model.value = UiModel.TopRatedData(result)
            } else {
                _model.value = UiModel.Error("You currently have no data. " +
                        "Check your internet connection and try again!")
            }
        }
    }

    fun getUpcomingMovies() = viewModelScope.launch{
        val result = getUpcomingMoviesUseCase()
        withContext(Dispatchers.Main) {
            if (!result.isNullOrEmpty()) {
                _model.value = UiModel.UpcomingData(result)
            }
        }
    }

    fun getEnglishMovies() = viewModelScope.launch{
        val result = getEnglishMoviesUseCase()
        withContext(Dispatchers.Main){
            _model.value = UiModel.RecommendedData(result)
        }
    }

    fun getMoviesByDate() = viewModelScope.launch{
        val result = getDateFilteredMoviesUseCase()
        withContext(Dispatchers.Main){
            _model.value = UiModel.RecommendedData(result)
        }
    }

    fun onMovieItemSelected(movie: Movie, view: View){
        _model.value = UiModel.MovieSelected(gson.toJson(movie), view)
    }

}