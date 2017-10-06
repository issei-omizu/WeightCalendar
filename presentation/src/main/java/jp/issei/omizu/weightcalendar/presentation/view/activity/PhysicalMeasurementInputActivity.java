package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.BindView;
import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.databinding.ActivityPhysicalMeasurementInputBinding;
import jp.issei.omizu.weightcalendar.presentation.internal.di.HasComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.DaggerPhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.PhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.viewmodel.PhysicalMeasurementInputViewModel;

public class PhysicalMeasurementInputActivity extends BaseActivity
        implements HasComponent<PhysicalMeasurementComponent> {


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PhysicalMeasurementInputActivity.class);
    }

    @BindView(R.id.btnOk)
    Button btnOk;

    @BindView(R.id.clMain)
    ConstraintLayout clMain;

    @BindView(R.id.layoutButton)
    LinearLayout layoutButton;


    ConstraintSet resetConstraintSet = new ConstraintSet();
    ConstraintSet applyConstraintSet = new ConstraintSet();

    @Inject
    PhysicalMeasurementInputViewModel physicalMeasurementInputViewModel;

    private PhysicalMeasurementComponent physicalMeasurementComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_physical_measurement_input);

        ActivityPhysicalMeasurementInputBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_physical_measurement_input);
        this.initializeInjector();

        this.physicalMeasurementInputViewModel.initialize();
        binding.setViewModel(physicalMeasurementInputViewModel);

        this.clMain = (ConstraintLayout) findViewById(R.id.clMain);
        this.resetConstraintSet = new ConstraintSet();
        this.applyConstraintSet = new ConstraintSet();
        this.resetConstraintSet.clone(this.clMain);
        this.applyConstraintSet.clone(this.clMain);
        this.hideLayoutButton();

        this.initializeEvent();
    }

    private void initializeInjector() {
        this.physicalMeasurementComponent = DaggerPhysicalMeasurementComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        this.physicalMeasurementComponent.inject(this);
    }

    private void initializeEvent() {
        // OKボタン
        findViewById(R.id.btnOk).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                physicalMeasurementInputViewModel.updatePhysicalMeasurement();
                hideLayoutButton();
            }
        });

        // キャンセルボタン
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                hideLayoutButton();
            }
        });

        // クリアボタン
        findViewById(R.id.btnClear).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // クリック時の処理
                hideLayoutButton();
            }
        });

        // 体重
        findViewById(R.id.editWeight).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //受け取った時
                    showOkButton();
                } else {
                    //離れた時
                }
            }
        });

        // 体脂肪率
        findViewById(R.id.editBodyFatPercentage).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //受け取った時
                    showOkButton();
                } else {
                    //離れた時
                }
            }
        });

        // 体温
        findViewById(R.id.editBodyTemperature).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    //受け取った時
                    showOkButton();
                } else {
                    //離れた時
                }
            }
        });
    }

    @Override public PhysicalMeasurementComponent getComponent() {
        return this.physicalMeasurementComponent;
    }

    private void showOkButton() {
        TransitionManager.beginDelayedTransition(this.clMain);
        this.resetConstraintSet.applyTo(this.clMain);
    }

    private void hideLayoutButton() {
        TransitionManager.beginDelayedTransition(this.clMain);
        this.applyConstraintSet.setVisibility(R.id.layoutButton, ConstraintSet.GONE);
        this.applyConstraintSet.applyTo(this.clMain);
    }
}
