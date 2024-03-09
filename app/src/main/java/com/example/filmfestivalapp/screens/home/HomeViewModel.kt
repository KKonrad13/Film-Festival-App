package com.example.filmfestivalapp.screens.home

import android.content.Intent
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.filmfestivalapp.event.IntentEvent
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.repositories.room.dao.MovieDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime

private const val INCOMING_MOVIES_SHOWN = 5

class HomeViewModel(
    private val intentEvent: IntentEvent,
    private val movieDao: MovieDao,
) : ViewModel(), IntentEvent by intentEvent {
    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> get() = _state

    fun updateMovies() {
        viewModelScope.launch {
            movieDao.getAllMovies().collect { movieEntities ->
                _state.update {
                    it.copy(incomingMovies = movieEntities
                        .map { movieEntity ->
                            movieEntity.toMovie(emptyList())
                        }
                        .filter { movie ->
                            movie.date >= LocalDateTime.now()
                        }
                        .sortedBy { movie -> movie.date }
                        .take(INCOMING_MOVIES_SHOWN)
                    )
                }
            }
        }
    }

    fun onFacebookClick() {
        openSite("https://www.facebook.com/")
    }

    fun onInstagramClick() {
        openSite("https://www.instagram.com/")
    }

    fun onYoutubeClick() {
        openSite("https://www.youtube.com/")
    }

    private fun openSite(link: String) = viewModelScope.launch {
        intent(Intent(Intent.ACTION_VIEW, Uri.parse(link)))
    }
}

data class HomeState(
    val incomingMovies: List<Movie> = emptyList(),
)