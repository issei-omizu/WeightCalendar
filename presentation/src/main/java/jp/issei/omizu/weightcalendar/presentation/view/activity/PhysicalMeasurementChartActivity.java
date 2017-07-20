package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.presentation.internal.di.HasComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.DaggerPhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.PhysicalMeasurementComponent;

public class PhysicalMeasurementChartActivity extends BaseActivity
        implements HasComponent<PhysicalMeasurementComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PhysicalMeasurementChartActivity.class);
    }

    private PhysicalMeasurementComponent physicalMeasurementComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_measurement_chart);
        this.initializeInjector();
    }

    private void initializeInjector() {
        this.physicalMeasurementComponent = DaggerPhysicalMeasurementComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

        this.physicalMeasurementComponent.inject(this);
    }

    @Override public PhysicalMeasurementComponent getComponent() {
        return this.physicalMeasurementComponent;
    }
}
