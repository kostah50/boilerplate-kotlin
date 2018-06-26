package com.denwehrle.boilerplate.redux.state

import org.rekotlin.StateType
import com.denwehrle.boilerplate.data.local.model.Contact

/**
 * @author Miguel Costa
 */
data class AppState (
        val isLoading: Boolean = false,
        val contactsState: ContactsState = ContactsState()
): StateType