package com.denwehrle.boilerplate.ui.welcome

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.View
import com.denwehrle.boilerplate.R
import com.denwehrle.boilerplate.ui.base.BaseActivity
import com.denwehrle.boilerplate.ui.login.LoginActivity
import com.denwehrle.boilerplate.ui.welcome.section.WelcomeSectionFragment
import com.denwehrle.boilerplate.viewModel.WelcomeViewModel
import kotlinx.android.synthetic.main.content_welcome.*
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Dennis Wehrle
 */
class WelcomeActivity : BaseActivity(), ViewPager.OnPageChangeListener, View.OnClickListener {

    /**
     * To make classed injectable make sure they have a constructor
     * with the @Inject annotation.
     */
    @Inject
    lateinit var adapter: WelcomeAdapter

    /**
     * For AndroidInjection.inject(this) to work the Activity/Fragment/Service has to be
     * registered in injection/module/BindingModule. Make sure it's called before
     * super.onCreate() to prevent unexpected crashed if the task gets suspended by the OS.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        setUpViewModels()
        setUpClickListener()
    }

    /**
     * We check the scrolling position to toggle between the next
     * and done button if we reach the last page.
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        if (position == adapter.count - 1) {
            nextButton.visibility = View.GONE
            doneButton.visibility = View.VISIBLE
        } else {
            nextButton.visibility = View.VISIBLE
            doneButton.visibility = View.GONE
        }
    }

    /**
     * We respond to the click events by ether finish the
     * activity or show the next screen.
     */
    override fun onClick(view: View) {
        when (view.id) {
            skipButton.id, doneButton.id -> {
                //presenter.setWelcomeDone()
                val intent = Intent(application, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left)
                finish()
            }
            nextButton.id -> viewPager.setCurrentItem(viewPager.currentItem + 1, true)
        }
    }

    override fun onPageSelected(position: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}

    private fun setUpViewModels() {
        var welcomeViewModel = ViewModelProviders.of(this@WelcomeActivity).get(WelcomeViewModel::class.java)
        welcomeViewModel.getWelcomeData().observe(this@WelcomeActivity, Observer {
            it?.forEach {
                adapter.addFragment(WelcomeSectionFragment.newInstance(it.title, it.text, it.image))
            }

            viewPager.adapter = adapter
            viewPager.addOnPageChangeListener(this)
            adapter.notifyDataSetChanged()
        })
    }

    private fun setUpClickListener() {
        skipButton.setOnClickListener(this)
        nextButton.setOnClickListener(this)
        doneButton.setOnClickListener(this)
    }
}