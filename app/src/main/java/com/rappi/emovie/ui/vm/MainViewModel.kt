package com.rappi.emovie.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    private val getDateFilteredMoviesUseCase: GetDateFilteredMoviesUseCase
): ViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel> get() = _model

    sealed class UiModel{
        class Error(val value: String) : UiModel()
        class TopRatedData(val data: List<Movie>) : UiModel()
        class UpcomingData(val data: List<Movie>) : UiModel()
        class RecommendedData(val data: List<Movie>) : UiModel()
    }

    fun onCreate(){
        setLoadingData()
        getTopRatedMovies()
        getUpcomingMovies()
    }

    private fun setLoadingData()= viewModelScope.launch (Dispatchers.Main){
        val loadingData = listOf(Movie(Constants.loading),Movie(Constants.loading)
            ,Movie(Constants.loading),Movie(Constants.loading))
        _model.value = UiModel.TopRatedData(loadingData)
        _model.value = UiModel.UpcomingData(loadingData)
        _model.value = UiModel.RecommendedData(loadingData)
    }

    private fun getTopRatedMovies() = viewModelScope.launch (Dispatchers.IO){
        val result = getTopRatedMoviesUseCase()
        withContext(Dispatchers.Main) {
            if (!result.isNullOrEmpty()) {
                _model.value = UiModel.TopRatedData(result)
                getEnglishMovies()
            } else {
                _model.value = UiModel.Error("You currently have no data")
            }
        }
    }

    private fun getUpcomingMovies() = viewModelScope.launch (Dispatchers.IO){
        val result = getUpcomingMoviesUseCase()
        withContext(Dispatchers.Main) {
            if (!result.isNullOrEmpty()) {
                _model.value = UiModel.UpcomingData(result)
            } else {
                _model.value = UiModel.Error("You currently have no data")
            }
        }
    }

    fun getEnglishMovies() = viewModelScope.launch(Dispatchers.IO){
        val result = getEnglishMoviesUseCase()
        withContext(Dispatchers.Main){
            _model.value = UiModel.RecommendedData(result)
        }
    }

    fun getMoviesByDate() = viewModelScope.launch(Dispatchers.IO){
        val result = getDateFilteredMoviesUseCase()
        withContext(Dispatchers.Main){
            _model.value = UiModel.RecommendedData(result)
        }
    }

    fun onMovieItemSelected(movie: Movie){

    }

}