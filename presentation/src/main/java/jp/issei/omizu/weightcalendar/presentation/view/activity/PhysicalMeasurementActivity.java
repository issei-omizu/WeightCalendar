package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.databinding.ActivityPhysicalMeasurementBinding;
import jp.issei.omizu.weightcalendar.presentation.di.HasComponent;
import jp.issei.omizu.weightcalendar.presentation.di.components.DaggerPhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.di.components.PhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.view.PhysicalMeasurementView;
import jp.issei.omizu.weightcalendar.presentation.viewmodel.PhysicalMeasurementViewModel;

public class PhysicalMeasurementActivity extends GoogleApiActivity
        implements HasComponent<PhysicalMeasurementComponent>, PhysicalMeasurementView {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PhysicalMeasurementActivity.class);
    }

    @Inject
    PhysicalMeasurementViewModel physicalMeasurementViewModel;

    private PhysicalMeasurementComponent physicalMeasurementComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_physical_measurement);

        this.initializeInjector();

        final ActivityPhysicalMeasurementBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_physical_measurement);
        binding.setViewModel(physicalMeasurementViewModel);

        // call google api
        this.getResultsFromApi();
    }

    private void initializeInjector() {
        this.physicalMeasurementComponent = DaggerPhysicalMeasurementComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

        this.physicalMeasurementComponent.inject(this);

        this.physicalMeasurementViewModel.initialize();
    }

    @Override
    protected void onReadyToUseGoogleApi() {
        this.physicalMeasurementViewModel.executeGoogleApi();
    }

    @Override public PhysicalMeasurementComponent getComponent() {
        return this.physicalMeasurementComponent;
    }

}
