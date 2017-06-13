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
package jp.issei.omizu.weghtcalendar.data.preference;

import io.reactivex.Observable;

/**
 * GoogleApi for retrieving data from the network.
 */
public interface Preference {

  String PREF_GOOGLE_API = "GoogleApi";
  String PREF_ACCOUNT_NAME = "accountName";

  Observable<String> getAccountName();

  void putAccountName(final String accountName);
}
