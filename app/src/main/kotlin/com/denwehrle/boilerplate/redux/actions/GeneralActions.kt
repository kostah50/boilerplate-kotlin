package com.denwehrle.boilerplate.redux.actions

import org.rekotlin.Action
import com.denwehrle.boilerplate.data.local.model.Contact

data class CounterActionIncrease(val unit: Unit = Unit): Action
data class CounterActionDecrease(val unit: Unit = Unit): Action

data class SomeOtherCounterActionIncrease(val unit: Unit = Unit): Action
data class SomeOtherCounterActionDecrease(val unit: Unit = Unit): Action

class StartLoadingAction: Action
class StopLoadingAction: Action

data class LoadContactsAction(val unit: Unit = Unit): Action
data class LoadedContactsSuccessfulAction(val contacts: List<Contact>): Action
