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
package jp.issei.omizu.weghtcalendar.data.realm;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;

/**
 * An interface representing a user Cache.
 */
public interface PhysicalMeasurementRealm {
  /**
   * Retrieves an {@link Observable} which will emit a List of {@link PhysicalMeasurementEntity}.
   */
  Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList();

  /**
   * Gets an {@link Observable} which will emit a {@link PhysicalMeasurementEntity}.
   *
   * @param date The user id to retrieve data.
   */
  Observable<PhysicalMeasurementEntity> get(final Date date);

  Observable<PhysicalMeasurementEntity> set(final PhysicalMeasurementEntity physicalMeasurementEntity);

  /**
   * Puts and element into the cache.
   *
   * @param physicalMeasurementEntity Element to insert in the cache.
   */
  void put(List<PhysicalMeasurementEntity> physicalMeasurementEntity);

  void deleteAll();

}
