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
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.AppendDimensionRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetRequest;
import com.google.api.services.sheets.v4.model.BatchUpdateSpreadsheetResponse;
import com.google.api.services.sheets.v4.model.CellData;
import com.google.api.services.sheets.v4.model.DeleteDimensionRequest;
import com.google.api.services.sheets.v4.model.DimensionRange;
import com.google.api.services.sheets.v4.model.ExtendedValue;
import com.google.api.services.sheets.v4.model.GridCoordinate;
import com.google.api.services.sheets.v4.model.Request;
import com.google.api.services.sheets.v4.model.RowData;
import com.google.api.services.sheets.v4.model.UpdateCellsRequest;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
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
//  private com.google.api.services.sheets.v4.Sheets googleApiServices = null;

  private GooglePlayService googlePlayService;
  private Sheets sheetsApi = null;

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
    this.googlePlayService = new GooglePlayService(context);
    this.context = context.getApplicationContext();
    this.physicalMeasurementEntitySheetsApiMapper = physicalMeasurementEntitySheetsApiMapper;

    HttpTransport transport = AndroidHttp.newCompatibleTransport();
    JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
    this.sheetsApi = new com.google.api.services.sheets.v4.Sheets.Builder(
            transport, jsonFactory, credential)
            .setApplicationName("Google Sheets API Android Quickstart")
            .build();
  }

  @Override
  public Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList() {
    return Observable.create(emitter -> {
      if (isThereInternetConnection()) {
        if (this.googlePlayService.initialize()){
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

  @Override
  public void put(List<PhysicalMeasurementEntity> physicalMeasurementEntity) {
    try {
      this.exportDataFromApi(physicalMeasurementEntity);
    } catch (Exception e) {

    }
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

    Sheets sheetsApi = this.googlePlayService.getSheetsApi();
    ValueRange response = this.googlePlayService.getSheetsApi().spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
//    ValueRange response = this.sheetsApi.spreadsheets().values()
//            .get(spreadsheetId, range)
//            .execute();

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

  private void exportDataFromApi(List<PhysicalMeasurementEntity> physicalMeasurementEntities) throws IOException {
    String spreadsheetId = "1CYOcWrQG7VG9wwPmf2VqI2Xqf-YclI04LiUB8Do_v0Q";
    Integer worksheetId = 1144545091;

    // 列削除テスト！
    BatchUpdateSpreadsheetRequest del = new BatchUpdateSpreadsheetRequest();
    DeleteDimensionRequest deleteDimensionRequest = new DeleteDimensionRequest();

    DimensionRange dimensionRange = new DimensionRange();
    dimensionRange.setSheetId(worksheetId);
    dimensionRange.setDimension("COLUMNS");
    dimensionRange.setStartIndex(0);
    dimensionRange.setEndIndex(3);

    deleteDimensionRequest.setRange(dimensionRange);


    List<Request> requests = new ArrayList<>();

    // 列削除
    requests.add(new Request()
            .setDeleteDimension(deleteDimensionRequest));

    // 列追加
    requests.add(new Request()
            .setAppendDimension(new AppendDimensionRequest()
                    .setSheetId(worksheetId)
                    .setDimension("COLUMNS")
                    .setLength(3)));

    del.setRequests(requests);
    BatchUpdateSpreadsheetResponse retDel = this.sheetsApi.spreadsheets().batchUpdate(spreadsheetId, del).execute();

    requests.clear();

    // 体重データ全登録
    List<CellData> values;
    Integer count = 1;

    String date = "";
    String weight = "";
    String bodyFatPercentage = "";
    String bodyTemperature = "";

    for (PhysicalMeasurementEntity weightItem : physicalMeasurementEntities) {
      date = this.date2String(weightItem.getDate());
      weight = weightItem.getWeight().toString();
      bodyFatPercentage = weightItem.getBodyFatPercentage().toString();
      bodyTemperature = weightItem.getBodyTemperature().toString();

      /**
       * 体重・体脂肪率が両方ともデータが設定されていない時はデータを
       * 登録しない。
       */
      if (weight != null || bodyFatPercentage != null) {
        values = new ArrayList<>();

        // date
        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(date)));

        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(weight)));

        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(bodyFatPercentage)));

        values.add(new CellData()
                .setUserEnteredValue(new ExtendedValue()
                        .setStringValue(bodyTemperature)));

        requests.add(new Request()
                .setUpdateCells(new UpdateCellsRequest()
                        .setStart(new GridCoordinate()
                                .setSheetId(worksheetId)
                                .setRowIndex(count)
                                .setColumnIndex(0))
                        .setRows(Arrays.asList(
                                new RowData().setValues(values)))
                        .setFields("userEnteredValue,userEnteredFormat.backgroundColor")));

        count++;
      }
    }

    del.clear();
    del.setRequests(requests);
    retDel = this.sheetsApi.spreadsheets().batchUpdate(spreadsheetId, del).execute();

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

  /**
   * 日付時刻文字列を Date型に変換
   *
   * @param date
   * @return
   */
  private String date2String(Date date) {
    String dateDate = "";

    // 日付文字列→date型変換フォーマットを指定して
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    dateDate = sdf1.format(date);

    return dateDate;
  }

}
