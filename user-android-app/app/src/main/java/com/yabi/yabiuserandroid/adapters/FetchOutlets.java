package com.yabi.yabiuserandroid.adapters;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.appspot.yabiapp.yabi.Yabi;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserMerchantList;
import com.yabi.yabiuserandroid.utils.YabiUtils;

import java.io.IOException;

/**
 * Created by yogeshmadaan on 02/05/16.
 */
public class FetchOutlets extends AsyncTaskLoader<CloudEndpointsUserMerchantList> {

    private Context context = null;
    Yabi service = null;
    int category;
    public FetchOutlets(Context context,Yabi service,int category)
    {
        super(context);
        this.context = context;
        this.service = service;
        this.category = category;
    }
    @Override
    public CloudEndpointsUserMerchantList loadInBackground() {
        try {
            if(service ==null)
                service = YabiUtils.getBuilder(context).build();
            Log.e("category ",""+category);
            return service.user().getMerchantsList().setCategory((long) category).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

