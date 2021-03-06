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
package jp.issei.omizu.weightcalendar.presentation.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * Class that represents a PhysicalMeasurement in the domain layer.
 */
@Getter
@Setter
public class PhysicalMeasurementModel {

  public PhysicalMeasurementModel() {

  }
  private Date date;
  private Float weight;
  private Float bodyFatPercentage;
  private Float bodyTemperature;

}
