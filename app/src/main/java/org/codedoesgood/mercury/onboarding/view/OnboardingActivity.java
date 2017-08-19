package org.codedoesgood.mercury.onboarding.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import org.codedoesgood.mercury.MainApplication;
import org.codedoesgood.mercury.R;
import org.codedoesgood.mercury.onboarding.viewmodel.OnboardingViewModel;

/**
 * Activity holding the Login and Registration screens (fragments)
 */
public class OnboardingActivity extends AppCompatActivity {

    /**
     * Integer representing the location of the LoginFragment in the
     * {@link OnboardingPagerAdapter} Fragment array
     */
    public static final int FRAGMENT_LOGIN = 0;

    /**
     * Integer representing the location of the RegistrationFragment in the
     * {@link OnboardingPagerAdapter} Fragment array
     */
    public static final int FRAGMENT_REGISTRATION = 1;

    private final int viewpagerSize = 2;
    private ViewPager viewPager;
    private RegistrationFragment regististrationFragment = new RegistrationFragment();
    private LoginFragment loginFragment = new LoginFragment();
    private OnboardingViewModel onboardingViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_activity);

        MainApplication application = (MainApplication) getApplication();
        onboardingViewModel = application.getOnboardingViewModel();

        initViewPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Initialize the ViewPager and add the Login/Registration fragments
     */
    public void initViewPager() {
        viewPager = findViewById(R.id.onboarding_viewpager);
        OnboardingPagerAdapter viewPagerAdapter = new OnboardingPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.add(FRAGMENT_LOGIN, loginFragment);
        viewPagerAdapter.add(FRAGMENT_REGISTRATION, regististrationFragment);
        viewPager.setAdapter(viewPagerAdapter);
    }

    /**
     * Update the ViewPager to display the desired fragment.
     * @param position Use <b>FRAGMENT_LOGIN</b> or <b>FRAGMENT_REGISTRATION</b>
     *                 to represent one of the Fragments within this activity.
     */
    public void setViewPagerCurrentItem(int position) { viewPager.setCurrentItem(position); }

    public OnboardingViewModel getOnboardingViewModel() { return onboardingViewModel; }

    private final class OnboardingPagerAdapter extends FragmentPagerAdapter {

        private Fragment[] fragArray = new Fragment[viewpagerSize];

        private OnboardingPagerAdapter(FragmentManager fm) { super(fm); }

        private void add(int position, Fragment frag) { fragArray[position] = frag; }

        @Override
        public Fragment getItem(int position) {
            return fragArray[position];
        }

        @Override
        public int getCount() { return fragArray.length; }
    }

}
