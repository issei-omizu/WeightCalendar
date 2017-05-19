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
import java.util.Collection;
import java.util.Date;
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
    final List<PhysicalMeasurementEntity> physicalMeasurementList = new ArrayList<>();
//    for (PhysicalMeasurementEntity physicalMeasurementEntity : userEntityCollection) {
//      final PhysicalMeasurement physicalMeasurement = transform(physicalMeasurementEntity);
//      if (physicalMeasurement != null) {
//        physicalMeasurementList.add(physicalMeasurement);
//      }
//    }
    String date;
    String weight;
    String bodyFatPercentage;
    String bodyTemperature;

    PhysicalMeasurementEntity physicalMeasurementEntity;

    // 取得したデータをweightテーブルに追加
    if (values != null) {
      for (List row : values) {
        weight = "";
        bodyFatPercentage = "";
        bodyTemperature = "";

        date = row.get(0).toString();

        if (row.get(1) != null) {
          weight = row.get(1).toString();
        }
        if (row.get(2) != null) {
          bodyFatPercentage = row.get(2).toString();
        }
        /**
         * 一番最後の列にデータが存在しない（空）ときはデータが取得されない。
         * 例外エラーを避けるためにデータサイズで判断する。
         */
        if (row.size() == 4) {
          bodyTemperature = row.get(3).toString();
        }

        // sqliteに保存
        physicalMeasurementEntity = new PhysicalMeasurementEntity();
        physicalMeasurementEntity.setDate(this.string2date(date));
        physicalMeasurementEntity.setWeight(weight);
        physicalMeasurementEntity.setBodyFatPercentage(bodyFatPercentage);
        physicalMeasurementEntity.setBodyTemperature(bodyTemperature);
        physicalMeasurementList.add(physicalMeasurementEntity);
      }
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
