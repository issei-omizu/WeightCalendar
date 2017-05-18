/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jp.issei.omizu.weghtcalendar.data.repository.datasource;

import android.content.Context;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.util.List;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.cache.PhysicalMeasurementCache;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weghtcalendar.data.google.GoogleApi;
import jp.issei.omizu.weghtcalendar.data.net.RestApi;

/**
 * {@link PhysicalMeasurementDataStore} implementation based on connections to the api (Cloud).
 */
class CloudPhysicalMeasurementDataStore implements PhysicalMeasurementDataStore {

  private final GoogleApi googleApi;
  private final PhysicalMeasurementCache physicalMeasurementCache;

  /**
   * Construct a {@link PhysicalMeasurementDataStore} based on connections to the api (Cloud).
   *
   * @param googleApi The {@link GoogleApi} implementation to use.
   * @param physicalMeasurementCache A {@link PhysicalMeasurementCache} to cache data retrieved from the api.
   */
  CloudPhysicalMeasurementDataStore(GoogleApi googleApi, PhysicalMeasurementCache physicalMeasurementCache) {
    this.googleApi = googleApi;
    this.physicalMeasurementCache = physicalMeasurementCache;
  }

  @Override
  public Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList() {
    return this.googleApi.physicalMeasurementEntityList();
  }

  @Override
  public Observable<PhysicalMeasurementEntity> physicalMeasurementEntityDetails(final int userId) {
    return this.googleApi.physicalMeasurementEntityById(userId).doOnNext(CloudPhysicalMeasurementDataStore.this.physicalMeasurementCache::put);
  }
}
