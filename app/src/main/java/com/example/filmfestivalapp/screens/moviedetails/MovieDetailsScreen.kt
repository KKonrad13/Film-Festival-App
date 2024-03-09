package com.example.filmfestivalapp.screens.moviedetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.filmfestivalapp.Navigation
import com.example.filmfestivalapp.Navigation1
import com.example.filmfestivalapp.R
import com.example.filmfestivalapp.screens.components.FilmFestivalTopBar
import com.example.filmfestivalapp.screens.components.StarRatingBar
import com.example.filmfestivalapp.screens.components.clickableNoIndication
import com.example.filmfestivalapp.ui.Dimensions
import com.example.filmfestivalapp.ui.theme.FilmFestivalAppTheme
import org.koin.androidx.compose.getViewModel
import java.time.format.DateTimeFormatter


@Composable
fun MovieDetailsScreen(
    onBackClick: Navigation,
    onStarsClick: Navigation1<String>,
    movieId: String?,
) {
    val viewModel = getViewModel<MovieDetailsViewModel>()
    val uiInteraction = MovieDetailsUiInteraction.default(
        onBackClick = onBackClick,
        onStarsClick = onStarsClick
    )
    val uiState by viewModel.state.collectAsState()
    LaunchedEffect(Unit) {
        movieId?.let { viewModel.init(it) }
    }
    MovieDetailsScreenContent(state = uiState, uiInteraction = uiInteraction)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreenContent(
    state: MovieDetailsState,
    uiInteraction: MovieDetailsUiInteraction,
) {
    Scaffold(
        topBar = {
            FilmFestivalTopBar(
                leftContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_back),
                        contentDescription = stringResource(id = R.string.icon_back),
                        modifier = Modifier
                            .clickableNoIndication { uiInteraction.onBackClick() }
                            .size(Dimensions.icon48)
                    )
                },
                centerContent = {
                    Text(
                        text = stringResource(id = R.string.movie_details),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                },
            )
        }
    ) { contentPadding ->
        val topPadding = contentPadding.calculateTopPadding()
        Column(
            modifier = Modifier
                .padding(top = topPadding)
                .padding(horizontal = Dimensions.space18)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = state.movie.photo,
                contentDescription = null,
                placeholder = painterResource(id = R.drawable.icon_downloading),
                error = painterResource(id = R.drawable.icon_error),
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .padding(top = Dimensions.space18)
                    .background(color = MaterialTheme.colorScheme.secondary),
                contentScale = ContentScale.FillHeight
            )
            Text(
                text = state.movie.title.uppercase(),
                style = MaterialTheme.typography.titleLarge
            )
            StarRatingBar(
                rating = state.movie.movieRating,
                starSize = Dimensions.icon24,
                onClick = { uiInteraction.onStarsClick(movieId = state.movie.id) },
                modifier = Modifier.fillMaxWidth()
            )
            TypeAndDetails(
                type = stringResource(id = R.string.place),
                details = state.movie.place
            )
            val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            TypeAndDetails(
                type = stringResource(id = R.string.date),
                details = state.movie.date.format(dateFormatter),
                modifier = Modifier.padding(top = Dimensions.space12)
            )
            TypeAndDetails(
                type = stringResource(id = R.string.director),
                details = state.movie.displayDirectors,
                modifier = Modifier.padding(top = Dimensions.space12)
            )
            TypeAndDetails(
                type = stringResource(id = R.string.screenwriter),
                details = state.movie.displayScreenwriters,
                modifier = Modifier.padding(top = Dimensions.space12)
            )
            TypeAndDetails(
                type = stringResource(id = R.string.genre),
                details = state.movie.displayGenres,
                modifier = Modifier.padding(top = Dimensions.space12)
            )
            TypeAndDetails(
                type = stringResource(id = R.string.production),
                details = state.movie.displayProducers,
                modifier = Modifier.padding(top = Dimensions.space12)
            )
            TypeAndDetails(
                type = stringResource(id = R.string.description),
                details = state.movie.description,
                modifier = Modifier.padding(top = Dimensions.space12)
            )
            Spacer(modifier = Modifier.height(Dimensions.space18))
        }
    }
}

@Composable
fun TypeAndDetails(
    type: String,
    details: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        Text(
            text = "$type: ",
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = details,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@Composable
fun MovieDetailsScreenPreview() {
    FilmFestivalAppTheme {
        MovieDetailsScreen({}, {}, "")
    }
}