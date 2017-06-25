package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.databinding.ActivityPhysicalMeasurementInputBinding;
import jp.issei.omizu.weightcalendar.presentation.di.HasComponent;
import jp.issei.omizu.weightcalendar.presentation.di.components.DaggerPhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.di.components.PhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.view.PhysicalMeasurementView;
import jp.issei.omizu.weightcalendar.presentation.view.component.PickedDate;
import jp.issei.omizu.weightcalendar.presentation.viewmodel.PhysicalMeasurementInputViewModel;

public class PhysicalMeasurementInputActivity extends BaseActivity
        implements HasComponent<PhysicalMeasurementComponent>, PhysicalMeasurementView {


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PhysicalMeasurementInputActivity.class);
    }

    @Inject
    PhysicalMeasurementInputViewModel physicalMeasurementInputViewModel;

    private PhysicalMeasurementComponent physicalMeasurementComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPhysicalMeasurementInputBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_physical_measurement_input);
        this.initializeInjector();

        PickedDate date = new PickedDate();
        binding.setViewModel(physicalMeasurementInputViewModel);




//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initializeInjector() {
        this.physicalMeasurementComponent = DaggerPhysicalMeasurementComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

        this.physicalMeasurementComponent.inject(this);

        this.physicalMeasurementInputViewModel.initialize();
    }

    @Override public PhysicalMeasurementComponent getComponent() {
        return this.physicalMeasurementComponent;
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
