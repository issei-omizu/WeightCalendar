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

import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;

/**
 * Mapper class used to transform {@link PhysicalMeasurementEntity} (in the data layer) to {@link PhysicalMeasurement} in the
 * domain layer.
 */
public class PhysicalMeasurementEntitySheetsApiMapper {

  @Inject
  public PhysicalMeasurementEntitySheetsApiMapper() {}

  /**
   * Transform a {@link PhysicalMeasurementEntity} into an {@link PhysicalMeasurement}.
   *
   * @param physicalMeasurementEntity Object to be transformed.
   * @return {@link PhysicalMeasurement} if valid {@link PhysicalMeasurementEntity} otherwise null.
   */
  public PhysicalMeasurement transform(PhysicalMeasurementEntity physicalMeasurementEntity) {
    PhysicalMeasurement physicalMeasurement = null;
    if (physicalMeasurementEntity != null) {
      physicalMeasurement = new PhysicalMeasurement(physicalMeasurementEntity.getUserId());
      physicalMeasurement.setCoverUrl(physicalMeasurementEntity.getCoverUrl());
      physicalMeasurement.setFullName(physicalMeasurementEntity.getFullname());
      physicalMeasurement.setDescription(physicalMeasurementEntity.getDescription());
      physicalMeasurement.setFollowers(physicalMeasurementEntity.getFollowers());
      physicalMeasurement.setEmail(physicalMeasurementEntity.getEmail());
    }
    return physicalMeasurement;
  }

  /**
   * Transform a List of {@link PhysicalMeasurementEntity} into a Collection of {@link PhysicalMeasurementEntity}.
   *
   * @param values Object Collection to be transformed.
   * @return {@link PhysicalMeasurement} if valid {@link PhysicalMeasurementEntity} otherwise null.
   */
  public List<PhysicalMeasurementEntity> transform(List<List<Object>> values) {
    final List<PhysicalMeasurementEntity> physicalMeasurementList = new ArrayList<>(20);
//    for (PhysicalMeasurementEntity physicalMeasurementEntity : userEntityCollection) {
//      final PhysicalMeasurement physicalMeasurement = transform(physicalMeasurementEntity);
//      if (physicalMeasurement != null) {
//        physicalMeasurementList.add(physicalMeasurement);
//      }
//    }
    int rangeStart = 2;

    // 取得したデータをMapに展開
    List<String> listData;
    Integer count = rangeStart;
    if (values != null) {
      for (List row : values) {
        listData = new ArrayList<>();
        listData.add(count.toString());
        if (row.size() > 1) {
          listData.add(row.get(1).toString());
          listData.add(row.get(2).toString());
        }
//        mapWeight.put(row.get(0).toString(), listData);
        count++;
      }
    }


    return physicalMeasurementList;
  }
}
