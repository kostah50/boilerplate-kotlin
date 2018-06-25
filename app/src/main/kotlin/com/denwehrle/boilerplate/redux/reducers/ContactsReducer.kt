package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.redux.actions.LoadedContactsSuccessfulAction
import org.rekotlin.Action


fun contactsReducer(action: Action, contacts: List<Contact>?): List<Contact> {
    var state: List<Contact> = contacts ?: listOf()
    when(action){
        is LoadedContactsSuccessfulAction -> {
            state = action.contacts
        }
    }
    return state
}