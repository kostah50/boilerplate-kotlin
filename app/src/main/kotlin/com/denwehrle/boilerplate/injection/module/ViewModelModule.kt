package com.denwehrle.boilerplate.injection.module

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.denwehrle.boilerplate.viewModel.ContactsViewModel

import com.denwehrle.boilerplate.viewModel.GithubViewModelFactory
import com.denwehrle.boilerplate.viewModel.LoadingViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
internal abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(LoadingViewModel::class)
    internal abstract fun bindLoadingViewModel(loadingViewModel: LoadingViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ContactsViewModel::class)
    internal abstract fun bindContactsViewModel(contactsViewModel: ContactsViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: GithubViewModelFactory): ViewModelProvider.Factory
}