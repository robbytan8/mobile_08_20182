package com.robby.mobile_08_20182;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.robby.mobile_08_20182.R;
import com.robby.mobile_08_20182.fragment.DetailFragment;
import com.robby.mobile_08_20182.fragment.MainFragment;

import butterknife.ButterKnife;

/**
 * @author Robby
 */
public class MainActivity extends AppCompatActivity {

    private MainFragment mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_container, getMainFragment());
        transaction.commit();
    }

    private MainFragment getMainFragment() {
        if (mainFragment == null) {
            mainFragment = new MainFragment();
        }
        return mainFragment;
    }
}