package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.actions.*
import org.rekotlin.Action

/**
 * @author Miguel Costa
 */
fun isLoadingReducer(action: Action, isLoading: Boolean?): Boolean {
    var state: Boolean = isLoading ?: false
    when (action) {
        is StartLoadingAction, is LoadContactsAction -> {
            state = true
        }
        is StopLoadingAction, is LoadContactsSuccessfulAction, is LoadContactsFailedAction -> {
            state = false
        }
    }
    return state
}