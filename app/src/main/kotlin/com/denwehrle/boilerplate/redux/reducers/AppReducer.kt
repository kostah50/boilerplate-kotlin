package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.state.AppState
import org.rekotlin.Action

fun appReducer(action: Action, appState: AppState?): AppState =
        AppState(
                isLoading = isLoadingReducer(action, appState?.isLoading),
                contacts = contactsReducer(action, appState?.contacts)
        )