package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.actions.LoadPersistentAppStateAction
import com.denwehrle.boilerplate.redux.state.AppState
import org.rekotlin.Action

/**
 * Our [AppState] is composed of several states.
 * For each one of the states there's a responsible Reducer
 *
 * @author Miguel Costa
 */
fun appReducer(action: Action, appState: AppState?): AppState {
    var state: AppState

    when (action) {
        is LoadPersistentAppStateAction -> {
            state = action.appState
        }
        else -> {
            state = AppState.notLoading().copy(
                    isLoading = isLoadingReducer(action, appState?.isLoading),
                    contactsState = contactsState(action, appState?.contactsState)
            )
        }
    }

    return state
}


//fun appReducer(action: Action, appState: AppState?): AppState =
//        AppState(
//                index = "1",
//                isLoading = isLoadingReducer(action, appState?.isLoading),
//                contactsState = contactsState(action, appState?.contactsState)
//        )