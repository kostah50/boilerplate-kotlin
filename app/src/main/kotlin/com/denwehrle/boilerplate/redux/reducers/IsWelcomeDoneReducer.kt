package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.actions.SetWelcomeDone
import org.rekotlin.Action

/**
 * @author Duc Giang Le
 */

fun isWelcomeDoneReducer(action: Action, isWelcomeDone: Boolean?): Boolean {
    var state: Boolean = isWelcomeDone ?: false
    when(action) {
        is SetWelcomeDone -> {
            state = true
        }
    }

    return state
}