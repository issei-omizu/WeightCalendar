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
package jp.issei.omizu.weightcalendar.presentation.di.components;

import dagger.Component;
import jp.issei.omizu.weightcalendar.presentation.di.PerActivity;
import jp.issei.omizu.weightcalendar.presentation.di.modules.ActivityModule;
import jp.issei.omizu.weightcalendar.presentation.di.modules.WorkoutModule;
import jp.issei.omizu.weightcalendar.presentation.view.activity.WorkoutActivity;

/**
 * A scope {@link jp.issei.omizu.weightcalendar.presentation.di.PerActivity} component.
 * Injects physicalMeasurement specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, WorkoutModule.class})
public interface WorkoutComponent extends ActivityComponent {
    void inject(WorkoutActivity workoutActivity);
}
