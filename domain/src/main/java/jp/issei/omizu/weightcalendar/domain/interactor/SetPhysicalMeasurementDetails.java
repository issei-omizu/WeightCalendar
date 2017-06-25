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
package jp.issei.omizu.weightcalendar.domain.interactor;


import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observable;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.executor.PostExecutionThread;
import jp.issei.omizu.weightcalendar.domain.executor.ThreadExecutor;
import jp.issei.omizu.weightcalendar.domain.repository.PhysicalMeasurementRepository;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link PhysicalMeasurement}.
 */
public class SetPhysicalMeasurementDetails extends UseCase<PhysicalMeasurement, SetPhysicalMeasurementDetails.Params> {

  private final PhysicalMeasurementRepository physicalMeasurementRepository;

  @Inject
  SetPhysicalMeasurementDetails(PhysicalMeasurementRepository physicalMeasurementRepository, ThreadExecutor threadExecutor,
                                PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.physicalMeasurementRepository = physicalMeasurementRepository;
  }

  @Override
  Observable<PhysicalMeasurement> buildUseCaseObservable(Params params) {
    return this.physicalMeasurementRepository.physicalMeasurement(params.date);
  }

  public static final class Params {

    private final Date date;

    private Params(Date date) {
      this.date = date;
    }

    public static Params forDate(Date date) {
      return new Params(date);
    }
  }
}
