package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.presentation.viewmodel.PhysicalMeasurementViewModel;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_Workout)
    Button btn_Workout;

    @BindView(R.id.btn_PhysicalMeasurement)
    Button btn_PhysicalMeasurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
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

}
