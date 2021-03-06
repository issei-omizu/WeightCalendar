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
package jp.issei.omizu.weightcalendar.domain.repository;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.Date;
import java.util.List;

import io.reactivex.Observable;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;

/**
 * Interface that represents a Repository for getting {@link PhysicalMeasurement} related data.
 */
public interface PhysicalMeasurementRepository {
  /**
   * Get an {@link Observable} which will emit a List of {@link PhysicalMeasurement}.
   */
  Observable<List<PhysicalMeasurement>> physicalMeasurements();
  Observable<List<PhysicalMeasurement>> physicalMeasurements(GoogleAccountCredential credential);

  /**
   * Get an {@link Observable} which will emit a {@link PhysicalMeasurement}.
   *
   * @param date The physicalMeasurement id used to retrieve physicalMeasurement data.
   */
  Observable<PhysicalMeasurement> physicalMeasurement(final Date date);

  Observable<PhysicalMeasurement> setPhysicalMeasurement(final PhysicalMeasurement physicalMeasurement);

  Observable<List<PhysicalMeasurement>> exportPhysicalMeasurements(GoogleAccountCredential credential);
}
