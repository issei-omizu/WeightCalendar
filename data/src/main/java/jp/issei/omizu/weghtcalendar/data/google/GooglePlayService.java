package jp.issei.omizu.weghtcalendar.data.google;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;

import java.util.Arrays;
import java.util.List;

/**
 * Created by BestRun on 2017/06/07.
 */

public class GooglePlayService {

    private GoogleAccountCredential credential;
    private Context context;
    private Sheets sheetsApi = null;

    private static final String[] SCOPES = {SheetsScopes.DRIVE, SheetsScopes.SPREADSHEETS};
    private static final String PREF_ACCOUNT_NAME = "accountName";

    public GooglePlayService(Context context) {
        this.context = context;

        String className = null;
        try {
            PackageInfo pInfo = context
                    .getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            context.getPackageManager().GET_ACTIVITIES);
            className = pInfo.activities[0].name;
        } catch (Exception e) {

        }

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> runningTasks = manager .getRunningTasks(1);
        ActivityManager.RunningTaskInfo cinfo = runningTasks.get(0);
        ComponentName component = cinfo.topActivity;
        className = component.getClassName();

        String accountName = this.context.getSharedPreferences(className, Context.MODE_PRIVATE)
                .getString(PREF_ACCOUNT_NAME, null);

        // Initialize credentials and service object.
        this.credential = GoogleAccountCredential.usingOAuth2(
                this.context, Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());

        this.credential.setSelectedAccountName(accountName);
    }

    public boolean initialize() {
        boolean result = false;

        if (isReadyToUseGoogleApi()) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            this.sheetsApi = new com.google.api.services.sheets.v4.Sheets.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Google Sheets API Android Quickstart")
                    .build();
            result = true;
        }

        return result;
    }

    public Sheets getSheetsApi() { return this.sheetsApi; }

    /**
     * Checks whether the device currently has a network connection.
     * @return true if the device has a network connection, false otherwise.
     */
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Check that Google Play services APK is installed and up to date.
     * @return true if Google Play Services is available and up to
     *     date on this device; false otherwise.
     */
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this.context);
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }

    /**
     * Attempt to resolve a missing, out-of-date, invalid or disabled Google
     * Play Services installation via a user dialog, if possible.
     */
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(this.context);
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
//            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }

    /**
     * Attempt to call the API, after verifying that all the preconditions are
     * satisfied. The preconditions are: Google Play Services installed, an
     * account was selected and the device currently has online access. If any
     * of the preconditions are not satisfied, the app will prompt the user as
     * appropriate.
     */
    private boolean isReadyToUseGoogleApi() {
        boolean isReady = false;

        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (this.credential.getSelectedAccountName() == null) {
//            chooseAccount();
        } else if (! isDeviceOnline()) {
//            mOutputText.setText("No network connection available.");
        } else {
            isReady = true;
        }

        return isReady;
    }
}
