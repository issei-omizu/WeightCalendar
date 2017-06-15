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
package jp.issei.omizu.weightcalendar.presentation.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.presentation.di.PerActivity;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;

/**
 * Mapper class used to transform {@link PhysicalMeasurement} (in the domain layer) to {@link PhysicalMeasurementModel} in the
 * presentation layer.
 */
@PerActivity
public class PhysicalMeasurementModelDataMapper {

  @Inject
  public PhysicalMeasurementModelDataMapper() {}

  /**
   * Transform a {@link PhysicalMeasurement} into an {@link PhysicalMeasurementModel}.
   *
   * @param physicalMeasurement Object to be transformed.
   * @return {@link PhysicalMeasurementModel}.
   */
  public PhysicalMeasurementModel transform(PhysicalMeasurement physicalMeasurement) {
    if (physicalMeasurement == null) {
      throw new IllegalArgumentException("Cannot transform a null value");
    }
    final PhysicalMeasurementModel physicalMeasurementModel = new PhysicalMeasurementModel();
    physicalMeasurementModel.setCoverUrl(physicalMeasurement.getCoverUrl());
    physicalMeasurementModel.setFullName(physicalMeasurement.getFullName());
    physicalMeasurementModel.setEmail(physicalMeasurement.getEmail());
    physicalMeasurementModel.setDescription(physicalMeasurement.getDescription());
    physicalMeasurementModel.setFollowers(physicalMeasurement.getFollowers());

    physicalMeasurementModel.setDate(physicalMeasurement.getDate());
    physicalMeasurementModel.setWeight(physicalMeasurement.getWeight());
    physicalMeasurementModel.setBodyFatPercentage(physicalMeasurement.getBodyFatPercentage());
    physicalMeasurementModel.setBodyTemperature(physicalMeasurement.getBodyTemperature());

    return physicalMeasurementModel;
  }

  /**
   * Transform a Collection of {@link PhysicalMeasurement} into a Collection of {@link PhysicalMeasurementModel}.
   *
   * @param physicalMeasurements Objects to be transformed.
   * @return List of {@link PhysicalMeasurementModel}.
   */
  public Collection<PhysicalMeasurementModel> transform(Collection<PhysicalMeasurement> physicalMeasurements) {
    Collection<PhysicalMeasurementModel> userModelsCollection;

    if (physicalMeasurements != null && !physicalMeasurements.isEmpty()) {
      userModelsCollection = new ArrayList<>();
      for (PhysicalMeasurement physicalMeasurement : physicalMeasurements) {
        userModelsCollection.add(transform(physicalMeasurement));
      }
    } else {
      userModelsCollection = Collections.emptyList();
    }

    return userModelsCollection;
  }
}
