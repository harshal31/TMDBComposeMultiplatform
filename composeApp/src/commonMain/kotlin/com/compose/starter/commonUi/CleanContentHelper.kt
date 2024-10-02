package com.compose.starter.commonUi

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.compose.starter.constants.ContentDescription
import com.compose.starter.networking.ApiState
import com.compose.starter.screenHeight
import com.compose.starter.spacingsAndBorders.spacing
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.arrow_back
import composestarter.composeapp.generated.resources.general_error
import composestarter.composeapp.generated.resources.network_error
import composestarter.composeapp.generated.resources.not_found_error
import composestarter.composeapp.generated.resources.retry
import composestarter.composeapp.generated.resources.server_error
import composestarter.composeapp.generated.resources.unauthorized_error
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CleanContent(
    padding: PaddingValues,
    apiState: ApiState,
    onRetry: (() -> Unit)? = null,
    onBack: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit,
) {
    when (apiState) {
        ApiState.NETWORK_ISSUE -> NetworkIssue(
            padding,
            onBack = { onBack?.invoke() },
            onRetry = { onRetry?.invoke() }
        )

        ApiState.UNAUTHORIZED -> UnAuthorized(
            padding,
            onBack = { onBack?.invoke() },
            onRetry = { onRetry?.invoke() }
        )

        ApiState.NOT_FOUND -> NotFound(
            padding,
            onBack = { onBack?.invoke() },
            onRetry = { onRetry?.invoke() }
        )

        ApiState.INTERNAL_SERVER_ERROR -> InternalServerError(
            padding,
            onBack = { onBack?.invoke() },
            onRetry = { onRetry?.invoke() }
        )

        ApiState.ERROR -> Error(
            padding,
            onBack = { onBack?.invoke() },
            onRetry = { onRetry?.invoke() }
        )

        ApiState.CIRCULAR_LOADING -> CircularLoadingScreen(padding)
        ApiState.HORIZONTAL_LOADING -> HorizontalLoadingScreen(padding)
        ApiState.NONE -> content(padding)
    }
}

@Composable
@Preview
private fun NetworkIssue(
    padding: PaddingValues,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    AnimatedVisibility(true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.25f),
                    painter = painterResource(Res.drawable.network_error),
                    contentDescription = ContentDescription.NETWORK_ERROR,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))

                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        stringResource(Res.string.retry),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
            CircleIcon(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.default
                ).align(Alignment.TopStart),
                icon = Res.drawable.arrow_back,
                contentDesc = ContentDescription.ARROW_BACK,
                onIconClick = onBack
            )
        }
    }
}


//enter = fadeIn() + slideInHorizontally(),
//exit = fadeOut() + slideOutHorizontally()
@Composable
private fun UnAuthorized(
    padding: PaddingValues,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    AnimatedVisibility(
        true,
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.25f),
                    painter = painterResource(Res.drawable.unauthorized_error),
                    contentDescription = ContentDescription.UNAUTHORIZED_ERROR,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))

                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        stringResource(Res.string.retry),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
            CircleIcon(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.default
                ).align(Alignment.TopStart),
                icon = Res.drawable.arrow_back,
                contentDesc = ContentDescription.ARROW_BACK,
                onIconClick = onBack
            )
        }
    }
}

@Composable
private fun NotFound(
    padding: PaddingValues,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    AnimatedVisibility(true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.25f),
                    painter = painterResource(Res.drawable.not_found_error),
                    contentDescription = ContentDescription.NOT_FOUND_ERROR,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))

                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        stringResource(Res.string.retry),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
            CircleIcon(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.default
                ).align(Alignment.TopStart),
                icon = Res.drawable.arrow_back,
                contentDesc = ContentDescription.ARROW_BACK,
                onIconClick = onBack
            )
        }
    }
}

@Composable
private fun InternalServerError(
    padding: PaddingValues,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    AnimatedVisibility(true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.25f),
                    painter = painterResource(Res.drawable.server_error),
                    contentDescription = ContentDescription.INTERNAL_SERVER_ERROR,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))

                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        stringResource(Res.string.retry),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
            CircleIcon(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.default
                ).align(Alignment.TopStart),
                icon = Res.drawable.arrow_back,
                contentDesc = ContentDescription.ARROW_BACK,
                onIconClick = onBack
            )
        }
    }
}


@Composable
private fun Error(
    padding: PaddingValues,
    onBack: () -> Unit,
    onRetry: () -> Unit,
) {
    AnimatedVisibility(true) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(screenHeight * 0.25f),
                    painter = painterResource(Res.drawable.general_error),
                    contentDescription = ContentDescription.GENERAL_ERROR,
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.default))

                Button(
                    onClick = onRetry,
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .wrapContentHeight()
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        stringResource(Res.string.retry),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                    )
                }
            }
            CircleIcon(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.default,
                    vertical = MaterialTheme.spacing.default
                ).align(Alignment.TopStart),
                icon = Res.drawable.arrow_back,
                contentDesc = ContentDescription.ARROW_BACK,
                onIconClick = onBack
            )
        }
    }
}


@Composable
fun CircularLoadingScreen(padding: PaddingValues) {
    AnimatedVisibility(true) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun HorizontalLoadingScreen(padding: PaddingValues) {
    AnimatedVisibility(true) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}