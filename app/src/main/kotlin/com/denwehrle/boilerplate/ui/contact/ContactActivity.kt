package com.denwehrle.boilerplate.ui.contact

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.denwehrle.boilerplate.R
import com.denwehrle.boilerplate.data.local.model.Contact
import com.denwehrle.boilerplate.redux.actions.SelectedContactAction
import com.denwehrle.boilerplate.redux.actions.SyncAction
import com.denwehrle.boilerplate.redux.state.AppStore
import com.denwehrle.boilerplate.ui.base.BaseActivity
import com.denwehrle.boilerplate.util.extension.isNetworkConnected
import com.denwehrle.boilerplate.viewModel.ActiveContactViewModel
import com.denwehrle.boilerplate.viewModel.ContactsViewModel
import com.denwehrle.boilerplate.viewModel.LoadingViewModel
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.content_contact.*
import javax.inject.Inject

/**
 * @author Miguel Costa
 */
class ContactActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {
    /**
     * To make classed injectable make sure they have a constructor
     * with the @Inject annotation.
     */

    /** Store to be able to dispatch actions */
    @Inject
    lateinit var store: AppStore

    /** View Model Factory for the MVVM model and Dagger 2 injections */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var adapter: ContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        /** Set Up all the UI components that need so*/
        setUpUIComponents()

//        getContacts()
//        onRefresh()
    }

    /**
     * We check if a connection is available to trigger our sync and inform the user otherwise.
     */
    override fun onRefresh() {
        if (isNetworkConnected()) {
            store.dispatch(SyncAction(this))
        } else {
            swipeRefreshLayout.isRefreshing = false
            Snackbar.make(coordinatorLayout, R.string.snackbar_no_connection, Snackbar.LENGTH_LONG)
                    .setAction(R.string.snackbar_retry) {
                        onRefresh()
                    }
                    .setActionTextColor(resources.getColor(R.color.colorPrimary))
                    .show()
        }
    }


    /********* UI Components Set Up *********/
    private fun setUpToolbar() {
        setSupportActionBar(toolbar)
    }

    /**
     * Basic setup of the RecyclerView. We add an ItemDecoration for vertical divider
     * lines and ItemClickSupport to react to item clicks.
     */
    private fun setUpRecyclerAdapter() {
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))

        adapter.onItemClick = {
            store.dispatch(SelectedContactAction(it))
        }
    }

    /**
     * We change the color of the swipe layout spinner, to match our primary color.
     */
    private fun setUpSwipeRefresh() {
        val color: Int = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getColor(R.color.colorPrimary)
        } else {
            resources.getColor(R.color.colorPrimary)
        }
        swipeRefreshLayout.setColorSchemeColors(color)
        swipeRefreshLayout.setOnRefreshListener(this)
    }

    private fun setUpViewModels() {
        val isLoadingViewModel = ViewModelProviders.of(this, viewModelFactory).get(LoadingViewModel::class.java)
        isLoadingViewModel.isLoading().observe(this, Observer {
            swipeRefreshLayout.isRefreshing = it ?: false
        })

        val contactsViewModel = ViewModelProviders.of(this, viewModelFactory).get(ContactsViewModel::class.java)
        contactsViewModel.getContacts().observe(this, Observer {
            showData(it ?: listOf())
        })

        val activeContactViewModel = ViewModelProviders.of(this, viewModelFactory).get(ActiveContactViewModel::class.java)
        activeContactViewModel.getActiveContact().observe(this, Observer {
            if (it != null) {
                launchContactDetailActivity()
            }
        })
    }

    private fun setUpUIComponents() {
        setUpToolbar()
        setUpRecyclerAdapter()
        setUpSwipeRefresh()
        setUpViewModels()
    }

    /**
     * We take the loaded data, set it to the corresponding adapter and notify the
     * UI that something has changed.
     */
    private fun showData(contacts: List<Contact>) {
        swipeRefreshLayout.isRefreshing = false
        adapter.contacts = contacts
        adapter.notifyDataSetChanged()
    }

    private fun launchContactDetailActivity() {
        startActivity(Intent(this, com.denwehrle.boilerplate.ui.contact.detail.ContactDetailActivity::class.java))
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left)
    }
}