/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.issei.omizu.weightcalendar.presentation.navigation;

import android.content.Context;
import android.content.Intent;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.presentation.view.activity.PhysicalMeasurementActivity;
import jp.issei.omizu.weightcalendar.presentation.view.activity.PhysicalMeasurementChartActivity;
import jp.issei.omizu.weightcalendar.presentation.view.activity.PhysicalMeasurementInputActivity;
import jp.issei.omizu.weightcalendar.presentation.view.activity.SettingsActivity;
import jp.issei.omizu.weightcalendar.presentation.view.activity.WorkoutActivity;
import jp.issei.omizu.weightcalendar.presentation.viewmodel.PhysicalMeasurementViewModel;

/**
 * Class used to navigate through the application.
 */
public class Navigator {

  @Inject
  public Navigator() {
    //empty
  }

  /**
   * Goes to the physicalMeasurement list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToWorkout(Context context) {
    if (context != null) {
      Intent intentToLaunch = WorkoutActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the physicalMeasurement list screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToPhysicalMeasurement(Context context) {
    if (context != null) {
      Intent intentToLaunch = PhysicalMeasurementActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the physicalMeasurement Input screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToPhysicalMeasurementInput(Context context) {
    if (context != null) {
      Intent intentToLaunch = PhysicalMeasurementInputActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the physicalMeasurement Chart screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToPhysicalMeasurementChart(Context context) {
    if (context != null) {
      Intent intentToLaunch = PhysicalMeasurementChartActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }

  /**
   * Goes to the Settings screen.
   *
   * @param context A Context needed to open the destiny activity.
   */
  public void navigateToSettings(Context context) {
    if (context != null) {
      Intent intentToLaunch = SettingsActivity.getCallingIntent(context);
      context.startActivity(intentToLaunch);
    }
  }


//  /**
//   * Goes to the physicalMeasurement list screen.
//   *
//   * @param context A Context needed to open the destiny activity.
//   */
//  public void navigateToUserList(Context context) {
//    if (context != null) {
//      Intent intentToLaunch = UserListActivity.getCallingIntent(context);
//      context.startActivity(intentToLaunch);
//    }
//  }
//
//  /**
//   * Goes to the physicalMeasurement details screen.
//   *
//   * @param context A Context needed to open the destiny activity.
//   */
//  public void navigateToUserDetails(Context context, int userId) {
//    if (context != null) {
//      Intent intentToLaunch = UserDetailsActivity.getCallingIntent(context, userId);
//      context.startActivity(intentToLaunch);
//    }
//  }
}
