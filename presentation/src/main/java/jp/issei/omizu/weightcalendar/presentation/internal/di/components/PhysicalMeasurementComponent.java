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
package jp.issei.omizu.weightcalendar.presentation.internal.di.components;

import dagger.Component;
import jp.issei.omizu.weightcalendar.presentation.internal.di.PerActivity;
import jp.issei.omizu.weightcalendar.presentation.internal.di.modules.ActivityModule;
import jp.issei.omizu.weightcalendar.presentation.internal.di.modules.PhysicalMeasurementModule;
import jp.issei.omizu.weightcalendar.presentation.internal.di.modules.PreferenceModule;
import jp.issei.omizu.weightcalendar.presentation.view.activity.PhysicalMeasurementActivity;
import jp.issei.omizu.weightcalendar.presentation.view.activity.PhysicalMeasurementChartActivity;
import jp.issei.omizu.weightcalendar.presentation.view.activity.PhysicalMeasurementInputActivity;

/**
 * A scope {@link PerActivity} component.
 * Injects physicalMeasurement specific Fragments.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, PhysicalMeasurementModule.class, PreferenceModule.class})
public interface PhysicalMeasurementComponent extends ActivityComponent {
    void inject(PhysicalMeasurementActivity physicalMeasurementActivity);
    void inject(PhysicalMeasurementChartActivity physicalMeasurementChartActivity);
    void inject(PhysicalMeasurementInputActivity physicalMeasurementInputActivity);
}
