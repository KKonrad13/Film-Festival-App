package com.example.filmfestivalapp.event

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.Composable

interface IntentEvent {
    suspend fun intent(intent: Intent)

    @Composable
    fun CollectIntent(context: Context)
}