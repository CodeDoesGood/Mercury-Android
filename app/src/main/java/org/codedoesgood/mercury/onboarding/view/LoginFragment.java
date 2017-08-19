package org.codedoesgood.mercury.onboarding.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.codedoesgood.mercury.R;

/**
 * Represents the Login screen
 */
public class LoginFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        initViews();
    }

    /**
     * Initialize views, attach adapters and listeners.
     */
    public void initViews() {
        View accountExists = getActivity().findViewById(R.id.button_register);
        accountExists.setOnClickListener(view -> {
            OnboardingActivity activity = (OnboardingActivity) getActivity();
            activity.setViewPagerCurrentItem(activity.FRAGMENT_REGISTRATION);
        });
    }
}
