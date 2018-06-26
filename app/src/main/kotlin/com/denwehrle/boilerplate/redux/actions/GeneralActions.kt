package com.denwehrle.boilerplate.redux.actions

import android.app.Activity
import org.rekotlin.Action
import com.denwehrle.boilerplate.data.local.model.Contact

/**
 * General Loading Actions
 * Usually associated with loading animation Views
 *
 * @author Miguel Costa
 */
class StartLoadingAction: Action
class StopLoadingAction: Action


/**
 * Action sent when the user requests to load/refresh the users data
 * */
data class LoadContactsAction(val unit: Unit = Unit): Action

/**
 * Actions sent when the data is received from the REST api
 * */
data class LoadContactsSuccessfulAction(val contacts: List<Contact>): Action
class LoadContactsFailedAction: Action

/**
 * Actions sent when a contact is selected or dismissed
 * */
data class SelectedContactAction(val contact: Contact, val activity: Activity): Action
class ClearSelectedContactAction: Action


