package com.monzo.androidtest.articles;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.monzo.androidtest.R;
import com.monzo.androidtest.api.GuardianService;
import com.monzo.androidtest.articles.model.ArticleMapper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticlesModule {
    private static final String BASE_URL = "https://content.guardianapis.com";
    private static final String HEADER_API_KEY = "api-key";

    ArticlesPresenter inject(Context context) {
        return new ArticlesPresenter(AndroidSchedulers.mainThread(), Schedulers.io(),
                new ArticlesRepository(createGuardianService(context), new ArticleMapper()));
    }

    private GuardianService createGuardianService(Context context) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(createOkHttpClient(context.getResources()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(GuardianService.class);
    }

    private OkHttpClient createOkHttpClient(Resources resources) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(getAuthInterceptor(resources));
        clientBuilder.addInterceptor(loggingInterceptor);
        return clientBuilder.build();
    }

    private Interceptor getAuthInterceptor(Resources resources) {
        return chain -> {
            Request original = chain.request();
            Headers.Builder hb = original.headers().newBuilder();
            hb.add(HEADER_API_KEY, resources.getString(R.string.guardian_api_key));
            return chain.proceed(original.newBuilder().headers(hb.build()).build());
        };
    }
}
