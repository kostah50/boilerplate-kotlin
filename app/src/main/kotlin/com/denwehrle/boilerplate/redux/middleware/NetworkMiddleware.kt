package com.denwehrle.boilerplate.redux.middleware

import com.denwehrle.boilerplate.data.manager.contact.ContactDataManager
import com.denwehrle.boilerplate.redux.actions.LoadContactsAction
import com.denwehrle.boilerplate.redux.actions.LoadContactsFailedAction
import com.denwehrle.boilerplate.redux.actions.LoadContactsSuccessfulAction
import com.denwehrle.boilerplate.redux.actions.SyncAction
import com.denwehrle.boilerplate.redux.state.AppState
import com.denwehrle.boilerplate.util.sync.SyncUtils
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
class NetworkMiddleware(val contactDataManager: ContactDataManager) : Middleware<AppState> {
    override fun invoke(dispatch: DispatchFunction, state: () -> AppState?): (DispatchFunction) -> DispatchFunction {
        return { next ->
            { action ->
                next(action)
                when (action) {
                /**
                 * When we have a [SyncAction] we want to intercept it
                 * and do the Sync Request */
                    is SyncAction -> {
                        SyncUtils.triggerRefresh(context = action.context)
                    }
                    is LoadContactsAction -> {
                        contactDataManager.getContactsAsSingle()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeBy(
                                        onSuccess = {
                                            dispatch(LoadContactsSuccessfulAction(it))
                                        },
                                        onError = {
                                            Timber.e(it)
                                            dispatch(LoadContactsFailedAction())
                                        }
                                )
                    }
                }
            }
        }
    }

//    private fun getContacts(dispatch: DispatchFunction) {
//        Timber.d("NetworkMiddleware - getContacts")
//        dataManager.getContacts().singleOrError()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeBy(
//                        onSuccess = {
//                            dispatch(LoadContactsSuccessfulAction(it))
//                        },
//                        onError = {
//                            Timber.e(it)
//                            dispatch(LoadContactsFailedAction())
//                        }
//                )
//    }
}