package org.codedoesgood.mercury;

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.test.espresso.matcher.ViewMatchers
import android.support.v7.widget.RecyclerView
import android.view.View
import org.hamcrest.Matcher
import org.hamcrest.Matchers


/**
 * Custom view assertion used to verify the number of items in the RecyclerView
 */
class RecyclerViewItemCountAssertion() : ViewAssertion {
    lateinit var matcher: Matcher<Int>

    // For testing an actual amount
    constructor(expectedAmount: Int) : this() {
        matcher = Matchers.`is`(expectedAmount)
    }

    // For testing greaterThan(1) or lessThan(5)
    constructor(match: Matcher<Int>) : this() {
        matcher = match
    }

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        ViewMatchers.assertThat(adapter.itemCount, matcher)
    }
}