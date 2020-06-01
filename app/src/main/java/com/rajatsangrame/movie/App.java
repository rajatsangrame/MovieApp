package com.rajatsangrame.movie;

import android.app.Application;
import android.content.Context;

import com.rajatsangrame.movie.di.component.ApplicationComponent;
import com.rajatsangrame.movie.di.component.DaggerApplicationComponent;
import com.rajatsangrame.movie.di.module.ContextModule;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
public class App extends Application {

    private ApplicationComponent component;

    public static App get(Context context) {
      return (App) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent
                .builder()
                .contextModule(new ContextModule(this))
                .build();

    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
