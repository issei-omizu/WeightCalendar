package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import jp.issei.omizu.weightcalendar.R;

public class SettingsActivity extends BaseActivity {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @BindView(R.id.btn_PhysicalMeasurement)
    Button btn_PhysicalMeasurement;

    @BindView(R.id.btn_Backup)
    Button btn_Backup;

    @BindView(R.id.btn_Restore)
    Button btn_Restore;

    private String IMPORT_REALM_FILE_NAME = "default.realm"; // Eventually replace this if you're using a custom db name

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_PhysicalMeasurement)
    public void navigateToPhysicalMeasurement() {
        this.navigator.navigateToPhysicalMeasurement(this);
    }

    @OnClick(R.id.btn_Backup)
    public void backup() {
        String fileName = "realm_copy.realm";
        File dir = Environment.getExternalStorageDirectory();

        Realm r = Realm.getDefaultInstance();

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
    }}
