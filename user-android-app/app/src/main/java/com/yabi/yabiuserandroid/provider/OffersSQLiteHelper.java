package com.yabi.yabiuserandroid.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yogeshmadaan on 01/05/16.
 */
public class OffersSQLiteHelper  extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FavouriteOffers";
    static final String TABLE_NAME = "favourites";
    static final int DATABASE_VERSION = 1;

    //fields for database
    public static final String ROW_ID = "id";
    public static final String ID ="offerId";
    public static final String TITLE = "title";
    public static final String CODE = "code";
    public static final String DESCRIPTION = "description";
    public static final String MECHANT_ID = "merchantId";
    public static final String TNC = "tnc";

    static final String CREATE_TABLE = " CREATE TABLE " + TABLE_NAME +" ( "+ROW_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, " + ID+ " INTEGER NOT NULL, " + "" +
            TITLE+" TEXT NOT NULL, " + CODE+" TEXT NOT NULL, " + DESCRIPTION+" TEXT NOT NULL, " + MECHANT_ID+" TEXT NOT NULL, "  + TNC+" TEXT NOT NULL); ";

    public OffersSQLiteHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Creating table",""+CREATE_TABLE);
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }
}
