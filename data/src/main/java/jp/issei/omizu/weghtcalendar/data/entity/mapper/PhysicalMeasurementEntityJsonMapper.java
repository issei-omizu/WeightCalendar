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

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;

/**
 * Class used to transform from Strings representing json to valid objects.
 */
public class PhysicalMeasurementEntityJsonMapper {

  private final Gson gson;

  @Inject
  public PhysicalMeasurementEntityJsonMapper() {
    this.gson = new Gson();
  }

  /**
   * Transform from valid json string to {@link PhysicalMeasurementEntity}.
   *
   * @param physicalMeasurementJsonResponse A json representing a user profile.
   * @return {@link PhysicalMeasurementEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public PhysicalMeasurementEntity transformPhysicalMeasurementEntity(String physicalMeasurementJsonResponse) throws JsonSyntaxException {
    final Type physicalMeasurementEntityType = new TypeToken<PhysicalMeasurementEntity>() {}.getType();
    return this.gson.fromJson(physicalMeasurementJsonResponse, physicalMeasurementEntityType);
  }

  /**
   * Transform from valid json string to List of {@link PhysicalMeasurementEntity}.
   *
   * @param physicalMeasurementListJsonResponse A json representing a collection of users.
   * @return List of {@link PhysicalMeasurementEntity}.
   * @throws JsonSyntaxException if the json string is not a valid json structure.
   */
  public List<PhysicalMeasurementEntity> transformPhysicalMeasurementEntityCollection(String physicalMeasurementListJsonResponse)
      throws JsonSyntaxException {
    final Type listOfPhysicalMeasurementEntityType = new TypeToken<List<PhysicalMeasurementEntity>>() {}.getType();
    return this.gson.fromJson(physicalMeasurementListJsonResponse, listOfPhysicalMeasurementEntityType);
  }
}
