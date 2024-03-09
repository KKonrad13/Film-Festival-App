package com.example.filmfestivalapp.screens.ratinglist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import com.example.filmfestivalapp.Navigation
import com.example.filmfestivalapp.Navigation1
import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.R
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.screens.components.FilmFestivalTopBar
import com.example.filmfestivalapp.screens.components.StarRatingBar
import com.example.filmfestivalapp.screens.components.clickableNoIndication
import com.example.filmfestivalapp.ui.Dimensions
import com.example.filmfestivalapp.ui.theme.FilmFestivalAppTheme
import org.koin.androidx.compose.getViewModel
import java.time.format.DateTimeFormatter


@Composable
fun RatingListScreen(
    onBackClick: Navigation,
    onMovieClick: Navigation1<String>,
) {
    val viewModel = getViewModel<RatingListViewModel>()
    val uiInteraction = RatingListUiInteraction.default(
        onBackClick = onBackClick,
        onSearchFilterChanged = viewModel::onSearchFilterChanged,
        onMovieClick = onMovieClick,
        onSortClick = viewModel::onSortClick,
    )
    val uiState by viewModel.state.collectAsState()
    RatingListScreenContent(state = uiState, uiInteraction = uiInteraction)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RatingListScreenContent(
    state: RatingListState,
    uiInteraction: RatingListUiInteraction,
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
                        text = stringResource(id = R.string.your_ratings),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                },
            )
        }
    ) { contentPadding ->
        val topPadding = contentPadding.calculateTopPadding() + Dimensions.space18
        Column(
            modifier = Modifier
                .padding(top = topPadding)
                .padding(horizontal = Dimensions.space18)
        ) {
            OutlinedTextField(
                value = state.searchFilter,
                onValueChange = uiInteraction::onSearchFilterChanged,
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_search),
                        contentDescription = null
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.find_movie),
                        modifier = Modifier.alpha(.7f)
                    )
                },
                shape = RoundedCornerShape(Dimensions.roundedCorner10),
                singleLine = true
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = Dimensions.space10)
            ) {
                for (sortOption in state.sortOptions) {
                    val title = when (sortOption.first) {
                        SortType.TITLE -> {
                            stringResource(id = R.string.title)
                        }

                        SortType.DATE -> {
                            stringResource(id = R.string.date)

                        }

                        SortType.RATING -> {
                            stringResource(id = R.string.rating)
                        }
                    }
                    SortSection(
                        onSortClick = { uiInteraction.onSortClick(sortOption.first) },
                        sortMode = sortOption.second,
                        title = title,
                        moreThanOneMovieInRatingList = state.shownMovies.size > 1
                    )
                }
            }
            if (state.allMovies.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = stringResource(id = R.string.no_ratings_msg),
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.padding(top = Dimensions.space18),
                ) {
                    items(state.shownMovies) {
                        MovieWithDetailsTile(
                            movie = it,
                            onClick = { uiInteraction.onMovieClick(it.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SortSection(
    onSortClick: OnClick,
    sortMode: SortMode,
    title: String,
    moreThanOneMovieInRatingList: Boolean,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickableNoIndication {
                if (moreThanOneMovieInRatingList) {
                    onSortClick()
                }
            }
    ) {
        Icon(
            painter =
            if (sortMode == SortMode.ASCENDING) painterResource(id = R.drawable.icon_up)
            else painterResource(id = R.drawable.icon_down),
            contentDescription = null,
            tint = if (sortMode == SortMode.NONE) MaterialTheme.colorScheme.primary.copy(alpha = .8f) else MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(Dimensions.icon24)
        )
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.let {
                it.copy(color = if (sortMode == SortMode.NONE) it.color.copy(alpha = .8f) else it.color)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieWithDetailsTile(movie: Movie, onClick: OnClick) {
    Surface(
        shape = RoundedCornerShape(Dimensions.roundedCorner10),
        modifier = Modifier
            .padding(bottom = Dimensions.space18)
            .border(
                width = Dimensions.space1,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(Dimensions.roundedCorner10)
            ),
        onClick = onClick
    ) {
        Row(modifier = Modifier.height(Dimensions.space96)) {
            AsyncImage(
                model = movie.photo,
                placeholder = painterResource(id = R.drawable.icon_downloading),
                error = painterResource(id = R.drawable.icon_error),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(Dimensions.space96)
                    .weight(.29f)
                    .background(color = Color.LightGray)
            )
            Column(
                modifier = Modifier
                    .weight(.71f)
                    .padding(Dimensions.space8),
                verticalArrangement = Arrangement.spacedBy(Dimensions.space18)
            ) {
                val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                Text(
                    text = movie.title.uppercase(),
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.35f)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.65f)
                ) {
                    Text(
                        text = movie.date.format(dateFormatter),
                        style = MaterialTheme.typography.bodySmall,
//                        modifier = Modifier.weight(.35f)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    StarRatingBar(
                        rating = movie.movieRating,
                        starSize = Dimensions.icon24,
//                        modifier = Modifier.weight(.65f),
                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun RatingListScreenPreview() {
    FilmFestivalAppTheme {
        RatingListScreen({}, {})
    }
}