package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.Arrays;

import javax.inject.Inject;

/**
 * Created by isseiomizu on 2017/05/11.
 */

public class GoogleApiViewModel {

    private static GoogleAccountCredential credential;

    private static final String[] SCOPES = {SheetsScopes.DRIVE, SheetsScopes.SPREADSHEETS};

    @Inject
    public GoogleApiViewModel() {
    }

    public void initialize(Context context) {
        // Initialize credentials and service object.
        credential = GoogleAccountCredential.usingOAuth2(
                context, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }



    public GoogleAccountCredential getCredential() {
        return credential;
    }

    protected void executeGoogleApi() {
    }
}
