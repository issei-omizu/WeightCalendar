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

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntityRealmMapper;
import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntitySheetsApiMapper;
import jp.issei.omizu.weghtcalendar.data.exception.NetworkConnectionException;
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
  private final Realm realm;
  private final PhysicalMeasurementEntityRealmMapper physicalMeasurementEntityRealmMapper;

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

    this.realm = Realm.getDefaultInstance();
    this.physicalMeasurementEntityRealmMapper = new PhysicalMeasurementEntityRealmMapper();
  }

  @Override
  public Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList() {
    return Observable.create(emitter -> {
      try {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<PhysicalMeasurement> realmResults = realm.where(PhysicalMeasurement.class).findAll();
        realmResults.size();

        if (realmResults.size() > 0) {
          emitter.onNext(this.physicalMeasurementEntityRealmMapper.transform(realmResults));
          emitter.onComplete();
        } else {
          emitter.onError(new NetworkConnectionException());
        }
      } catch (Exception e) {
        emitter.onError(new NetworkConnectionException(e.getCause()));
      }
    });
  }

  @Override
  public Observable<PhysicalMeasurementEntity> get(final Date date) {
    return Observable.create(emitter -> {
      try {
        Realm realm = Realm.getDefaultInstance();
        final PhysicalMeasurement physicalMeasurement = realm.where(PhysicalMeasurement.class).equalTo("date", date).findFirst();
        if (physicalMeasurement != null) {
          emitter.onNext(this.physicalMeasurementEntityRealmMapper.transform(physicalMeasurement));
        }
        emitter.onComplete();
      } catch (Exception e) {
        emitter.onError(new NetworkConnectionException(e.getCause()));
      }
    });
  }

//  @Override
//  public void put(PhysicalMeasurementEntity physicalMeasurementEntity) {
//    if (physicalMeasurementEntity != null) {
//      final File userEntityFile = this.buildFile(physicalMeasurementEntity.getUserId());
//      if (!isCached(physicalMeasurementEntity.getUserId())) {
//        final String jsonString = this.serializer.serialize(physicalMeasurementEntity, PhysicalMeasurementEntity.class);
//        this.executeAsynchronously(new CacheWriter(this.fileManager, userEntityFile, jsonString));
//        setLastCacheUpdateTimeMillis();
//      }
//    }
//  }

  @Override
  public void put(List<PhysicalMeasurementEntity> physicalMeasurementEntity) {
    Realm realm = Realm.getDefaultInstance();
    for(PhysicalMeasurementEntity val : physicalMeasurementEntity){
      realm.executeTransaction(_realm -> {
        PhysicalMeasurement transformPhysicalMeasurement = this.physicalMeasurementEntityRealmMapper.transform(val);

        String id = UUID.randomUUID().toString();
        PhysicalMeasurement physicalMeasurement = _realm.createObject(PhysicalMeasurement.class, id);
//        physicalMeasurement.setId(id);
        physicalMeasurement.setDate(transformPhysicalMeasurement.getDate());
        physicalMeasurement.setWeight(transformPhysicalMeasurement.getWeight());
        physicalMeasurement.setBodyFatPercentage(transformPhysicalMeasurement.getBodyFatPercentage());
        physicalMeasurement.setBodyTemperature(transformPhysicalMeasurement.getBodyTemperature());
      });
    }
    realm.close();

  }

}
