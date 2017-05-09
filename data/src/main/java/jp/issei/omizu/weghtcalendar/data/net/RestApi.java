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
package jp.issei.omizu.weghtcalendar.data.net;

import java.util.List;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.entity.PhysicalMeasurementEntity;

/**
 * RestApi for retrieving data from the network.
 */
public interface RestApi {
//  String API_BASE_URL =
//          "https://raw.githubusercontent.com/android10/Sample-Data/master/Android-CleanArchitecture/";
  String API_BASE_URL =
          "https://script.googleusercontent.com/macros/";

  /** Api url for getting all users */
//  String API_URL_GET_USER_LIST = API_BASE_URL + "users.json";
  String API_URL_GET_USER_LIST = API_BASE_URL + "echo?user_content_key=w9mX5mv-BWUkoAFmLc9-SQdlJqKyVQ9dL0x4AG8c_PZKmhoQS9uN0s7ADiCstmb_4Q8gs1lQj6pyNCxacizesu5HgIylPY07OJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1ZsYSbt7G4nMhEEDL32U4DxjO7V7yvmJPXJTBuCiTGh3rUPjpYM_V0PJJG7TIaKpzSwXWqDH0u6kdne6FHO_pI24nWDFIECv6I_fKCx8GlCzXkWazHozkrD1l4n-wXSDJoziHP_nGcJSOldl6JZibU&lib=MbpKbbfePtAVndrs259dhPT7ROjQYJ8yx";
  /** Api url for getting a user profile: Remember to concatenate id + 'json' */
  String API_URL_GET_USER_DETAILS = API_BASE_URL + "user_";

  /**
   * Retrieves an {@link Observable} which will emit a List of {@link PhysicalMeasurementEntity}.
   */
  Observable<List<PhysicalMeasurementEntity>> physicalMeasurementEntityList();

  /**
   * Retrieves an {@link Observable} which will emit a {@link PhysicalMeasurementEntity}.
   *
   * @param userId The user id used to get user data.
   */
  Observable<PhysicalMeasurementEntity> physicalMeasurementEntityById(final int userId);
}
