package com.rappi.emovie.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetTopRatedMoviesUseCase
import com.rappi.emovie.domain.uc.GetUpcomingMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase,
    private val getUpcomingMoviesUseCase: GetUpcomingMoviesUseCase
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
        val loadingData = listOf(Movie("loading"),Movie("loading"),Movie("loading"),Movie("loading"))
        _model.value = UiModel.TopRatedData(loadingData)
        _model.value = UiModel.UpcomingData(loadingData)
        _model.value = UiModel.RecommendedData(loadingData)
    }

    private fun getTopRatedMovies() = viewModelScope.launch (Dispatchers.IO){
        val result = getTopRatedMoviesUseCase()
        withContext(Dispatchers.Main) {
            if (!result.isNullOrEmpty()) {
                _model.value = UiModel.RecommendedData(
                    result.filter { it.original_language == "en" }.take(6))
                _model.value = UiModel.TopRatedData(result)
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

    fun onMovieItemSelected(movie: Movie){

    }

    fun getEnglishMovies(){

    }

    fun getMoviesByDate(){

    }

}