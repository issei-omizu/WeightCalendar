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


import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.NullValue;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.executor.PostExecutionThread;
import jp.issei.omizu.weightcalendar.domain.executor.ThreadExecutor;
import jp.issei.omizu.weightcalendar.domain.repository.PhysicalMeasurementRepository;
import jp.issei.omizu.weightcalendar.domain.repository.PreferenceRepository;

/**
 * This class is an implementation of {@link UseCase} that represents a use case for
 * retrieving a collection of all {@link PhysicalMeasurement}.
 */
public class SetAccountName extends UseCase<String, SetAccountName.Params> {

  private final PreferenceRepository preferenceRepository;

  @Inject
  SetAccountName(PreferenceRepository preferenceRepository, ThreadExecutor threadExecutor,
                 PostExecutionThread postExecutionThread) {
    super(threadExecutor, postExecutionThread);
    this.preferenceRepository = preferenceRepository;
  }

  @Override
  Observable<Boolean> buildUseCaseObservable(Params params) {
    return this.preferenceRepository.putAccountName(params.accountName);
  }

  public static final class Params {

    private String accountName;

    private Params(String accountName) {
      this.accountName = accountName;
    }

    public static Params forAccountName(String accountName) {
      return new Params(accountName);
    }
  }
}
