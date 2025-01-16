package com.example.testwithpoetry.ui.main.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testwithpoetry.R
import com.example.testwithpoetry.ui.components.LoadingDialogComponent
import com.example.testwithpoetry.ui.components.TopBarComponent
import com.example.testwithpoetry.ui.main.TabsNav
import com.example.testwithpoetry.ui.main.interactions.AuthorsState
import com.example.testwithpoetry.ui.main.interactions.UserInfoState
import com.example.testwithpoetry.ui.main.viewmodels.AuthorsViewModel

@Composable
fun TabsScreen(modifier: Modifier = Modifier, userInfoState: UserInfoState) {


    val authorsViewModel: AuthorsViewModel = hiltViewModel()
    val authorsState by authorsViewModel.authorsState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = authorsState.isError) {
        if (authorsState.isError && authorsState.message.isNotEmpty()) {
            snackbarHostState
                .showSnackbar(
                    message = authorsState.message,
                    duration = SnackbarDuration.Indefinite,
                    actionLabel = "Close"
                )
        }
    }

    var currentSection by remember {
        mutableStateOf(TabsNav.Poetry)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                TabsNav.entries.forEach { item ->
                    NavigationBarItem(selected = item == currentSection, onClick = {
                        currentSection = item
                    }, icon = {
                        Text(item.name)
                    })
                }
            }
        }, topBar = {
            TopBarComponent(
                title = if (currentSection == TabsNav.Poetry && authorsState.selectedAuthor != null) {
                    "${authorsState.selectedAuthor}"
                } else if (currentSection == TabsNav.Poetry) {
                    "Welcome ${userInfoState.user!!.name}"
                } else {
                    stringResource(R.string.profile_label)
                },
                backAction = backAction(currentSection, authorsState, authorsViewModel)

            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { snackbarData ->
                Snackbar(snackbarData = snackbarData)
            }
        }
    ) {
        Box(modifier.padding(it)) {
            when (currentSection) {
                TabsNav.Poetry -> {
                    if (authorsState.selectedAuthor != null) {
                        AuthorDetails(
                            poemList = authorsState.poemsList,
                            poemSelected = authorsState.poemSelected,
                            onSelectPoem = { poemTitle ->
                                authorsViewModel.selectPoem(
                                    authorsState.selectedAuthor!!,
                                    poemTitle
                                )
                            }) {
                            authorsViewModel.backToPoems()
                        }

                    } else {
                        PoetryView(
                            authorsList = authorsState.authorsList,
                            authorsViewModel = authorsViewModel
                        )

                    }

                }


                TabsNav.Account -> ProfileView(
                    name = userInfoState.user!!.name,
                    email = userInfoState.user.email,
                    birthday = userInfoState.user.birthday
                )
            }

            if (authorsState.isLoading) {
                LoadingDialogComponent()
            }
        }
    }
}

fun backAction(
    currentSection: TabsNav,
    authorsState: AuthorsState,
    authorsViewModel: AuthorsViewModel
): (() -> Unit)? {
    if (currentSection == TabsNav.Account) {
        return null
    }
    if ((currentSection == TabsNav.Poetry || currentSection == TabsNav.Account) && authorsState.selectedAuthor == null) {
        return null
    } else {
        return { authorsViewModel.backToAuthors() }
    }
}

fun mToast(context: Context) {
    Toast.makeText(context, "Author added to favorites", Toast.LENGTH_SHORT).show()
}