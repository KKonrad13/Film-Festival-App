package com.example.filmfestivalapp.di

import android.content.Context
import com.example.filmfestivalapp.screens.home.HomeViewModel
import com.example.filmfestivalapp.screens.moviedetails.MovieDetailsViewModel
import com.example.filmfestivalapp.screens.movielist.MovieListViewModel
import com.example.filmfestivalapp.screens.ratemovie.RateMovieViewModel
import com.example.filmfestivalapp.screens.ratinglist.RatingListViewModel
import com.example.filmfestivalapp.MainViewModel
import com.example.filmfestivalapp.event.IntentEventImpl
import com.example.filmfestivalapp.event.IntentEvent
import com.example.filmfestivalapp.repositories.FirestoreRepository
import com.example.filmfestivalapp.repositories.FirestoreRepositoryImpl
import com.example.filmfestivalapp.repositories.room.database.AppDatabase
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

object Koin {
    fun init(context: Context) {
        startKoin {
            androidLogger()
            androidContext(context)
            modules(listOf(viewModels, events, firebase, repositories, localDatabase))
        }
    }

    private val viewModels = module {
        viewModelOf(::HomeViewModel)
        viewModelOf(::MovieDetailsViewModel)
        viewModelOf(::MovieListViewModel)
        viewModelOf(::RateMovieViewModel)
        viewModelOf(::RatingListViewModel)
        viewModelOf(::MainViewModel)
    }

    private val events = module {
        factoryOf(::IntentEventImpl) bind IntentEvent::class
    }

    private val repositories = module {
        singleOf(::FirestoreRepositoryImpl) bind FirestoreRepository::class
    }

    private val firebase = module {
        single {
            FirebaseFirestore.getInstance()
        }
    }

    private val localDatabase = module {
        single { AppDatabase.create(androidApplication()) }
        single { get<AppDatabase>().movieDaos() }
        single { get<AppDatabase>().ratingDaos() }
    }
}