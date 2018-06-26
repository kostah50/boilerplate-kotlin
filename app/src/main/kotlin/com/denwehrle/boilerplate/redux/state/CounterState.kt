package com.denwehrle.boilerplate.redux.state

import org.rekotlin.StateType

/**
 * The [AppState] can be composed of sub-states
 *
 * If it is required that the AppState is composed of complex object, use [StateType] in
 * order to have access to functions like copy and as such
 *
 * @author Miguel Costa
 */
data class CounterState (
        val counter: Int = 0
): StateType