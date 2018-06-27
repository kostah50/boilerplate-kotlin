package com.denwehrle.boilerplate.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.denwehrle.boilerplate.R
import com.denwehrle.boilerplate.data.local.helper.PreferenceHelper
import com.denwehrle.boilerplate.data.manager.base.BaseDataManager
import com.denwehrle.boilerplate.data.manager.login.LoginDataManager
import com.denwehrle.boilerplate.data.remote.model.WelcomeData
import com.denwehrle.boilerplate.redux.state.AppStore
import org.rekotlin.StoreSubscriber
import javax.inject.Inject

/**
 * Welcome View Model
 * This View Model is only providing static data to observing Views
 * also sets the value of WelcomeDone in the Preference
 *
 * @author Duc Giang Le
 */

class WelcomeViewModel @Inject constructor(private val store: AppStore, private val preferenceHelper : PreferenceHelper): ViewModel(), StoreSubscriber<Boolean> {

    private var welcomeData : MutableLiveData<List<WelcomeData>> = MutableLiveData()

    init {
        welcomeData.postValue(listOf(
                WelcomeData(R.string.welcome_title_1, R.string.welcome_text_1, R.drawable.ic_android),
                WelcomeData(R.string.welcome_title_2, R.string.welcome_text_2, R.drawable.ic_beach),
                WelcomeData(R.string.welcome_title_3, R.string.welcome_text_3, R.drawable.ic_cake)))

        store.subscribe(this) {
            it.select {
                it.isWelcomeDone
            }.skipRepeats()
        }
    }

    override fun newState(state: Boolean) {
        preferenceHelper.welcomeDone = state
    }

    fun getWelcomeData() : LiveData<List<WelcomeData>> {
        return welcomeData
    }

    override fun onCleared() {
        super.onCleared()
        store.unsubscribe(this)
    }

}