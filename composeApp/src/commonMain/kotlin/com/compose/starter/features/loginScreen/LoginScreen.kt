package com.compose.starter.features.loginScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.commonUi.FailureStringBasedOnResponse
import com.compose.starter.constants.ContentDescription
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.primaryDark
import com.compose.starter.theme.primaryLight
import com.compose.starter.theme.tertiaryDark
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.hide
import composestarter.composeapp.generated.resources.login
import composestarter.composeapp.generated.resources.password
import composestarter.composeapp.generated.resources.show
import composestarter.composeapp.generated.resources.tmdb_logo
import composestarter.composeapp.generated.resources.user
import composestarter.composeapp.generated.resources.user_name
import composestarter.composeapp.generated.resources.welcome_msg
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    goToHomeScreen: () -> Unit,
) {
    val snackBarHostState by remember { mutableStateOf(SnackbarHostState()) }
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    var userName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var shouldShowPassword by remember { mutableStateOf(false) }
    val userNameFocus = remember { FocusRequester() }
    val passwordFocus = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val failureMessage = screenState.result.FailureStringBasedOnResponse()

    LaunchedEffect(key1 = screenState.isLoginSuccess, key2 = screenState.isLoading) {
        if (screenState.isLoginSuccess) {
            goToHomeScreen()
        }

        if (!screenState.isLoading && screenState.result.isFailure) {
            launch { snackBarHostState.showSnackbar(failureMessage) }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(MaterialTheme.spacing.medium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                WelcomeText()
            }

            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
            }

            item {
                Image(
                    painter = painterResource(Res.drawable.tmdb_logo),
                    contentDescription = ContentDescription.TMDB_LOGO,
                )
            }

            item {
                Spacer(modifier = Modifier.height(MaterialTheme.sizing.large))
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().focusRequester(userNameFocus),
                    value = userName,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    ),
                    singleLine = true,
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.size(MaterialTheme.sizing.large),
                            painter = painterResource(Res.drawable.user),
                            contentDescription = ContentDescription.USERNAME
                        )
                    },
                    keyboardActions = KeyboardActions(onNext = { passwordFocus.requestFocus() }),
                    onValueChange = { user -> userName = user },
                    placeholder = {
                        Text(
                            stringResource(Res.string.user_name),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
            }

            item {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().focusRequester(passwordFocus),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus(true)
                        }
                    ),
                    singleLine = true,
                    value = password,
                    visualTransformation = if (shouldShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
                    onValueChange = { pas -> password = pas },
                    trailingIcon = {
                        IconButton(onClick = {
                            shouldShowPassword = !shouldShowPassword
                        }) {
                            val (passIcon, desc) = getIconAndContentDescription(
                                shouldShowPassword
                            )
                            Icon(
                                modifier = Modifier.size(MaterialTheme.sizing.large),
                                painter = painterResource(passIcon),
                                contentDescription = desc
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            stringResource(Res.string.password),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.medLarge))
            }

            item {
                ButtonWithLoader(screenState, userName, password, viewModel::onEvent)
            }
        }
    }
}

@Composable
private fun WelcomeText() {
    Text(
        stringResource(Res.string.welcome_msg),
        modifier = Modifier.fillMaxWidth(),
        style = MaterialTheme.typography.displayLarge.copy(
            fontWeight = FontWeight.Bold,
            brush = brush()
        )
    )
}

@Composable
private fun ButtonWithLoader(
    uiState: LoginUiState,
    userName: String,
    password: String,
    onEvent: (LoginEvent) -> Unit,
) {
    AnimatedVisibility(
        visible = uiState.isLoading
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator()
        }
    }
    AnimatedVisibility(
        visible = uiState.isLoading.not()
    ) {
        Button(
            enabled = userName.isNotEmpty() && password.isNotEmpty(),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onEvent(LoginEvent.Login(userName, password))
            }
        ) {
            Text(
                stringResource(Res.string.login),
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
private fun brush(): Brush {
    return Brush.linearGradient(
        colors = listOf(
            primaryDark,
            primaryLight,
            tertiaryDark
        ),
    )
}

@Composable
fun getIconAndContentDescription(shouldShowPassword: Boolean): Pair<DrawableResource, String> {
    return if (shouldShowPassword) {
        Pair(Res.drawable.show, ContentDescription.SHOW_PASS)
    } else {
        Pair(Res.drawable.hide, ContentDescription.HIDE_PASS)
    }
}