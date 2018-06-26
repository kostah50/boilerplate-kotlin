package com.denwehrle.boilerplate.ui.contact.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.denwehrle.boilerplate.R
import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.redux.actions.ClearSelectedContactAction
import com.denwehrle.boilerplate.redux.state.AppStore
import com.denwehrle.boilerplate.ui.base.BaseDetailActivity
import com.denwehrle.boilerplate.viewModel.ActiveContactViewModel
import com.denwehrle.boilerplate.viewModel.ContactsViewModel
import com.denwehrle.boilerplate.viewModel.LoadingViewModel
import kotlinx.android.synthetic.main.activity_contact_detail.*
import kotlinx.android.synthetic.main.content_contact.*
import kotlinx.android.synthetic.main.content_contact_detail.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Miguel Costa
 */
class ContactDetailActivity : BaseDetailActivity() {
    /** Store to be able to dispatch actions */
    @Inject
    lateinit var store: AppStore

    /** View Model Factory for the MVVM model and Dagger 2 injections */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        setUpUIComponents()
    }


    /********* UI Components Set Up *********/

    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
    }

    private fun setUpViewModels(){
        val activeContactViewModel = ViewModelProviders.of(this, viewModelFactory).get(ActiveContactViewModel::class.java)
        activeContactViewModel.getActiveContact().observe(this, Observer {
            showData(it)
        })
    }

    private fun setUpUIComponents(){
        setUpToolbar()
        setUpViewModels()
    }


    /**
     * We take the loaded data, set it to the corresponding adapter and notify the
     * UI that something has changed.
     */
    private fun showData(contact: Contact?) {
        title = contact?.email
        textName.text = contact?.toString()
    }

    override fun onDestroy() {
        store.dispatch(ClearSelectedContactAction())
        super.onDestroy()
    }
}