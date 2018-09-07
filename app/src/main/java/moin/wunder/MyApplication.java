package moin.wunder;

import android.app.Application;
import moin.wunder.di.components.ApplicationComponent;
import moin.wunder.di.components.DaggerApplicationComponent;
import moin.wunder.di.modules.ApplicationModule;

public class MyApplication extends Application {

    private static ApplicationComponent applicationComponent;

      public MyApplication(){

      }

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent
                                .builder()
                                .applicationModule(new ApplicationModule(this))
                                .build();

    }

    public ApplicationComponent getApplicationComponent(){
          return applicationComponent;
      }

}
