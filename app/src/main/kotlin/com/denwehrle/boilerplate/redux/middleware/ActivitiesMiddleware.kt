package com.denwehrle.boilerplate.redux.middleware

import android.content.Context
import android.content.Intent
import com.denwehrle.boilerplate.R
import com.denwehrle.boilerplate.redux.actions.LoadContactsAction
import com.denwehrle.boilerplate.redux.actions.SelectedContactAction
import com.denwehrle.boilerplate.redux.state.AppState
import com.denwehrle.boilerplate.ui.contact.detail.ContactDetailActivity
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware

/**
 * Middleware to intercept Network Related Actions
 *
 * @author Miguel Costa
 */
class ActivitiesMiddleware() : Middleware<AppState> {
    override fun invoke(dispatch: DispatchFunction, state: () -> AppState?): (DispatchFunction) -> DispatchFunction {
        return { next ->
            { action ->
                next(action)
                when (action) {
                /**
                 * When we have a [LoadContactsAction] we want to intercept it
                 * and do the Rest Request */
                    is SelectedContactAction -> {
                        launchContactDetailActivity(action)
                    }
                }
            }
        }
    }

    private fun launchContactDetailActivity(action: SelectedContactAction) {
        action.activity.startActivity(Intent(action.activity, ContactDetailActivity::class.java))
        action.activity.overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left)
    }
}