package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmResults;
import jp.issei.omizu.weghtcalendar.data.realm.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.presentation.viewmodel.PhysicalMeasurementViewModel;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_Workout)
    Button btn_Workout;

    @BindView(R.id.btn_PhysicalMeasurement)
    Button btn_PhysicalMeasurement;

    @BindView(R.id.btn_PhysicalMeasurementInput)
    Button btn_PhysicalMeasurementInput;

    @BindView(R.id.btn_Backup)
    Button btn_Backup;

    @BindView(R.id.btn_Restore)
    Button btn_Restore;

    private String IMPORT_REALM_FILE_NAME = "default.realm"; // Eventually replace this if you're using a custom db name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {

        }
    }

    /**
     * Goes to the physicalMeasurement list screen.
     */
    @OnClick(R.id.btn_Workout)
    public void navigateToWorkout() {
        this.navigator.navigateToWorkout(this);
    }

    @OnClick(R.id.btn_PhysicalMeasurement)
    public void navigateToPhysicalMeasurement() {
        this.navigator.navigateToPhysicalMeasurement(this);
    }

    @OnClick(R.id.btn_PhysicalMeasurementInput)
    public void navigateToPhysicalMeasurementInput() {
        this.navigator.navigateToPhysicalMeasurementInput(this);
    }

    @OnClick(R.id.btn_Backup)
    public void backup() {
        String fileName = "realm_copy.realm";
        File dir = Environment.getExternalStorageDirectory();

        Realm r = Realm.getDefaultInstance();

//        // トランザクション開始
//        r.beginTransaction();
//
//        final RealmResults<PhysicalMeasurement> realmResults = r.where(PhysicalMeasurement.class).findAll();

        File f = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
        if (f.exists()) {
            f.delete();
        }

        try {
            r.writeCopyTo(f);
        } catch (Exception e) {
            e.printStackTrace();
        }

        r.close();
    }

    @OnClick(R.id.btn_Restore)
    public void restore() {
        String fileName = "realm_copy.realm";
        File dir = Environment.getExternalStorageDirectory();

        //Restore
        String restoreFilePath = dir + "/" + fileName;
        copyBundledRealmFile(restoreFilePath, IMPORT_REALM_FILE_NAME);
    }

    private boolean hasPermission(String permissionName) {
        return (ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED);
    }

    private String copyBundledRealmFile(String oldFilePath, String outFileName) {
        try {
            File file = new File(this.getApplicationContext().getFilesDir(), outFileName);

            FileOutputStream outputStream = new FileOutputStream(file);

            FileInputStream inputStream = new FileInputStream(new File(oldFilePath));

            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buf)) > 0) {
                outputStream.write(buf, 0, bytesRead);
            }
            outputStream.close();
            return file.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
