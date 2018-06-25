package com.denwehrle.boilerplate.redux.state

import org.rekotlin.StateType

data class CounterState (
        val counter: Int = 0
): StateType