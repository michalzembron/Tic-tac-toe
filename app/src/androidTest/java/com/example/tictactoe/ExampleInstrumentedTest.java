package com.example.tictactoe;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule(MainActivity.class);

    @Test
    public void onCreate() throws Exception {

        /**
         * Singleplayer game test
         */
        onView(withId(R.id.buttonPlay)).perform(click());
        onView(withId(R.id.Button_Singleplayer)).perform(click());
        onView(withId(R.id.im_btn0)).perform(click());
        onView(withId(R.id.im_btn1)).perform(click());
        onView(withId(R.id.im_btn3)).perform(click());
        onView(withId(R.id.im_btn4)).perform(click());
        onView(withId(R.id.im_btn6)).perform(click());
        onView(withId(R.id.playerOneScore)).check(matches(withText("1")));
        onView(withId(R.id.resetGame)).perform(click());
        onView(withId(R.id.playerOneScore)).check(matches(withText("0")));
        pressBack();
        pressBack();

        /**
         * Statistics test
         */
        onView(withId(R.id.buttonStatistics)).perform(click());
        onView(withId(R.id.totalWinsAmount)).check(matches(not(withText("TBD"))));
        onView(withId(R.id.totalLostAmount)).check(matches(not(withText("TBD"))));
        onView(withId(R.id.totalDrawsAmount)).check(matches(not(withText("TBD"))));
        pressBack();

        /**
         * Challenges test
         */
        onView(withId(R.id.buttonChallenges)).perform(click());
        onView(withId(R.id.iloscWygranychMeczyTekst)).check(matches(not(withText("TextView"))));
        pressBack();

        /**
         * Skin changing test
         */
        onView(withId(R.id.buttonSettings)).perform(click());
        onView(withId(R.id.buttonSkins)).perform(click());
        onView(withId(R.id.skin_X)).perform(click());
        onView(withId(R.id.skin_x_0)).perform(click());
        onView(withId(R.id.skin_x_1)).perform(click());
        onView(withId(R.id.skin_x_2)).perform(click());
    }
}
