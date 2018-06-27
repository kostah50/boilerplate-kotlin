package com.denwehrle.boilerplate.redux.middleware

import com.denwehrle.boilerplate.data.manager.state.StateDataManager
import com.denwehrle.boilerplate.redux.actions.LoadPersistentAppStateAction
import com.denwehrle.boilerplate.redux.actions.LoadPersistentContactsAction
import com.denwehrle.boilerplate.redux.actions.LoadPersistentStateAction
import com.denwehrle.boilerplate.redux.actions.SaveStateAction
import com.denwehrle.boilerplate.redux.state.AppState
import com.denwehrle.boilerplate.redux.state.ContactsState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware
import timber.log.Timber

/**
 * Middleware to intercept Network Related Actions
 *
 * @author Miguel Costa
 */
class PersistenceMiddleware(private val stateDataManager: StateDataManager) : Middleware<AppState> {
    override fun invoke(dispatch: DispatchFunction, state: () -> AppState?): (DispatchFunction) -> DispatchFunction {
        return { next ->
            { action ->
                next(action)
                when (action) {
                    is SaveStateAction -> {
                        var appState: AppState? = state()
                        if (appState != null) {
                            saveStatePersistently(appState)
                        }
                    }
                    is LoadPersistentStateAction -> {
                        loadStateFromPersistentStorage(dispatch)
                    }
                }
            }
        }
    }

    private fun saveStatePersistently(appState: AppState) {
        stateDataManager.saveStatePersistently(appState)
    }

    private fun loadStateFromPersistentStorage(dispatch: DispatchFunction) {
//        Timber.d(">>>>>>>>>>>>>>>>>>>>>>> loadStateFromPersistentStorage")
        stateDataManager.somethingNew()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            when (it) {
                                is AppState -> {
//                                    Timber.d(">>>>>>>>>>>>>>>>>>>>>>> AppState:$it")
                                    dispatch(LoadPersistentAppStateAction(it))
                                }
                                is ContactsState -> {
//                                    Timber.d(">>>>>>>>>>>>>>>>>>>>>>> else:$it")
                                    dispatch(LoadPersistentContactsAction(it.contacts))
                                }
                            }
                        },
                        onError = {
                            Timber.d("onError: $it")
                        }
                )

//        stateDataManager.loadAppStateFromPersistentStorage()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeBy(
//                        onSuccess = {
//                            Timber.d(">>>>>>>>>>>>>>>>>>>>>>> loadAppStateFromPersistentStorage:$it")
//                            dispatch(LoadPersistentAppStateAction(appState = it))
//                            stateDataManager.loadContactsFromPersistentStorage()
//                                    .subscribeOn(Schedulers.io())
//                                    .observeOn(AndroidSchedulers.mainThread())
//                                    .subscribeBy(
//                                            onSuccess = {
//                                                Timber.d(">>>>>>>>>>>>>>>>>>>>>>> loadContactsFromPersistentStorage:$it")
//                                                dispatch(LoadPersistentContactsAction(contacts = it))
//                                            },
//                                            onError = {
//                                                Timber.d(">>>>>>>>>>>>>>>>>>>>>>> onError:$it")
//                                            }
//                                    )
//                        },
//                        onError = {
//                            Timber.d(">>>>>>>>>>>>>>>>>>>>>>> onError:$it")
//                        }
//                )
    }

//    private fun getContacts(dispatch: DispatchFunction) {
//        stateDataManager.getContacts()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeBy(
//                        onSuccess = {
//                            dispatch(SyncContactsSuccessfulAction(it))
//                        },
//                        onError = {
//                            Timber.e(it)
//                        }
//                )
//    }
}