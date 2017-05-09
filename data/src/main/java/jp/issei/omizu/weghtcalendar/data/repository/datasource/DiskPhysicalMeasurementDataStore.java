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

import java.util.List;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.cache.PhysicalMeasurementCache;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;

/**
 * {@link PhysicalMeasurementDataStore} implementation based on file system data store.
 */
class DiskPhysicalMeasurementDataStore implements PhysicalMeasurementDataStore {

  private final PhysicalMeasurementCache physicalMeasurementCache;

  /**
   * Construct a {@link PhysicalMeasurementDataStore} based file system data store.
   *
   * @param physicalMeasurementCache A {@link PhysicalMeasurementCache} to cache data retrieved from the api.
   */
  DiskPhysicalMeasurementDataStore(PhysicalMeasurementCache physicalMeasurementCache) {
    this.physicalMeasurementCache = physicalMeasurementCache;
  }

  @Override
  public Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList() {
    //TODO: implement simple cache for storing/retrieving collections of physicalMeasurements.
    throw new UnsupportedOperationException("Operation is not available!!!");
  }

  @Override
  public Observable<PhysicalMeasurementEntity> physicalMeasurementEntityDetails(final int userId) {
     return this.physicalMeasurementCache.get(userId);
  }
}
