/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.issei.omizu.weghtcalendar.data.repository.datasource;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.preference.Preference;

/**
 * {@link PhysicalMeasurementDataStore} implementation based on connections to the api (Cloud).
 */
public class SharedPreferenceDataStore implements PreferenceDataStore {

  private Preference preference;

  /**
   * Construct a {@link SharedPreferenceDataStore} based on connections to the api (Cloud).
   */
  SharedPreferenceDataStore(Preference preference) {
    this.preference = preference;
  }

  @Override
  public Observable<String> getAccountName() {
    return this.preference.getAccountName();
  }

  @Override
  public Observable<Boolean> putAccountName(final String accountName) {
    return this.preference.putAccountName(accountName);
  }
}
