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
package jp.issei.omizu.weghtcalendar.data.google;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;
import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntityJsonMapper;
import jp.issei.omizu.weghtcalendar.data.entity.mapper.PhysicalMeasurementEntitySheetsApiMapper;
import jp.issei.omizu.weghtcalendar.data.exception.NetworkConnectionException;

/**
 * {@link GoogleApi} implementation for retrieving data from the network.
 */
public class GoogleApiImpl implements GoogleApi {

  private final Context context;
  private final PhysicalMeasurementEntitySheetsApiMapper physicalMeasurementEntitySheetsApiMapper;
  private com.google.api.services.sheets.v4.Sheets googleApiServices = null;

  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   * @param physicalMeasurementEntitySheetsApiMapper {@link PhysicalMeasurementEntitySheetsApiMapper}.
   */
  public GoogleApiImpl(Context context, GoogleAccountCredential credential, PhysicalMeasurementEntitySheetsApiMapper physicalMeasurementEntitySheetsApiMapper) {
    if (context == null || physicalMeasurementEntitySheetsApiMapper == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
    this.physicalMeasurementEntitySheetsApiMapper = physicalMeasurementEntitySheetsApiMapper;

    HttpTransport transport = AndroidHttp.newCompatibleTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    this.googleApiServices = new com.google.api.services.sheets.v4.Sheets.Builder(
            transport, jsonFactory, credential)
            .setApplicationName("Google Sheets API Android Quickstart")
            .build();
  }

  @Override
  public Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList() {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
          List<List<Object>> val = new ArrayList<>();
          val = this.getDataFromApi();

          if (val != null) {
            emitter.onNext(physicalMeasurementEntitySheetsApiMapper.transform(val));
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  @Override
  public Observable<PhysicalMeasurementEntity> physicalMeasurementEntityById(final int userId) {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        try {
//          String responseUserDetails = getUserDetailsFromApi(userId);
          String responseUserDetails = null;
          if (responseUserDetails != null) {
//            emitter.onNext(physicalMeasurementEntityJsonMapper.transformPhysicalMeasurementEntity(responseUserDetails));
            emitter.onComplete();
          } else {
            emitter.onError(new NetworkConnectionException());
          }
        } catch (Exception e) {
          emitter.onError(new NetworkConnectionException(e.getCause()));
        }
      } else {
        emitter.onError(new NetworkConnectionException());
      }
    });
  }

  /**
   * Fetch a list of names and majors of students in a sample spreadsheet:
   * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
   * @return List of names and majors
   * @throws IOException
   */
  private List<List<Object>> getDataFromApi() throws IOException {
    String spreadsheetId = "1CYOcWrQG7VG9wwPmf2VqI2Xqf-YclI04LiUB8Do_v0Q";
    int rangeStart = 2;
    String range = "weight!A" + rangeStart + ":C";
    ValueRange response = this.googleApiServices.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();

    List<List<Object>> values = response.getValues();

    // 取得したデータをMapに展開
    List<String> listData;
    Integer count = rangeStart;
    if (values != null) {
      for (List row : values) {
        listData = new ArrayList<>();
        listData.add(count.toString());
        if (row.size() > 1) {
          listData.add(row.get(1).toString());
          listData.add(row.get(2).toString());
        }
//        mapWeight.put(row.get(0).toString(), listData);
        count++;
      }
    }

    return values;
  }

//  private String getUserEntitiesFromApi() throws MalformedURLException {
//    return ApiConnection.createGET(API_URL_GET_USER_LIST).requestSyncCall();
//  }

//  private String getUserDetailsFromApi(int userId) throws MalformedURLException {
//    String apiUrl = API_URL_GET_USER_DETAILS + userId + ".json";
//    return ApiConnection.createGET(apiUrl).requestSyncCall();
//  }

  /**
   * Checks if the device has any active internet connection.
   *
   * @return true device with internet connection, otherwise false.
   */
  private boolean isThereInternetConnection() {
    boolean isConnected;

    ConnectivityManager connectivityManager =
        (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
    isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

    return isConnected;
  }
}
