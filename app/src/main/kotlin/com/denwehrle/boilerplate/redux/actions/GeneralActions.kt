package com.denwehrle.boilerplate.redux.actions

import org.rekotlin.Action

data class CounterActionIncrease(val unit: Unit = Unit): Action
data class CounterActionDecrease(val unit: Unit = Unit): Action

data class SomeOtherCounterActionIncrease(val unit: Unit = Unit): Action
data class SomeOtherCounterActionDecrease(val unit: Unit = Unit): Action