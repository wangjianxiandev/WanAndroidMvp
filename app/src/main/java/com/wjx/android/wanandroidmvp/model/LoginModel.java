package com.wjx.android.wanandroidmvp.model;

import android.annotation.TargetApi;
import android.os.Build;
import android.text.TextUtils;

import com.blankj.utilcode.util.SPUtils;
import com.wjx.android.wanandroidmvp.base.utils.ApiServer;
import com.wjx.android.wanandroidmvp.base.utils.Constant;
import com.wjx.android.wanandroidmvp.bean.me.LoginData;
import com.wjx.android.wanandroidmvp.contract.me.Contract;

import java.io.IOException;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2019/12/31
 * Time: 14:19
 */
public class LoginModel implements Contract.ILoginModel {
    @Override
    public Observable<LoginData> login(String userName, String passWord) {
        return getApiServer().loadLoginData(userName, passWord);
    }
    /**
     * 获取请求对象
     * @return 当前的请求对象
     */
    private ApiServer getApiServer() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(new ReadCookieInterceptor());
        builder.addInterceptor(new WriteCookieInterceptor(true));
        Retrofit retrofit = new Retrofit.Builder()
                .client(builder.build()).baseUrl(Constant.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        return apiServer;
    }

    public class ReadCookieInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            Request.Builder builder = chain.request().newBuilder();
            long expire = SPUtils.getInstance("cookie").getLong("cookie_expire", 0);
            if (expire > System.currentTimeMillis()) {
                String cookies = SPUtils.getInstance("cookie").getString("user");
                if (!TextUtils.isEmpty(cookies)) {
                    for (String cookie : cookies.split(";")) {
                        builder.addHeader("Cookie", cookie);
                    }
                    return chain.proceed(builder.build());
                }
            }
            return response;
        }
    }
    public class WriteCookieInterceptor implements Interceptor {

        private boolean mSaveCookie;

        public WriteCookieInterceptor(boolean saveCookie) {
            this.mSaveCookie = saveCookie;
        }

        @TargetApi(Build.VERSION_CODES.N)
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            if (mSaveCookie) {
                List<String> headers = response.headers("Set-Cookie");
                if (!headers.isEmpty()) {
                    StringBuilder sb = new StringBuilder();
                    headers.stream().forEach(h -> {
                        sb.append(h).append(";");
                    });
                    SPUtils.getInstance("cookie").put("user", sb.toString());
                    SPUtils.getInstance("cookie").put("cookie_expire", System.currentTimeMillis() + 30 * 24 * 3600 * 1000L);
                }
            }
            return response;
        }
    }

}
