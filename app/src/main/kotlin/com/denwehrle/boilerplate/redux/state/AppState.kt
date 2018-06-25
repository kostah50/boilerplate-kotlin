package com.denwehrle.boilerplate.redux.state

import org.rekotlin.StateType
import com.denwehrle.boilerplate.data.local.model.Contact

data class AppState (
        val contacts: List<Contact> = listOf(),
        val isLoading: Boolean = false
): StateType