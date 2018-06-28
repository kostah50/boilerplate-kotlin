package com.denwehrle.boilerplate.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.denwehrle.boilerplate.data.local.helper.PreferenceHelper
import com.denwehrle.boilerplate.data.manager.login.LoginDataManager
import com.denwehrle.boilerplate.data.remote.model.LoginResult
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val dataManager : LoginDataManager, private val preferenceHelper: PreferenceHelper): ViewModel() {

    private val loginStatus : MutableLiveData<LoginResult> = MutableLiveData()
    private var isWelcomeDone : MutableLiveData<Boolean> = MutableLiveData()

    init {
        isWelcomeDone.postValue(preferenceHelper.welcomeDone)
    }

    fun signIn(username: String, password: String) {
        dataManager.signIn(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onSuccess = {

                        }
                        ,
                        onError = {

                        }
                )
    }

    fun isWelcomeDone() : LiveData<Boolean> {
        return isWelcomeDone
    }

}