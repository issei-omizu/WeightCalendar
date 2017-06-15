package jp.issei.omizu.weightcalendar.presentation;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jp.issei.omizu.weightcalendar.presentation.di.components.ApplicationComponent;
import jp.issei.omizu.weightcalendar.presentation.di.components.DaggerApplicationComponent;
import jp.issei.omizu.weightcalendar.presentation.di.modules.ApplicationModule;

/**
 * Created by isseiomizu on 2017/04/17.
 */

public class WeightCalendarApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        injectApplicationComponent();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().build();
        Realm.setDefaultConfiguration(config);
    }

    private void injectApplicationComponent() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }

}
