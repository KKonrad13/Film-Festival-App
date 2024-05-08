package com.example.filmfestivalapp.screens.movielist

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.filmfestivalapp.Navigation
import com.example.filmfestivalapp.Navigation1
import com.example.filmfestivalapp.OnClick
import com.example.filmfestivalapp.OnClick1
import com.example.filmfestivalapp.R
import com.example.filmfestivalapp.model.Movie
import com.example.filmfestivalapp.screens.components.FilmFestivalTopBar
import com.example.filmfestivalapp.screens.components.capitalizeFirstLetter
import com.example.filmfestivalapp.screens.components.clickableNoIndication
import com.example.filmfestivalapp.ui.Dimensions
import com.example.filmfestivalapp.ui.theme.FilmFestivalAppTheme
import org.koin.androidx.compose.getViewModel
import java.time.format.DateTimeFormatter


@Composable
fun MovieListScreen(
    onBackClick: Navigation,
    onMovieClick: Navigation1<String>,
) {
    val viewModel = getViewModel<MovieListViewModel>()
    val uiInteraction = MovieListUiInteraction.default(
        onBackClick = onBackClick,
        onSearchFilterChanged = viewModel::onSearchFilterChanged,
        onMovieClick = onMovieClick,
        onSortClick = viewModel::onSortClick,
        onShowCategoriesClick = viewModel::onShowCategoriesClick,
        onCategoryClick = viewModel::onCategoryClick,
        onCategoriesDialogDismiss = viewModel::onCategoriesDialogDismiss
    )
    val uiState by viewModel.state.collectAsState()
    if (uiState.isCategoriesDialogShown) {
        CategoriesDialog(
            categories = uiState.allCategories,
            chosenCategories = uiState.chosenCategories,
            onCategoryClick = uiInteraction::onCategoryClick,
            onCategoriesDialogDismiss = uiInteraction::onCategoriesDialogDismiss
        )
    }
    LaunchedEffect(Unit) {
        viewModel.init()
    }
    MovieListScreenContent(state = uiState, uiInteraction = uiInteraction)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MovieListScreenContent(
    state: MovieListState,
    uiInteraction: MovieListUiInteraction,
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
                            .testTag("iconBackMovieList")
                    )
                },
                centerContent = {
                    Text(
                        text = stringResource(id = R.string.screening_movies),
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
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("findMovie"),
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickableNoIndication { uiInteraction.onShowCategoriesClick() }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.icon_filter),
                        contentDescription = stringResource(id = R.string.categories),
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Dimensions.icon24)
                    )
                    Spacer(modifier = Modifier.width(Dimensions.space4))
                    Text(
                        text = stringResource(id = R.string.categories),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                Spacer(modifier = Modifier.weight(1.0f))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickableNoIndication { uiInteraction.onSortClick() }
                ) {
                    Icon(
                        painter =
                        if (state.ascendingSort) painterResource(id = R.drawable.icon_down)
                        else painterResource(id = R.drawable.icon_up),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(Dimensions.icon24)
                    )
                    Text(
                        text = stringResource(id = R.string.date),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            if (state.chosenCategories.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .padding(top = Dimensions.space18)
                ) {
                    for (category in state.chosenCategories) {
                        Surface(
                            shape = RoundedCornerShape(Dimensions.roundedCorner4),
                            modifier = Modifier
                                .padding(Dimensions.space4)
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
                                Icon(
                                    painter = painterResource(id = R.drawable.icon_close),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.clickableNoIndication {
                                        uiInteraction.onCategoryClick(
                                            category
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier.padding(top = Dimensions.space18)
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
                val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.4f)
                ) {
                    Text(
                        text = movie.title.uppercase(),
                        style = MaterialTheme.typography.bodyLarge,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        modifier = Modifier.weight(.65f)
                    )
                    Text(
                        text = movie.date.format(dateFormatter),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(.35f),
                        textAlign = TextAlign.End
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.6f)
                ) {
                    Text(
                        text = movie.place,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(.8f),
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = movie.date.format(timeFormatter),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.weight(.2f),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

    }
}

@Composable
fun CategoriesDialog(
    categories: List<String>,
    chosenCategories: List<String>,
    onCategoryClick: OnClick1<String>,
    onCategoriesDialogDismiss: OnClick,
) {
    Dialog(onDismissRequest = onCategoriesDialogDismiss) {
        Card(
            shape = RoundedCornerShape(Dimensions.roundedCorner10),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(.5f)
                .border(
                    Dimensions.space1,
                    color = MaterialTheme.colorScheme.secondary,
                    RoundedCornerShape(Dimensions.roundedCorner10)
                )
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.categories),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(horizontal = Dimensions.space14)
                        .padding(top = Dimensions.space14)
                )
                for (category in categories) {
                    Row(verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.clickableNoIndication { onCategoryClick(category) }
                    ) {
                        Checkbox(
                            checked = category in chosenCategories,
                            onCheckedChange = { onCategoryClick(category) }
                        )
                        Text(
                            text = category.capitalizeFirstLetter(),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun MovieListScreenPreview() {
    FilmFestivalAppTheme {
        MovieListScreen({}, {})
    }
}