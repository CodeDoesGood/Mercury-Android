package org.codedoesgood.mercury.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.codedoesgood.mercury.R;
import org.codedoesgood.mercury.onboarding.view.OnboardingActivity;

/**
 * Default launch activity
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, ProjectListActivity.class);
        Intent intent = new Intent(this, OnboardingActivity.class);

        startActivity(intent);
    }
}
