package com.denwehrle.boilerplate.viewModel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.denwehrle.boilerplate.R
import com.denwehrle.boilerplate.data.remote.model.WelcomeData

class WelcomeViewModel : ViewModel() {

    private var welcomeData : MutableLiveData<List<WelcomeData>> = MutableLiveData()


    init {
        welcomeData.postValue(listOf(
                WelcomeData(R.string.welcome_title_1, R.string.welcome_text_1, R.drawable.ic_android),
                WelcomeData(R.string.welcome_title_2, R.string.welcome_text_2, R.drawable.ic_beach),
                WelcomeData(R.string.welcome_title_3, R.string.welcome_text_3, R.drawable.ic_cake)))
    }

    fun getWelcomeData() : LiveData<List<WelcomeData>> {
        return welcomeData
    }

}