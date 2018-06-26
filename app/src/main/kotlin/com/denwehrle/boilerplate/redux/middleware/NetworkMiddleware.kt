package com.denwehrle.boilerplate.redux.middleware

import com.denwehrle.boilerplate.redux.actions.SyncAction
import com.denwehrle.boilerplate.redux.state.AppState
import com.denwehrle.boilerplate.util.sync.SyncUtils
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware

/**
 * Middleware to intercept Network Related Actions
 *
 * @author Miguel Costa
 */
class NetworkMiddleware: Middleware<AppState> {
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