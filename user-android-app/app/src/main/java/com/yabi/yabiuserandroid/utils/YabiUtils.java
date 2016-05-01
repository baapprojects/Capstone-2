package com.yabi.yabiuserandroid.utils;

import android.content.Context;

import com.appspot.yabiapp.yabi.Yabi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.yabi.AppConstants;
import com.yabi.yabiuserandroid.R;

import java.io.IOException;

/**
 * Created by yogeshmadaan on 01/05/16.
 */
public class YabiUtils {

    public static Yabi.Builder getBuilder(Context context)
    {
        GoogleAccountCredential credential = GoogleAccountCredential.usingAudience(
                context, AppConstants.AUDIENCE);
        credential.setSelectedAccountName(new SharedPrefUtils(context).getUserEmail());
        Yabi.Builder builder = new Yabi.Builder(
                AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(),
                credential)
                .setRootUrl(Yabi.DEFAULT_ROOT_URL);
        builder.setHttpRequestInitializer(new HttpRequestInitializer() {
            @Override
            public void initialize(HttpRequest request) throws IOException {
                request.setConnectTimeout(R.integer.http_connection_timeout);
                request.setReadTimeout(R.integer.http_connection_timeout);
            }
        });
        return builder;
    }
}
