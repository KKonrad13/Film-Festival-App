package com.example.filmfestivalapp.screens.video

import android.content.Context
import android.content.res.Configuration
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.R
import com.example.filmfestivalapp.screens.components.FilmFestivalTopBar
import com.example.filmfestivalapp.screens.components.clickableNoIndication
import com.example.filmfestivalapp.screens.home.Animation
import com.example.filmfestivalapp.ui.Dimensions


@Composable
fun VideoScreen(
    onBackClick: OnClick,
) {
    val configuration = LocalConfiguration.current
    val videoUri = Uri.parse("android.resource://com.example.filmfestivalapp/raw/movie_promo")
    if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
        Scaffold(
            topBar = {
                FilmFestivalTopBar(
                    leftContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_back),
                            contentDescription = stringResource(id = R.string.icon_back),
                            modifier = Modifier
                                .clickableNoIndication { onBackClick() }
                                .size(Dimensions.icon48)
                        )
                    },
                    centerContent = {
                        Text(
                            text = stringResource(id = R.string.promo_movie),
                            style = MaterialTheme.typography.titleLarge,
                            modifier = Modifier.align(Alignment.Center),
                            textAlign = TextAlign.Center
                        )
                    },
                )
            }
        ) { contentPadding ->
            val topPadding = contentPadding.calculateTopPadding() + Dimensions.space18
            Column(modifier = Modifier.padding(top = topPadding)) {
                Spacer(modifier = Modifier.weight(1f))
                Animation(
                    modifier = Modifier.fillMaxWidth(),
                    animationId = R.raw.sound_up_animation
                )
                Spacer(modifier = Modifier.weight(1f))
                VideoPlayer(videoUri = videoUri)
                Spacer(modifier = Modifier.weight(1f))
                Animation(
                    modifier = Modifier.fillMaxWidth(),
                    animationId = R.raw.sound_up_animation
                )
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }else{
        VideoPlayer(videoUri = videoUri)
    }
}

@Composable
fun VideoPlayer(
    videoUri: Uri,
    modifier: Modifier = Modifier,
) {
    AndroidView(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp)),
        factory = { context ->
            VideoView(context).apply {
                setVideoURI(videoUri)

                val mediaController = MediaController(context)
                mediaController.setAnchorView(this)

                setMediaController(mediaController)
                println("setMediaController")
                setOnPreparedListener {
                    println("setOnPreparedListener")
                    start()
                }
            }
        })

}

private fun isValidUri(context: Context, uri: Uri): Boolean {
    return try {
        val inputStream = context.contentResolver.openInputStream(uri)
        inputStream?.close()
        true
    } catch (e: Exception) {
        false
    }
}
