package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.redux.actions.ClearSelectedContactAction
import com.denwehrle.boilerplate.redux.actions.LoadPersistentContactsAction
import com.denwehrle.boilerplate.redux.actions.SyncContactsSuccessfulAction
import com.denwehrle.boilerplate.redux.actions.SelectedContactAction
import com.denwehrle.boilerplate.redux.state.ContactsState
import org.rekotlin.Action

/**
 * @author Miguel Costa
 */
fun contactsState(action: Action, contactsState: ContactsState?): ContactsState {
    var state: ContactsState = contactsState ?: ContactsState()
    when(action){
        is SyncContactsSuccessfulAction -> {
            state = state.copy(contacts = action.contacts)
        }
        is LoadPersistentContactsAction -> {
            state = state.copy(contacts = action.contacts)
        }
        is SelectedContactAction -> {
            state = state.copy(activeContact = action.contact)
        }
        is ClearSelectedContactAction -> {
            state = state.copy(activeContact = null)
        }
    }
    return state
}