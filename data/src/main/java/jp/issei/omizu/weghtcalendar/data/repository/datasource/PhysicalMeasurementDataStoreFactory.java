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
package jp.issei.omizu.weghtcalendar.data.repository.datasource;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import javax.inject.Inject;

import jp.issei.omizu.weghtcalendar.data.cache.PhysicalMeasurementCache;
import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntityJsonMapper;
import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntitySheetsApiMapper;
import jp.issei.omizu.weghtcalendar.data.google.GoogleApi;
import jp.issei.omizu.weghtcalendar.data.google.GoogleApiImpl;
import jp.issei.omizu.weghtcalendar.data.net.RestApi;
import jp.issei.omizu.weghtcalendar.data.net.RestApiImpl;

/**
 * Factory that creates different implementations of {@link PhysicalMeasurementDataStore}.
 */
public class PhysicalMeasurementDataStoreFactory {

  private final Context context;
  private final PhysicalMeasurementCache physicalMeasurementCache;

  @Inject
  PhysicalMeasurementDataStoreFactory(@NonNull Context context, @NonNull PhysicalMeasurementCache physicalMeasurementCache) {
    this.context = context.getApplicationContext();
    this.physicalMeasurementCache = physicalMeasurementCache;
  }

  /**
   * Create {@link PhysicalMeasurementDataStore} from a physicalMeasurement id.
   */
  public PhysicalMeasurementDataStore create(int userId) {
    PhysicalMeasurementDataStore physicalMeasurementDataStore;

    if (!this.physicalMeasurementCache.isExpired() && this.physicalMeasurementCache.isCached(userId)) {
      physicalMeasurementDataStore = new DiskPhysicalMeasurementDataStore(this.physicalMeasurementCache);
    } else {
      physicalMeasurementDataStore = createCloudDataStore(null);
    }

    return physicalMeasurementDataStore;
  }

  /**
   * Create {@link PhysicalMeasurementDataStore} to retrieve data from the Cloud.
   */
  public PhysicalMeasurementDataStore createCloudDataStore(GoogleAccountCredential credential) {
//    final PhysicalMeasurementEntityJsonMapper userEntityJsonMapper = new PhysicalMeasurementEntityJsonMapper();
//    final RestApi restApi = new RestApiImpl(this.context, userEntityJsonMapper);
    final PhysicalMeasurementEntitySheetsApiMapper physicalMeasurementEntitySheetsApiMapper = new PhysicalMeasurementEntitySheetsApiMapper();

    final GoogleApi googleApi = new GoogleApiImpl(this.context, credential, physicalMeasurementEntitySheetsApiMapper);

    return new CloudPhysicalMeasurementDataStore(googleApi, this.physicalMeasurementCache);
  }
}
