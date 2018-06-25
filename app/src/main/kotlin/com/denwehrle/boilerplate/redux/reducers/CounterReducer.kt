package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.actions.CounterActionDecrease
import com.denwehrle.boilerplate.redux.actions.CounterActionIncrease
import com.denwehrle.boilerplate.redux.state.CounterState
import org.rekotlin.Action

fun counterReducer(action: Action, counterState: CounterState?): CounterState {
    var state: CounterState = counterState ?: CounterState()
    when(action){
        is CounterActionIncrease -> {
            state = state.copy(counter = state.counter + 1)
        }
        is CounterActionDecrease -> {
            state = state.copy(counter = state.counter - 1)
        }
    }
    return state
}