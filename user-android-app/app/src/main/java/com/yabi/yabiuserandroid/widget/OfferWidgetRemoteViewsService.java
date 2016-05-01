package com.yabi.yabiuserandroid.widget;

import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.provider.OffersProvider;
import com.yabi.yabiuserandroid.provider.OffersSQLiteHelper;

/**
 * Created by yogeshmadaan on 09/03/16.
 */
public class OfferWidgetRemoteViewsService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
            private Cursor data = null;
            @Override
            public void onCreate() {
                // Nothing to do
            }

            @Override
            public void onDataSetChanged() {
                if (data != null) {
                    data.close();
                }

                // This method is called by the app hosting the widget (e.g., the launcher)
                // However, our ContentProvider is not exported so it doesn't have access to the
                // data. Therefore we need to clear (and finally restore) the calling identity so
                // that calls use our process and permission
                final long identityToken = Binder.clearCallingIdentity();

                // This is the same query from MyStocksActivity
                data = getContentResolver().query(
                        OffersProvider.CONTENT_URI,
                        new String[] {
                                OffersSQLiteHelper.ID,
                                OffersSQLiteHelper.TITLE,
                                OffersSQLiteHelper.DESCRIPTION,
                                OffersSQLiteHelper.CODE,
                                OffersSQLiteHelper.TNC,
                                OffersSQLiteHelper.MECHANT_ID
                        },
                        null,
                        null,
                        null);
                Binder.restoreCallingIdentity(identityToken);
            }

            @Override
            public void onDestroy() {

            }

            @Override
            public int getCount() {
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int position) {
                if (position == AdapterView.INVALID_POSITION ||
                        data == null || !data.moveToPosition(position)) {
                    return null;
                }

                // Get the layout
                RemoteViews views = new RemoteViews(getPackageName(), R.layout.widget_collection_item);

                // Bind data to the views
                views.setTextViewText(R.id.offer_title, data.getString(data.getColumnIndex(OffersSQLiteHelper.TITLE)));


                    views.setTextViewText(R.id.offer_code, data.getString(data.getColumnIndex(OffersSQLiteHelper.CODE)));

                Log.e("setting widget data",data.getString(data.getColumnIndex(OffersSQLiteHelper.CODE)));
                return views;
            }

            @Override
            public RemoteViews getLoadingView() {
                return null; // use the default loading view
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public long getItemId(int position) {
                // Get the row ID for the view at the specified position
                if (data != null && data.moveToPosition(position)) {
                    final int QUOTES_ID_COL = 0;
                    return data.getLong(QUOTES_ID_COL);
                }
                return position;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }
        };
    }
}