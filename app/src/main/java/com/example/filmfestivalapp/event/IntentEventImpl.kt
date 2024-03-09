package com.example.filmfestivalapp.event

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.res.stringResource
import com.example.filmfestivalapp.R
import kotlinx.coroutines.flow.MutableSharedFlow
import java.lang.Exception

class IntentEventImpl : IntentEvent {
    private val flow = MutableSharedFlow<Intent>()
    override suspend fun intent(intent: Intent) {
        flow.emit(intent)
    }

    @Composable
    override fun CollectIntent(context: Context) {
        val error = stringResource(id = R.string.error)
        LaunchedEffect(Unit) {
            flow.collect { intent ->
                try {
                    context.startActivity(intent)
                } catch (e: Exception){
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}