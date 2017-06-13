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

import android.content.Context;
import android.content.SharedPreferences;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import io.reactivex.Observable;
import jp.issei.omizu.weghtcalendar.data.exception.NetworkConnectionException;

/**
 * {@link Preference} implementation for retrieving data from the network.
 */
public class PreferenceImpl implements Preference {

  private final Context context;

  /**
   * Constructor of the class
   *
   * @param context {@link Context}.
   */
  public PreferenceImpl(Context context) {
    if (context == null) {
      throw new IllegalArgumentException("The constructor parameters cannot be null!!!");
    }
    this.context = context.getApplicationContext();
  }

  @Override
  public Observable<String> getAccountName() {
    return Observable.create(emitter -> {
      try {
        String accountName = this.context.getSharedPreferences(PREF_GOOGLE_API, Context.MODE_PRIVATE)
                .getString(PREF_ACCOUNT_NAME, null);
        if (accountName != null && !accountName.isEmpty()) {
          emitter.onNext(accountName);
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
  public void putAccountName(final String accountName) {
    SharedPreferences settings =
            this.context.getSharedPreferences(PREF_GOOGLE_API, Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = settings.edit();
    editor.putString(PREF_ACCOUNT_NAME, accountName);
    editor.apply();
  }
}
