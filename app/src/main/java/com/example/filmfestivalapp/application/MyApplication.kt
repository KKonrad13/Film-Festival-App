package com.example.filmfestivalapp.application

import android.app.Application
import com.example.filmfestivalapp.di.Koin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Koin.init(this)
    }
}