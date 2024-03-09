package com.example.filmfestivalapp.screens.ratemovie

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.times
import com.example.filmfestivalapp.Navigation
import com.example.filmfestivalapp.R
import com.example.filmfestivalapp.model.Rating
import com.example.filmfestivalapp.screens.components.FilmFestivalTopBar
import com.example.filmfestivalapp.screens.components.StarRatingBar
import com.example.filmfestivalapp.screens.components.capitalizeFirstLetter
import com.example.filmfestivalapp.screens.components.clickableNoIndication
import com.example.filmfestivalapp.ui.Dimensions
import com.example.filmfestivalapp.ui.theme.FilmFestivalAppTheme
import org.koin.androidx.compose.getViewModel


@Composable
fun RateMovieScreen(
    onBackClick: Navigation,
    movieId: String?
) {
    val viewModel = getViewModel<RateMovieViewModel>()
    val uiInteraction = RateMovieUiInteraction.default(
        onBackClick = onBackClick,
        onConfirmClick = {
            viewModel.onConfirmClick()
            onBackClick()
        },
        onRatingChange = viewModel::onRatingChange,
        onNotesChange = viewModel::onNotesChange
    )
    val uiState by viewModel.state.collectAsState()
    LaunchedEffect(Unit){
        movieId?.let { viewModel.init(it) }
    }
    RateMovieScreenContent(state = uiState, uiInteraction = uiInteraction)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateMovieScreenContent(
    state: RateMovieState,
    uiInteraction: RateMovieUiInteraction,
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
                        text = stringResource(id = R.string.rate_movie),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                },
                rightContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_confirm),
                        contentDescription = stringResource(id = R.string.icon_back),
                        modifier = Modifier
                            .clickableNoIndication { uiInteraction.onConfirmClick() }
                            .size(Dimensions.icon48)
                    )
                }
            )
        }
    ) { contentPadding ->
        val topPadding = contentPadding.calculateTopPadding()
        Column(
            modifier = Modifier
                .padding(top = topPadding)
                .padding(horizontal = Dimensions.space18)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = state.movie.title.uppercase(),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = Dimensions.space18)
            )
            Row(modifier = Modifier.padding(top = Dimensions.space14)) {
                LazyRow(
                    modifier = Modifier
                        .weight(.7f)
                ) {
                    for (category in state.movie.genres) {
                        item {
                            Surface(
                                shape = RoundedCornerShape(Dimensions.roundedCorner4),
                                modifier = Modifier
                                    .padding(horizontal = Dimensions.space4)
                                    .border(
                                        width = Dimensions.space1,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(Dimensions.roundedCorner4)
                                    )
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(Dimensions.space8)
                                ) {
                                    Text(
                                        text = category.capitalizeFirstLetter(),
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                }
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.weight(.1f))
                Row(
                    modifier = Modifier.weight(.2f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.out_of,
                            (state.movie.movieRating * 100).toInt() / 100.0,
                            5
                        ),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.icon_star),
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.icon24)
                    )
                }
            }
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Dimensions.space8)
                    .height(Dimensions.space1)
                    .background(color = MaterialTheme.colorScheme.primary)
            )
            for (rating in state.movie.ratings) {
                RateSection(
                    rating = rating,
                    onRateChange = uiInteraction::onRatingChange
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Dimensions.space8)
                        .height(Dimensions.space1)
                        .background(color = MaterialTheme.colorScheme.primary)
                )
            }
            OutlinedTextField(
                value = state.movie.note,
                onValueChange = uiInteraction::onNotesChange,
                label = {
                    Text(
                        text = stringResource(id = R.string.notes),
                        modifier = Modifier.alpha(.8f)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Dimensions.space18)
            )
        }
    }
}

@Composable
fun RateSection(
    rating: Rating,
    onRateChange: (Rating) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(id = rating.category),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Box {
            StarRatingBar(
                rating = rating.value,
                modifier = Modifier.align(Alignment.Center)
            )
            Slider(
                value = rating.value,
                onValueChange = { onRateChange(Rating(rating.category, it)) },
                valueRange = 0f..5f,
                steps = 10,
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(.0f)
                    .width(5 * Dimensions.icon48)
            )
        }

    }
}

@Preview
@Composable
fun RateMovieScreenPreview() {
    FilmFestivalAppTheme {
        RateMovieScreen({}, "")
    }
}