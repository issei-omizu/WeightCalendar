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

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntityRealmMapper;
import jp.issei.omizu.weghtcalendar.data.exception.NetworkConnectionException;

/**
 * {@link PhysicalMeasurementRealm} implementation.
 */
public class PhysicalMeasurementRealmImpl implements PhysicalMeasurementRealm {

  private final Context context;
//  private final Realm realm;
  private final PhysicalMeasurementEntityRealmMapper physicalMeasurementEntityRealmMapper;

  /**
   * Constructor of the class {@link PhysicalMeasurementRealmImpl}.
   *
   * @param context A
   */
  public PhysicalMeasurementRealmImpl(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("Invalid null parameter");
    }
    this.context = context.getApplicationContext();

//    this.realm = Realm.getDefaultInstance();
    this.physicalMeasurementEntityRealmMapper = new PhysicalMeasurementEntityRealmMapper();
  }

  @Override
  public Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList() {
    return Observable.create(emitter -> {
      try (Realm realm = Realm.getDefaultInstance()) {
        final RealmResults<PhysicalMeasurement> realmResults = realm
                .where(PhysicalMeasurement.class)
                .findAll()
//                .sort("date", Sort.DESCENDING);
                .sort("date", Sort.ASCENDING);

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
      try (Realm realm = Realm.getDefaultInstance()) {
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

  @Override
  public Observable<PhysicalMeasurementEntity> set(final PhysicalMeasurementEntity physicalMeasurementEntity) {
    return Observable.create(emitter -> {
      try (Realm realm = Realm.getDefaultInstance()) {
        PhysicalMeasurement physicalMeasurement = this.update(physicalMeasurementEntity);
        if (physicalMeasurement != null) {
          emitter.onNext(this.physicalMeasurementEntityRealmMapper.transform(physicalMeasurement));
        }
        else {
          this.insert(physicalMeasurementEntity);
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
    for(PhysicalMeasurementEntity val : physicalMeasurementEntity){
      this.insert(val);
    }
  }

  private void insert(final PhysicalMeasurementEntity physicalMeasurementEntity) {
    try (Realm realm = Realm.getDefaultInstance()) {
      realm.executeTransaction(_realm -> {
        PhysicalMeasurement transformPhysicalMeasurement = this.physicalMeasurementEntityRealmMapper.transform(physicalMeasurementEntity);
        String id = UUID.randomUUID().toString();
        PhysicalMeasurement physicalMeasurement = _realm.createObject(PhysicalMeasurement.class, id);
        physicalMeasurement.setDate(transformPhysicalMeasurement.getDate());
        physicalMeasurement.setWeight(transformPhysicalMeasurement.getWeight());
        physicalMeasurement.setBodyFatPercentage(transformPhysicalMeasurement.getBodyFatPercentage());
        physicalMeasurement.setBodyTemperature(transformPhysicalMeasurement.getBodyTemperature());
      });
    }
  }

  private PhysicalMeasurement update(final PhysicalMeasurementEntity physicalMeasurementEntity) {
    PhysicalMeasurement physicalMeasurement = null;
    try (Realm realm = Realm.getDefaultInstance()) {
      physicalMeasurement = realm.where(PhysicalMeasurement.class).equalTo("date", physicalMeasurementEntity.getDate()).findFirst();
      if (physicalMeasurement != null) {
        realm.beginTransaction();
        physicalMeasurement.setDate(physicalMeasurementEntity.getDate());
        physicalMeasurement.setWeight(physicalMeasurementEntity.getWeight());
        physicalMeasurement.setBodyFatPercentage(physicalMeasurementEntity.getBodyFatPercentage());
        physicalMeasurement.setBodyTemperature(physicalMeasurementEntity.getBodyTemperature());
        realm.commitTransaction();
      }
    }

    return physicalMeasurement;
  }

  @Override
  public void deleteAll() {
    try (Realm realm = Realm.getDefaultInstance()) {
      realm.beginTransaction();
      realm.deleteAll();
      realm.commitTransaction();
    }
  }

}
