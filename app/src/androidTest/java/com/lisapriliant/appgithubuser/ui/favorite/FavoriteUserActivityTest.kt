package com.lisapriliant.appgithubuser.ui.favorite

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Assert.*
import org.junit.runner.RunWith
import org.junit.Test
import com.lisapriliant.appgithubuser.R

@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteUserActivityTest {

    @Test
    fun testFavoriteActivity() {
        val scenario = ActivityScenario.launch(FavoriteUserActivity::class.java)

        Thread.sleep(2000)
        Espresso.onView(ViewMatchers.withId(R.id.rv_favoriteUser))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        scenario.close()
    }
}