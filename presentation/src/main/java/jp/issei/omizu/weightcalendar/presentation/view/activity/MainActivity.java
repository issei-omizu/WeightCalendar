package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.issei.omizu.weightcalendar.R;


public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_Workout)
    Button btn_Workout;

    @BindView(R.id.btn_PhysicalMeasurementInput)
    Button btn_PhysicalMeasurementInput;

    @BindView(R.id.btn_PhysicalMeasurementChart)
    Button btn_PhysicalMeasurementChart;

    @BindView(R.id.btn_settings)
    Button btn_settings;

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

    @OnClick(R.id.btn_PhysicalMeasurementInput)
    public void navigateToPhysicalMeasurementInput() {
        this.navigator.navigateToPhysicalMeasurementInput(this);
    }

    @OnClick(R.id.btn_PhysicalMeasurementChart)
    public void navigateToPhysicalMeasurementChart() {
        this.navigator.navigateToPhysicalMeasurementChart(this);
    }

    @OnClick(R.id.btn_settings)
    public void navigateToSettings() {
        this.navigator.navigateToSettings(this);
    }

    private boolean hasPermission(String permissionName) {
        return (ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED);
    }
}
