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

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weghtcalendar.data.google.GoogleApi;
import jp.issei.omizu.weghtcalendar.data.realm.PhysicalMeasurementRealm;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;

/**
 * {@link PhysicalMeasurementDataStore} implementation based on connections to the api (Cloud).
 */
class CloudPhysicalMeasurementDataStore implements PhysicalMeasurementDataStore {

  private final GoogleApi googleApi;
  private final PhysicalMeasurementRealm physicalMeasurementRealm;

  /**
   * Construct a {@link PhysicalMeasurementDataStore} based on connections to the api (Cloud).
   *
   * @param googleApi The {@link GoogleApi} implementation to use.
   * @param physicalMeasurementRealm A {@link PhysicalMeasurementRealm} to cache data retrieved from the api.
   */
  CloudPhysicalMeasurementDataStore(GoogleApi googleApi, PhysicalMeasurementRealm physicalMeasurementRealm) {
    this.googleApi = googleApi;
    this.physicalMeasurementRealm = physicalMeasurementRealm;
  }

  @Override
  public Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList() {
    return this.googleApi.physicalMeasurementEntityList().doOnNext(CloudPhysicalMeasurementDataStore.this.physicalMeasurementRealm::put);
//    return this.physicalMeasurementRealm.physicalMeasurementEntityList();
  }

  @Override
  public Observable<PhysicalMeasurementEntity> physicalMeasurementEntityDetails(final Date date) {
    return this.physicalMeasurementRealm.get(date);
//    .doOnNext(CloudPhysicalMeasurementDataStore.this.physicalMeasurementRealm::put);
  }

  @Override
  public Observable<PhysicalMeasurementEntity> setPhysicalMeasurementEntityDetails(final PhysicalMeasurementEntity physicalMeasurementEntity) {
    return null;
  }
}
