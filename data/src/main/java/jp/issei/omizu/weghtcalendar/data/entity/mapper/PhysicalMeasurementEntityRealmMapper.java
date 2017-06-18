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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.realm.RealmResults;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weghtcalendar.data.realm.PhysicalMeasurement;

import static java.lang.Float.parseFloat;

/**
 * Mapper class used to transform {@link PhysicalMeasurementEntity} (in the data layer) to {@link PhysicalMeasurement} in the
 * domain layer.
 */
public class PhysicalMeasurementEntityRealmMapper {

  @Inject
  public PhysicalMeasurementEntityRealmMapper() {}

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

      if (physicalMeasurementEntity.getWeight() != null && !physicalMeasurementEntity.getWeight().isEmpty()) {
        physicalMeasurement.setWeight(parseFloat(physicalMeasurementEntity.getWeight()));
      }

      if (physicalMeasurementEntity.getBodyFatPercentage() != null && !physicalMeasurementEntity.getBodyFatPercentage().isEmpty()) {
        physicalMeasurement.setBodyFatPercentage(parseFloat(physicalMeasurementEntity.getBodyFatPercentage()));
      }

      if (physicalMeasurementEntity.getBodyTemperature() != null && !physicalMeasurementEntity.getBodyTemperature().isEmpty()) {
        physicalMeasurement.setBodyTemperature(parseFloat(physicalMeasurementEntity.getBodyTemperature()));
      }
    }
    return physicalMeasurement;
  }

  /**
   * Transform a {@link PhysicalMeasurementEntity} into an {@link PhysicalMeasurement}.
   *
   * @param physicalMeasurement Object to be transformed.
   * @return {@link PhysicalMeasurement} if valid {@link PhysicalMeasurementEntity} otherwise null.
   */
  public PhysicalMeasurementEntity transform(PhysicalMeasurement physicalMeasurement) {
    PhysicalMeasurementEntity physicalMeasurementEntity = null;
    if (physicalMeasurement != null) {
      physicalMeasurementEntity = new PhysicalMeasurementEntity();
      physicalMeasurementEntity.setDate(physicalMeasurement.getDate());

      if (physicalMeasurement.getWeight() != null) {
        physicalMeasurementEntity.setWeight(physicalMeasurement.getWeight().toString());
      }
      if (physicalMeasurement.getBodyFatPercentage() != null) {
        physicalMeasurementEntity.setBodyFatPercentage(physicalMeasurement.getBodyFatPercentage().toString());
      }
      if (physicalMeasurement.getBodyTemperature() != null) {
        physicalMeasurementEntity.setBodyTemperature(physicalMeasurement.getBodyTemperature().toString());
      }
    }
    return physicalMeasurementEntity;
  }

  /**
   * Transform a List of {@link PhysicalMeasurementEntity} into a Collection of {@link PhysicalMeasurementEntity}.
   *
   * @param realmResults Object Collection to be transformed.
   * @return {@link PhysicalMeasurement} if valid {@link PhysicalMeasurementEntity} otherwise null.
   */
  public List<PhysicalMeasurementEntity> transform(RealmResults<PhysicalMeasurement> realmResults) {
    final List<PhysicalMeasurementEntity> physicalMeasurementList = new ArrayList<>();
    for(PhysicalMeasurement val : realmResults) {
      physicalMeasurementList.add(this.transform(val));
    }
    return physicalMeasurementList;
  }

  /**
   * 日付時刻文字列を Date型に変換
   *
   * @param strDate
   * @return
   */
  public Date string2date(String strDate) {
    Date dateDate=null;
    // 日付文字列→date型変換フォーマットを指定して
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");

    try {
      dateDate = sdf1.parse(strDate);
    }
    catch (ParseException e) {
      dateDate = Calendar.getInstance().getTime();
    }
    return dateDate;
  }

}
