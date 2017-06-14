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
package jp.issei.omizu.weghtcalendar.data.repository;

import javax.inject.Inject;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.repository.datasource.PreferenceDataStoreFactory;
import jp.issei.omizu.weghtcalendar.data.repository.datasource.SharedPreferenceDataStore;
import jp.issei.omizu.weightcalendar.domain.repository.PreferenceRepository;

/**
 * {@link PreferenceDataRepository} for retrieving physicalMeasurement data.
 */
public class PreferenceDataRepository implements PreferenceRepository {

  private final PreferenceDataStoreFactory preferenceDataStoreFactory;

  /**
   * Constructs a {@link PreferenceRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   */
  @Inject
  PreferenceDataRepository(PreferenceDataStoreFactory dataStoreFactory) {
    this.preferenceDataStoreFactory = dataStoreFactory;
  }

  @Override
  public Observable<String> getAccountName() {
    final SharedPreferenceDataStore sharedPreferenceDataStore = this.preferenceDataStoreFactory.create();
    return sharedPreferenceDataStore.getAccountName();
  }

  @Override
  public Observable<Boolean> putAccountName(String accountName) {
    final SharedPreferenceDataStore sharedPreferenceDataStore = this.preferenceDataStoreFactory.create();
    return sharedPreferenceDataStore.putAccountName(accountName);
  }
}
