package jp.issei.omizu.weightcalendar.presentation;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.ApplicationComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.DaggerApplicationComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.modules.ApplicationModule;

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

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).withLimit(1000).build())
                        .build());
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
