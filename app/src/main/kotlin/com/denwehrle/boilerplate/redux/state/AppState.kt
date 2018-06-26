package com.denwehrle.boilerplate.redux.state

import org.rekotlin.StateType
import com.denwehrle.boilerplate.data.local.model.Contact

/**
 * @author Miguel Costa
 */
data class AppState (
        val contacts: List<Contact> = listOf(),
        val isLoading: Boolean = false
//        val CounterState: CounterState = CounterState() - This is an example of a Composed State
): StateType