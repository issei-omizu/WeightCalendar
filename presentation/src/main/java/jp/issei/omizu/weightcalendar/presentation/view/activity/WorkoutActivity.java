package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.WindowManager;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.databinding.ActivityWorkoutBinding;
import jp.issei.omizu.weightcalendar.presentation.internal.di.HasComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.DaggerWorkoutComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.WorkoutComponent;
import jp.issei.omizu.weightcalendar.presentation.viewmodel.WorkoutViewModel;

public class WorkoutActivity extends BaseActivity implements HasComponent<WorkoutComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, WorkoutActivity.class);
    }

    @Inject
    WorkoutViewModel stopWatchViewModel;

    private WorkoutComponent workoutComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        // Keep screen on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        this.initializeInjector();
        final ActivityWorkoutBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_workout);
        binding.setViewModel(stopWatchViewModel);

        stopWatchViewModel.initialize(this);
    }

    private void initializeInjector() {
        this.workoutComponent = DaggerWorkoutComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

        this.workoutComponent.inject(this);
    }

    @Override
    protected void onDestroy() {
        stopWatchViewModel.dispose();
        super.onDestroy();
    }

    @Override public WorkoutComponent getComponent() {
        return workoutComponent;
    }

}
