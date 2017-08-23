package org.codedoesgood.mercury.mainactivitytests.Robots;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

 /**
  * The robot class should contain high-level actions that a standard QA team member
  * would perform. It should contain logic for how a value or an action would be taken,
  * but not specific values.
 */
public class MainActivityRobot {

    public MainActivityRobot verifyTextIsVisible(String textToCheck){
        onView(withText(textToCheck)).check(matches(isDisplayed()));
        return this;
    }
}
