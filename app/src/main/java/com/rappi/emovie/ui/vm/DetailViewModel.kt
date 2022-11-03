package com.rappi.emovie.ui.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rappi.emovie.domain.model.Genre
import com.rappi.emovie.domain.model.Movie
import com.rappi.emovie.domain.uc.GetGenresUseCase
import javax.inject.Inject

class DetailViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase
): ViewModel(){

    private val _model = MutableLiveData<UiModel>()
    val model : LiveData<UiModel> get() = _model

    sealed class UiModel{
        class Genres(val data: List<Genre>) : UiModel()
        class MovieData(val data: Movie) : UiModel()
    }

}