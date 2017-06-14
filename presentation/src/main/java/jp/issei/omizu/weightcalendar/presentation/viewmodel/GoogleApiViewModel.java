package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.content.Context;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementList;
import jp.issei.omizu.weightcalendar.domain.interactor.SetAccountName;

/**
 * Created by isseiomizu on 2017/05/11.
 */

public class GoogleApiViewModel {

    private SetAccountName setAccountNameUseCase;

    private static GoogleAccountCredential credential;

    private static final String[] SCOPES = {SheetsScopes.DRIVE, SheetsScopes.SPREADSHEETS};

    @Inject
    public GoogleApiViewModel(SetAccountName setAccountNameUseCase) {
        this.setAccountNameUseCase = setAccountNameUseCase;
    }

    public void initialize(Context context) {
        // Initialize credentials and service object.
        credential = GoogleAccountCredential.usingOAuth2(
                context, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
    }

    public void setAccountName(String accountName) {
        SetAccountName.Params params = SetAccountName.Params.forAccountName(accountName);
        this.setAccountNameUseCase.execute(new SetAccountNameObserver(), params);
    }


    public GoogleAccountCredential getCredential() {
        return credential;
    }

    protected void executeGoogleApi() {
    }

    private final class SetAccountNameObserver extends DefaultObserver<Boolean> {

        @Override public void onComplete() {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onNext(Boolean result) {
            boolean ret;

            ret = result;
        }
    }
}
