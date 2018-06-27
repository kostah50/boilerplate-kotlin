package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.state.AppState
import org.rekotlin.Action

/**
 * Our [AppState] is composed of several states.
 * For each one of the states there's a responsible Reducer
 *
 * @author Miguel Costa
 */
fun appReducer(action: Action, appState: AppState?): AppState =
        AppState(
                isLoading = isLoadingReducer(action, appState?.isLoading),
                contactsState = contactsState(action, appState?.contactsState),
                isWelcomeDone = isWelcomeDoneReducer(action, appState?.isWelcomeDone)
        )