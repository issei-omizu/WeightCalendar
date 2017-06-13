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
package jp.issei.omizu.weghtcalendar.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import javax.annotation.Nonnull;
import javax.inject.Inject;

import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntitySheetsApiMapper;
import jp.issei.omizu.weghtcalendar.data.google.GoogleApi;
import jp.issei.omizu.weghtcalendar.data.google.GoogleApiImpl;
import jp.issei.omizu.weghtcalendar.data.preference.Preference;
import jp.issei.omizu.weghtcalendar.data.realm.PhysicalMeasurementRealm;

/**
 * Factory that creates different implementations of {@link PhysicalMeasurementDataStore}.
 */
public class PreferenceDataStoreFactory {

  private final Context context;
  private final Preference preference;

  @Inject
  PreferenceDataStoreFactory(@NonNull Context context, @NonNull Preference preference) {
    this.context = context.getApplicationContext();
    this.preference = preference;
  }

  /**
   * Create {@link SharedPreferenceDataStore} from a physicalMeasurement id.
   */
  public SharedPreferenceDataStore create() {
    return new SharedPreferenceDataStore(this.preference);
  }
}
