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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntityDataMapper;
import jp.issei.omizu.weghtcalendar.data.repository.datasource.PhysicalMeasurementDataStore;
import jp.issei.omizu.weghtcalendar.data.repository.datasource.PhysicalMeasurementDataStoreFactory;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.repository.PhysicalMeasurementRepository;

/**
 * {@link PhysicalMeasurementRepository} for retrieving physicalMeasurement data.
 */
public class PhysicalMeasurementDataRepository implements PhysicalMeasurementRepository {

  private final PhysicalMeasurementDataStoreFactory physicalMeasurementDataStoreFactory;
  private final PhysicalMeasurementEntityDataMapper physicalMeasurementEntityDataMapper;

  /**
   * Constructs a {@link PhysicalMeasurementRepository}.
   *
   * @param dataStoreFactory A factory to construct different data source implementations.
   * @param physicalMeasurementEntityDataMapper {@link PhysicalMeasurementEntityDataMapper}.
   */
  @Inject
  PhysicalMeasurementDataRepository(PhysicalMeasurementDataStoreFactory dataStoreFactory,
                                    PhysicalMeasurementEntityDataMapper physicalMeasurementEntityDataMapper) {
    this.physicalMeasurementDataStoreFactory = dataStoreFactory;
    this.physicalMeasurementEntityDataMapper = physicalMeasurementEntityDataMapper;
  }

  @Override
  public Observable<List<PhysicalMeasurement>> physicalMeasurements() {
    //we always get all physicalMeasurements from the cloud
    final PhysicalMeasurementDataStore physicalMeasurementDataStore = this.physicalMeasurementDataStoreFactory.createCloudDataStore();
    return physicalMeasurementDataStore.physicalMeasurementEntityList().map(this.physicalMeasurementEntityDataMapper::transform);
  }

  @Override
  public Observable<PhysicalMeasurement> physicalMeasurement(int userId) {
    final PhysicalMeasurementDataStore physicalMeasurementDataStore = this.physicalMeasurementDataStoreFactory.create(userId);
    return physicalMeasurementDataStore.physicalMeasurementEntityDetails(userId).map(this.physicalMeasurementEntityDataMapper::transform);
  }
}
