package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.actions.SomeOtherCounterActionDecrease
import com.denwehrle.boilerplate.redux.actions.SomeOtherCounterActionIncrease
import com.denwehrle.boilerplate.redux.state.SomeOtherCounterState
import org.rekotlin.Action

fun someOtherCounterReducer(action: Action, counterState: SomeOtherCounterState?): SomeOtherCounterState {
    var state: SomeOtherCounterState = counterState ?: SomeOtherCounterState()
    when(action){
        is SomeOtherCounterActionIncrease -> {
            state = state.copy(someOtherCounterState = state.someOtherCounterState + 1)
        }
        is SomeOtherCounterActionDecrease -> {
            state = state.copy(someOtherCounterState = state.someOtherCounterState - 1)
        }
    }
    return state
}