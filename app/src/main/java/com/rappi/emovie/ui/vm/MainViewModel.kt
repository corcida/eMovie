package com.rappi.emovie.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rappi.emovie.data.repository.TopRatedMovieRepository
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetTopRatedMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTopRatedMoviesUseCase: GetTopRatedMoviesUseCase
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
    }

    private fun setLoadingData(){
        val loadingData = listOf(Movie("Loading"),Movie("Loading"),Movie("Loading"))
        _model.value = UiModel.TopRatedData(loadingData)
        _model.value = UiModel.UpcomingData(loadingData)
        _model.value = UiModel.RecommendedData(loadingData)
    }

    private fun getTopRatedMovies() = viewModelScope.launch (Dispatchers.IO){
        val result = getTopRatedMoviesUseCase()
        if (!result.isNullOrEmpty()){
            _model.value = UiModel.TopRatedData(result)
        } else {
            _model.value = UiModel.Error("You currently have no data")
        }
    }

}