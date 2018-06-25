package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.actions.*
import com.denwehrle.boilerplate.redux.state.CounterState
import org.rekotlin.Action

fun isLoadingReducer(action: Action, isLoading: Boolean?): Boolean {
    var state: Boolean = isLoading ?: false
    when(action){
        is StartLoadingAction -> {
            state = true
        }
        is LoadContactsAction -> {
            state = true
        }
        is StopLoadingAction -> {
            state = false
        }
        is LoadedContactsSuccessfulAction -> {
            state = false
        }
    }
    return state
}