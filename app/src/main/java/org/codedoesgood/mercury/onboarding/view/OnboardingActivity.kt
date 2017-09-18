package org.codedoesgood.mercury.onboarding.view

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

import org.codedoesgood.mercury.MainApplication
import org.codedoesgood.mercury.R
import org.codedoesgood.mercury.api.MockApiResponseController
import org.codedoesgood.mercury.onboarding.viewmodel.OnboardingViewModel
import org.codedoesgood.mercury.projectlist.view.ProjectListActivity

/**
 * Activity holding the Login and Registration screens (fragments)
 */
open class OnboardingActivity : AppCompatActivity() {

    companion object {

        /**
         * Integer representing the location of the LoginFragment in the
         * [OnboardingPagerAdapter] Fragment array
         */
        val FRAGMENT_LOGIN = 0

        /**
         * Integer representing the location of the RegistrationFragment in the
         * [OnboardingPagerAdapter] Fragment array
         */
        val FRAGMENT_REGISTRATION = 1
    }

    private val viewpagerSize = 2
    private var viewPager: ViewPager? = null
    private val regististrationFragment = RegistrationFragment()
    private val loginFragment = LoginFragment()
    var onboardingViewModel: OnboardingViewModel? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.onboarding_activity)

        val application = application as MainApplication
        onboardingViewModel = application.onboardingViewModel

        initViewPager()
    }

    override fun onResume() {
        super.onResume()
    }

    /**
     * Initialize the ViewPager and add the Login/Registration fragments
     */
    fun initViewPager() {
        viewPager = findViewById(R.id.onboarding_viewpager)
        val viewPagerAdapter = OnboardingPagerAdapter(supportFragmentManager)

        viewPagerAdapter.add(FRAGMENT_LOGIN, loginFragment)
        viewPagerAdapter.add(FRAGMENT_REGISTRATION, regististrationFragment)
        viewPager!!.adapter = viewPagerAdapter
    }

    /**
     * Update the ViewPager to display the desired fragment.
     * @param position Use **FRAGMENT_LOGIN** or **FRAGMENT_REGISTRATION**
     * to represent one of the Fragments within this activity.
     */
    fun setViewPagerCurrentItem(position: Int) {
        viewPager!!.currentItem = position
    }


    /**
     * Launch the ProjectListActivity on successful authentication
     */
    fun launchProjectList() {
        startActivity(Intent(this, ProjectListActivity::class.java))
    }

    /**
     * Standard API to display a toast message
     * @param message The text String to display
     * @param duration The duration as `Toast.LENGTH_SHORT` or `Toast.LENGTH_LONG`
     */
    fun displayToast(message: String, duration: Int) {
        Toast.makeText(this, message, duration).show()
    }

    private inner class OnboardingPagerAdapter constructor(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragArray = arrayOfNulls<Fragment>(viewpagerSize)

        fun add(position: Int, frag: Fragment) {
            fragArray[position] = frag
        }

        override fun getItem(position: Int): Fragment? = fragArray[position]

        override fun getCount(): Int = fragArray.size
    }
}
