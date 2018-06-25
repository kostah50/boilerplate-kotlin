package com.denwehrle.boilerplate.redux.middleware

import android.util.Log
import com.denwehrle.boilerplate.redux.actions.SomeOtherCounterActionIncrease
import com.denwehrle.boilerplate.redux.state.AppState
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware

class NetworkMiddleware: Middleware<AppState>{
    override fun invoke(p1: DispatchFunction, p2: () -> AppState?): (DispatchFunction) -> DispatchFunction {
        return { next ->
            { action ->
                next(action)
                when (action) {
                    is SomeOtherCounterActionIncrease -> {
                        Log.d("networkMiddleWare", action.toString())
                    }
                }
            }
        }
    }
}