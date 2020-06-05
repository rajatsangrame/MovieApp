package com.rajatsangrame.movie.di.module;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rajatsangrame.movie.data.db.MovieDatabase;
import com.rajatsangrame.movie.di.qualifier.ApplicationContext;
import com.rajatsangrame.movie.di.scope.ApplicationScope;
import com.rajatsangrame.movie.data.rest.RetrofitApi;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rajat Sangrame on 12/5/20.
 * http://github.com/rajatsangrame
 */
@Module(includes = {ViewModelModule.class, OkHttpClientModule.class})
public class ApplicationModule {

    public static final String BASE_URL = "http://api.themoviedb.org";

    @Provides
    public RetrofitApi randomUsersApi(Retrofit retrofit) {
        return retrofit.create(RetrofitApi.class);
    }

    @ApplicationScope
    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient,
                             GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory(Gson gson) {
        return GsonConverterFactory.create(gson);
    }

    @Provides
    public Gson gson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        return gsonBuilder.create();
    }

    @ApplicationScope
    @Provides
    Repository getRepository(@ApplicationContext Context context, RetrofitApi retrofitApi, MovieDatabase database) {
        return new Repository(retrofitApi, database);
    }

    @ApplicationScope
    @Provides
    MovieDatabase getDatabase(@ApplicationContext Context context) {
        return MovieDatabase.getDatabase(context);
    }
}
