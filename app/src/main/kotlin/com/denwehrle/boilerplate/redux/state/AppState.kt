package com.denwehrle.boilerplate.redux.state

import org.rekotlin.StateType

data class AppState (
        val counter: CounterState = CounterState(),
        val someOtherCounter: SomeOtherCounterState = SomeOtherCounterState()
): StateType