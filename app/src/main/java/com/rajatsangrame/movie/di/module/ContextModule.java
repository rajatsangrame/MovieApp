package com.rajatsangrame.movie.di.module;

import android.content.Context;

import com.rajatsangrame.movie.di.scope.ApplicationContext;
import com.rajatsangrame.movie.di.scope.ApplicationScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Rajat Sangrame on 9/5/20.
 * http://github.com/rajatsangrame
 */
@Module
public class ContextModule {

    Context context;

    public ContextModule(Context context) {
        this.context = context;
    }

    @ApplicationContext
    @ApplicationScope
    @Provides
    public Context context() {
        return context.getApplicationContext();
    }
}
