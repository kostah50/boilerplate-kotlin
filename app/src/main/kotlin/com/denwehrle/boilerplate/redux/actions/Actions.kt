package com.denwehrle.boilerplate.redux.actions

import android.content.Context
import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.redux.state.AppState
import org.rekotlin.Action

/**
 * General Loading Actions
 * Usually associated with loading animation Views
 *
 * @author Miguel Costa
 */
class StartLoadingAction: Action
class StopLoadingAction: Action


/**
 * Actions sent when the user requests to load & refresh the users data
 * */
data class SyncAction(val context: Context): Action
data class SyncContactsSuccessfulAction(val contacts: List<Contact>): SaveStateAction

/**
 * Actions sent when a contact is selected or dismissed
 * */
data class SelectedContactAction(val contact: Contact): Action
class ClearSelectedContactAction: Action

/**
 * Persistence Actions
 * */
class LoadPersistentStateAction: Action
class LoadPersistentAppStateAction(val appState: AppState): Action
class LoadPersistentContactsAction(val contacts: List<Contact>): Action
interface SaveStateAction: Action

/**
 * Action sent when the welcome screen is skipped or done
 */
class SetWelcomeDone: Action