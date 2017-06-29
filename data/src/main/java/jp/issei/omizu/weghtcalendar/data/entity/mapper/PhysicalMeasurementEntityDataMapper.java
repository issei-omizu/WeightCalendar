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
package jp.issei.omizu.weghtcalendar.data.entity.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;

/**
 * Mapper class used to transform {@link PhysicalMeasurementEntity} (in the data layer) to {@link PhysicalMeasurement} in the
 * domain layer.
 */
public class PhysicalMeasurementEntityDataMapper {

  @Inject
  PhysicalMeasurementEntityDataMapper() {}

  /**
   * Transform a {@link PhysicalMeasurementEntity} into an {@link PhysicalMeasurement}.
   *
   * @param physicalMeasurementEntity Object to be transformed.
   * @return {@link PhysicalMeasurement} if valid {@link PhysicalMeasurementEntity} otherwise null.
   */
  public PhysicalMeasurement transform(PhysicalMeasurementEntity physicalMeasurementEntity) {
    PhysicalMeasurement physicalMeasurement = null;
    if (physicalMeasurementEntity != null) {
      physicalMeasurement = new PhysicalMeasurement();
      physicalMeasurement.setDate(physicalMeasurementEntity.getDate());
      physicalMeasurement.setWeight(physicalMeasurementEntity.getWeight());
      physicalMeasurement.setBodyFatPercentage(physicalMeasurementEntity.getBodyFatPercentage());
      physicalMeasurement.setBodyTemperature(physicalMeasurementEntity.getBodyTemperature());
    }
    return physicalMeasurement;
  }

  /**
   * Transform a List of {@link PhysicalMeasurementEntity} into a Collection of {@link PhysicalMeasurement}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link PhysicalMeasurement} if valid {@link PhysicalMeasurementEntity} otherwise null.
   */
  public List<PhysicalMeasurement> transform(Collection<PhysicalMeasurementEntity> userEntityCollection) {
    final List<PhysicalMeasurement> physicalMeasurementList = new ArrayList<>();
    for (PhysicalMeasurementEntity physicalMeasurementEntity : userEntityCollection) {
      final PhysicalMeasurement physicalMeasurement = transform(physicalMeasurementEntity);
      if (physicalMeasurement != null) {
        physicalMeasurementList.add(physicalMeasurement);
      }
    }
    return physicalMeasurementList;
  }

  /**
   * Transform a {@link PhysicalMeasurement} into an {@link PhysicalMeasurementEntity}.
   *
   * @param physicalMeasurement Object to be transformed.
   * @return {@link PhysicalMeasurementEntity} if valid {@link PhysicalMeasurement} otherwise null.
   */
  public PhysicalMeasurementEntity transform(PhysicalMeasurement physicalMeasurement) {
    PhysicalMeasurementEntity physicalMeasurementEntity = null;
    if (physicalMeasurement != null) {
      physicalMeasurementEntity = new PhysicalMeasurementEntity();
      physicalMeasurementEntity.setDate(physicalMeasurement.getDate());
      physicalMeasurementEntity.setWeight(physicalMeasurement.getWeight());
      physicalMeasurementEntity.setBodyFatPercentage(physicalMeasurement.getBodyFatPercentage());
      physicalMeasurementEntity.setBodyTemperature(physicalMeasurement.getBodyTemperature());
    }
    return physicalMeasurementEntity;
  }

}
