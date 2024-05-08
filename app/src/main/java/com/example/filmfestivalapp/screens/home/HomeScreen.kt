package com.example.filmfestivalapp.screens.home

import android.media.MediaPlayer
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.filmfestivalapp.Navigation
import com.example.filmfestivalapp.Navigation1
import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.R
import com.example.filmfestivalapp.screens.components.clickableNoIndication
import com.example.filmfestivalapp.ui.Dimensions
import com.example.filmfestivalapp.ui.theme.FilmFestivalAppTheme
import org.koin.androidx.compose.getViewModel


@Composable
fun HomeScreen(
    onBackClick: Navigation,
    onMovieClick: Navigation1<String>,
    onMovieListClick: Navigation,
    onRatingListClick: Navigation,
    onPromoMovieClick: Navigation,
) {
    val viewModel = getViewModel<HomeViewModel>()
    val uiInteraction = HomeUiInteraction.default(
        onBackClick = onBackClick,
        onFacebookClick = viewModel::onFacebookClick,
        onInstagramClick = viewModel::onInstagramClick,
        onYoutubeClick = viewModel::onYoutubeClick,
        onMovieClick = onMovieClick,
        onMovieListClick = onMovieListClick,
        onRatingListClick = onRatingListClick,
        onPromoMovieClick = onPromoMovieClick
    )
    val uiState by viewModel.state.collectAsState()
    val context = LocalContext.current
    viewModel.CollectIntent(context = context)
    LaunchedEffect(Unit) {
        viewModel.updateMovies()
    }
    HomeScreenContent(state = uiState, uiInteraction = uiInteraction)
}

@Composable
fun HomeScreenContent(
    state: HomeState,
    uiInteraction: HomeUiInteraction,
) {
    val context = LocalContext.current
    val mediaPlayer: MediaPlayer = MediaPlayer.create(context, R.raw.background_music)
    LaunchedEffect(key1 = Unit){
        mediaPlayer.start()
    }
    DisposableEffect(key1 = Unit){
        onDispose {
            mediaPlayer.pause()
        }
    }
    Column(
        modifier = Modifier
            .padding(Dimensions.space18)
            .verticalScroll(rememberScrollState())
    ) {
        Animation(
            modifier = Modifier
                .fillMaxWidth()
                .clickableNoIndication { mediaPlayer.pause() },
            animationId = R.raw.movie_animation
        )
        Text(
            text = stringResource(id = R.string.festival_date),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = Dimensions.space24),
            textAlign = TextAlign.Center
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_facebook),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimensions.icon96)
                    .clickableNoIndication { uiInteraction.onFacebookClick() },
                tint = MaterialTheme.colorScheme.primary
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_youtube),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimensions.icon96)
                    .clickableNoIndication { uiInteraction.onYoutubeClick() },
                tint = MaterialTheme.colorScheme.secondary
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_instagram),
                contentDescription = null,
                modifier = Modifier
                    .size(Dimensions.icon96)
                    .clickableNoIndication { uiInteraction.onInstagramClick() },
                tint = MaterialTheme.colorScheme.primary
            )
        }
        if (state.incomingMovies.isNotEmpty()) {
            Text(
                text = stringResource(id = R.string.incoming_screenings) + ":",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = Dimensions.space24)
            )
            Row(
                modifier = Modifier
                    .padding(top = Dimensions.space8)
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(Dimensions.space10)
            ) {
                for (movie in state.incomingMovies) {
                    Surface(
                        shape = RoundedCornerShape(Dimensions.roundedCorner10),
                        modifier = Modifier
                            .padding(horizontal = Dimensions.space4)
                            .border(
                                width = Dimensions.space1,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(Dimensions.roundedCorner10)
                            ),
                        onClick = { uiInteraction.onMovieClick(movie.id) }
                    ) {
                        Column(modifier = Modifier.width(Dimensions.photoLarge144)) {
                            AsyncImage(
                                model = movie.photo,
                                contentDescription = null,
                                placeholder = painterResource(id = R.drawable.icon_downloading),
                                error = painterResource(id = R.drawable.icon_error),
                                modifier = Modifier
                                    .size(Dimensions.photoLarge144)
                                    .background(color = MaterialTheme.colorScheme.secondary)
                            )
                        }
                    }
                }
            }
        }
        NavigationButton(
            icon = R.drawable.icon_list,
            text = stringResource(id = R.string.screening_movies),
            onClick = uiInteraction::onMovieListClick
        )
        NavigationButton(
            icon = R.drawable.icon_history,
            text = stringResource(id = R.string.your_ratings),
            onClick = uiInteraction::onRatingListClick
        )
        NavigationButton(
            icon = R.drawable.icon_movie,
            text = stringResource(id = R.string.promo_movie),
            onClick = uiInteraction::onPromoMovieClick
        )
    }
}

@Composable
fun Animation(
    modifier: Modifier = Modifier,
    @RawRes animationId: Int,
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animationId)
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = { preloaderProgress },
        modifier = modifier
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationButton(
    @DrawableRes icon: Int,
    text: String,
    onClick: OnClick,
) {
    Surface(
        shape = RoundedCornerShape(Dimensions.roundedCorner10),
        modifier = Modifier
            .padding(top = Dimensions.space18)
            .padding(horizontal = Dimensions.space4)
            .fillMaxWidth()
            .border(
                width = Dimensions.space1,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(Dimensions.roundedCorner10)
            ),
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(Dimensions.icon48)
            )
            Spacer(modifier = Modifier.width(Dimensions.space18))
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    FilmFestivalAppTheme {
        HomeScreen({}, {}, {}, {}, {})
    }
}