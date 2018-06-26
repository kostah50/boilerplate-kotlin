package com.denwehrle.boilerplate.redux.reducers

import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.redux.actions.LoadContactsSuccessfulAction
import org.rekotlin.Action

/**
 * @author Miguel Costa
 */
fun contactsReducer(action: Action, contacts: List<Contact>?): List<Contact> {
    var state: List<Contact> = contacts ?: listOf()
    when(action){
        is LoadContactsSuccessfulAction -> {
            state = action.contacts
        }
    }
    return state
}