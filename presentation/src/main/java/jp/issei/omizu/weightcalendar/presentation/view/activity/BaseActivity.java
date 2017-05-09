package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.presentation.WeightCalendarApplication;
import jp.issei.omizu.weightcalendar.presentation.di.components.ApplicationComponent;
import jp.issei.omizu.weightcalendar.presentation.di.modules.ActivityModule;
import jp.issei.omizu.weightcalendar.presentation.navigation.Navigator;

/**
 * Base {@link Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends Activity {

  @Inject
  Navigator navigator;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    this.getApplicationComponent().inject(this);
  }

  /**
   * Adds a {@link Fragment} to this activity's layout.
   *
   * @param containerViewId The container view to where add the fragment.
   * @param fragment The fragment to be added.
   */
  protected void addFragment(int containerViewId, Fragment fragment) {
    final FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
    fragmentTransaction.add(containerViewId, fragment);
    fragmentTransaction.commit();
  }

  /**
   * Get the Main Application component for dependency injection.
   *
   */
  protected ApplicationComponent getApplicationComponent() {
    return ((WeightCalendarApplication) getApplication()).getApplicationComponent();
  }

  /**
   * Get an Activity module for dependency injection.
   *
   */
  protected ActivityModule getActivityModule() {
    return new ActivityModule(this);
  }
}
