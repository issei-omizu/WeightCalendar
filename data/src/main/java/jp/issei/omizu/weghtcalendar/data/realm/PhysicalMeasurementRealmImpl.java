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
package jp.issei.omizu.weghtcalendar.data.realm;

import android.content.Context;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weightcalendar.domain.executor.ThreadExecutor;

/**
 * {@link PhysicalMeasurementRealm} implementation.
 */
public class PhysicalMeasurementRealmImpl implements PhysicalMeasurementRealm {

  private static final String SETTINGS_FILE_NAME = "com.fernandocejas.android10.SETTINGS";
  private static final String SETTINGS_KEY_LAST_CACHE_UPDATE = "last_cache_update";

  private static final String DEFAULT_FILE_NAME = "user_";
  private static final long EXPIRATION_TIME = 60 * 10 * 1000;

  private final Context context;
  private final ThreadExecutor threadExecutor;

  /**
   * Constructor of the class {@link PhysicalMeasurementRealmImpl}.
   *
   * @param context A
   */
  @Inject
  PhysicalMeasurementRealmImpl(Context context, ThreadExecutor executor) {
    if (context == null || executor == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.context = context.getApplicationContext();
    this.threadExecutor = executor;
  }

  @Override
  public Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList() {
//    return this.googleApi.physicalMeasurementEntityList();
    return null;
  }

  @Override
  public Observable<PhysicalMeasurementEntity> get(final int userId) {
    return Observable.create(emitter -> {
//      final File userEntityFile = PhysicalMeasurementRealmImpl.this.buildFile(userId);
//      final String fileContent = PhysicalMeasurementRealmImpl.this.fileManager.readFileContent(userEntityFile);
//      final PhysicalMeasurementEntity physicalMeasurementEntity =
//          PhysicalMeasurementRealmImpl.this.serializer.deserialize(fileContent, PhysicalMeasurementEntity.class);

//      if (physicalMeasurementEntity != null) {
//        emitter.onNext(physicalMeasurementEntity);
//        emitter.onComplete();
//      } else {
//        emitter.onError(new UserNotFoundException());
//      }
    });
  }

  @Override
  public void put(PhysicalMeasurementEntity physicalMeasurementEntity) {
//    if (physicalMeasurementEntity != null) {
//      final File userEntityFile = this.buildFile(physicalMeasurementEntity.getUserId());
//      if (!isCached(physicalMeasurementEntity.getUserId())) {
//        final String jsonString = this.serializer.serialize(physicalMeasurementEntity, PhysicalMeasurementEntity.class);
//        this.executeAsynchronously(new CacheWriter(this.fileManager, userEntityFile, jsonString));
//        setLastCacheUpdateTimeMillis();
//      }
//    }
  }

  @Override
  public void put(List<PhysicalMeasurementEntity> physicalMeasurementEntity) {
//    if (physicalMeasurementEntity != null) {
//      final File userEntityFile = this.buildFile(physicalMeasurementEntity.getUserId());
//      if (!isCached(physicalMeasurementEntity.getUserId())) {
//        final String jsonString = this.serializer.serialize(physicalMeasurementEntity, PhysicalMeasurementEntity.class);
//        this.executeAsynchronously(new CacheWriter(this.fileManager, userEntityFile, jsonString));
//        setLastCacheUpdateTimeMillis();
//      }
//    }
  }

  @Override
  public boolean isCached(int userId) {
//    final File userEntityFile = this.buildFile(userId);
    return true;
  }

  @Override
  public boolean isExpired() {
//    long currentTime = System.currentTimeMillis();
//    long lastUpdateTime = this.getLastCacheUpdateTimeMillis();
//
//    boolean expired = ((currentTime - lastUpdateTime) > EXPIRATION_TIME);
//
//    if (expired) {
//      this.evictAll();
//    }

    return false;
  }

}
