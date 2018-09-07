package moin.wunder.di.modules;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class ApplicationModule {

    private Application app;

    public ApplicationModule(Application app){
        this.app = app;
    }


    @Provides
    @Singleton
    Context provideContext(){
        return app;
    }

}
