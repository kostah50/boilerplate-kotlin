package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.actions.*
import org.rekotlin.Action

/**
 * @author Miguel Costa
 */
fun isLoadingReducer(action: Action, isLoading: Boolean?): Boolean {
    var state: Boolean = isLoading ?: false
    when (action) {
        is StartLoadingAction, is SyncAction -> {
            state = true
        }
        is StopLoadingAction, is SyncContactsSuccessfulAction -> {
            state = false
        }
    }
    return state
}