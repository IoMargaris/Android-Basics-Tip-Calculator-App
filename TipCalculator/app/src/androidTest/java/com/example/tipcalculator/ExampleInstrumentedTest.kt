package com.example.tipcalculator

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class CalculatorTests {

    @get:Rule
    val activity = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Test cases to cover all three tip options with Round Up switch enabled
     */
    @Test
    fun calculate_20_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("50.00"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(("Tip Amount: $10.00"))))
    }

    @Test
    fun calculate_18_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("100.00"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_eighteen_percent))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(("Tip Amount: $18.00"))))
    }

    @Test
    fun calculate_10_percent_tip() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("200.00"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_ten_percent))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(("Tip Amount: $20.00"))))
    }

    @Test
    fun calculate_10_percent_tip_round_up_off() {
        onView(withId(R.id.cost_of_service_edit_text))
            .perform(typeText("165.00"))
            .perform(ViewActions.closeSoftKeyboard())

        onView(withId(R.id.option_ten_percent))
            .perform(click())

        onView(withId(R.id.round_up_switch))
            .perform(click())

        onView(withId(R.id.calculate_button))
            .perform(click())

        onView(withId(R.id.tip_result))
            .check(matches(withText(("Tip Amount: $16.50"))))
    }

}