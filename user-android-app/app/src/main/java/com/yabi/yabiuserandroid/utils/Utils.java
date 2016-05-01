package com.yabi.yabiuserandroid.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.NotificationManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.appspot.yabiapp.yabi.model.CloudEndpointsUserMerchant;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserOffer;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.activities.SplashActivity;
import com.yabi.yabiuserandroid.provider.OffersProvider;
import com.yabi.yabiuserandroid.provider.OffersSQLiteHelper;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

/**
 * Created by yogeshmadaan on 31/08/15.
 */

public class Utils {

    private static final String LT_PREFIX = "LTM";
    private final static String MS_DATE_FORMAT = "yy-MM-dd HH:mm:ss";
    private final static int FONT_SIZE_27 = 27;
    private final static int FONT_SIZE_16 = 16;


    /**
     * Android unique device id
     *
     * @param context
     * @return
     */
    public static String getAndroidDeviceId(Context context) {
        String deviceId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return deviceId;
    }

    /**
     * Checks if the device is connected to the Internet.
     *
     * @param context
     * @return
     */
    public static boolean isConnectedToInternet(Context context) {
        if (context != null) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setBackgroundOfView(Drawable drawable, View v) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            v.setBackground(drawable);
        } else {
            v.setBackgroundDrawable(drawable);
        }
    }

    /**
     * No internet dialog
     *
     * @param context
     * @return
     */
    /**
     * Show error dialog
     *
     * @param context
     * @return
     */
    public static boolean showSuccessDialog(Context context, String successMessage, String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(successMessage);
        builder.setPositiveButton(context.getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
        return false;
    }

    /**
     * hide keyboard for the edittext
     *
     * @param context
     * @param editText
     */
    public static void hideKeyboardForField(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void hideKeyboardForField(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    public static void showKeyboardForField(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static String replaceAllSpecialCharsAndSpaces(String text) {
        if (!TextUtils.isEmpty(text)) {
            text = text.replaceAll("[^\\w\\s]", "");
            text = text.replaceAll(" ", "");
        }
        return text;
    }


    public static boolean isLocationServiceEnabled(final Context context) {
        LocationManager lm = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
                && !lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            return false;
        }
        return true;
    }

    public static String generateLTX() {
        String currentTimestamp = getMSDateString(new Date());
        String txnId = LT_PREFIX + currentTimestamp
                + generateRandomNumberLTX().toString();
        txnId = txnId.replaceAll("-", "").replaceAll(" ", "")
                .replaceAll(":", "").replace(".", "");
        return txnId;
    }

    private static Integer generateRandomNumberLTX() {
        Random rn = new Random();
//        int number = (1 + rn.nextInt(100)) * 10 + (1 + rn.nextInt(1000)) * 100
//                + (1 + rn.nextInt(10000)) * 1000 + (1 + rn.nextInt(10000));
        int number = (1 + rn.nextInt(1000)) * 10 + (1 + rn.nextInt(1000));
        return number;
    }

    public static String getMSDateString(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(MS_DATE_FORMAT);
        return sdf.format(date);
    }

    public static Integer getPxInDpUnits(Context ctxt, Integer px) {
        float scale = ctxt.getResources().getDisplayMetrics().density;
        Integer dps = (int) ((px - 0.5) / scale);
        return dps;
    }

    public static Integer getDpInPxUnits(Context ctxt, Integer dps) {
        float scale = ctxt.getResources().getDisplayMetrics().density;
        Integer px = (int) (dps * scale + 0.5f);
        return px;
    }

//    public static boolean isServiceRunning(Context context, Class serviceName) {
//        ActivityManager manager = (ActivityManager) context
//                .getSystemService(Context.ACTIVITY_SERVICE);
//        for (ActivityManager.RunningServiceInfo service : manager
//                .getRunningServices(Integer.MAX_VALUE)) {
//            if (serviceName.getName().equals(service.service.getClassName())) {
//                return true;
//            }
//        }
//        return false;
//    }

    public static Date getDisplayDate(Date date, String timeZoneOffset) {
        TimeZone timeZone = TimeZone.getTimeZone("GMT" + timeZoneOffset);
        Calendar cal = Calendar.getInstance(timeZone);
        cal.setTimeZone(timeZone);
        cal.setTime(date);
        cal.add(Calendar.MILLISECOND, timeZone.getRawOffset());
        return cal.getTime();

    }

    public static void launchAppMarketUrl(Activity activity) {
        String appPackageName = activity.getPackageName();
        try {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            activity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }



    public static void shareUsingApps(Context context, String subject, String text) {
//        String shareBody = "Here is the share content body";
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, text);
        context.startActivity(Intent.createChooser(sharingIntent, "Share Using"));

    }

    public static boolean isFragmentAdded(Fragment fragment, Activity activity) {
        if (fragment.isAdded() && activity != null) {
            return true;
        }
        return false;
    }

    public static Calendar parseTime(String _time) {
        final String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Calendar startCalendar = Calendar.getInstance();
        try {
            startCalendar.setTime(dateFormat.parse(_time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return startCalendar;
    }

    public static String parseTimeToDate(String _time) {

        Calendar startCalendar = Calendar.getInstance();
        try {
            final String format = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            startCalendar.setTime(dateFormat.parse(_time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String format = "yyyy-MM-dd";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(startCalendar.getTime());
    }
    public static File getInternalStorageLocation(Context context, String filename)
    {
        ContextWrapper cw = new ContextWrapper(context);
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("splashImages", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,filename);
        return mypath;
    }

        public static String findDateDifference(String date)
        {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d1 = null;
            Date d2 = null;

            try {
                d1 = format.parse(date);
                d2 = new Date();

                //in milliseconds
                long diff = d2.getTime() - d1.getTime();

                long diffSeconds = diff / 1000 % 60;
                long diffMinutes = diff / (60 * 1000) % 60;
                long diffHours = diff / (60 * 60 * 1000) % 24;
                long diffDays = diff / (24 * 60 * 60 * 1000);
                long diffYears = diffDays / 365;
                System.out.print(diffDays + " days, ");
                System.out.print(diffHours + " hours, ");
                System.out.print(diffMinutes + " minutes, ");
                System.out.print(diffSeconds + " seconds.");
                if (diffYears > 0) {
                    return String.valueOf(diffYears) + " years ago";
                } else if (diffDays > 0) {
                    return String.valueOf(diffDays) + " days ago";
                } else if (diffHours > 0) {
                    return String.valueOf(diffHours) + " hours ago";
                } else if (diffMinutes > 0) {
                    return String.valueOf(diffMinutes) + " minutes ago";
                } else return "Just Now";

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

    public static  int getScreenWidth(Context context)
    {
        Display display = ((AppCompatActivity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return width;
    }
    public static int getScreenHeight(Context context)
    {
        Display display = ((AppCompatActivity)context).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        return height;
    }


    public static Bitmap loadImageFromStorage(String path)
    {
        Bitmap b = null;
        try {
            File f=new File(path);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    return b;
    }


    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static void restartActivity(Activity activity)
    {
        Intent intent = new Intent(activity, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        if(activity!=null)
            activity.finish();
    }
    public static void restartActivity(Context context)
    {
        Intent intent = new Intent(context, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void cancelNotification(Context ctx, int notifyId) {
        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager nMgr = (NotificationManager) ctx.getSystemService(ns);
        nMgr.cancel(300);
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static enum PaymentType {
        LOAD_MONEY, CITRUS_CASH, PG_PAYMENT, DYNAMIC_PRICING;
    }

    public enum DPRequestType {
        SEARCH_AND_APPLY, CALCULATE_PRICING, VALIDATE_RULE;
    }


    /**
     * Convert byte array to hex string
     * @param bytes
     * @return
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder sbuf = new StringBuilder();
        for(int idx=0; idx < bytes.length; idx++) {
            int intVal = bytes[idx] & 0xff;
            if (intVal < 0x10) sbuf.append("0");
            sbuf.append(Integer.toHexString(intVal).toUpperCase());
        }
        return sbuf.toString();
    }

    /**
     * Get utf8 byte array.
     * @param str
     * @return  array of NULL if error was found
     */
    public static byte[] getUTF8Bytes(String str) {
        try { return str.getBytes("UTF-8"); } catch (Exception ex) { return null; }
    }

    /**
     * Load UTF8withBOM or any ansi text file.
     * @param filename
     * @return
     * @throws java.io.IOException
     */
    public static String loadFileAsString(String filename) throws java.io.IOException {
        final int BUFLEN=1024;
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(filename), BUFLEN);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(BUFLEN);
            byte[] bytes = new byte[BUFLEN];
            boolean isUTF8=false;
            int read,count=0;
            while((read=is.read(bytes)) != -1) {
                if (count==0 && bytes[0]==(byte)0xEF && bytes[1]==(byte)0xBB && bytes[2]==(byte)0xBF ) {
                    isUTF8=true;
                    baos.write(bytes, 3, read-3); // drop UTF8 bom marker
                } else {
                    baos.write(bytes, 0, read);
                }
                count+=read;
            }
            return isUTF8 ? new String(baos.toByteArray(), "UTF-8") : new String(baos.toByteArray());
        } finally {
            try{ is.close(); } catch(Exception ex){}
        }
    }

    /**
     * Returns MAC address of the given interface name.
     * @param interfaceName eth0, wlan0 or NULL=use first interface
     * @return  mac address or empty string
     */
    public static String getMACAddress(String interfaceName) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                if (interfaceName != null) {
                    if (!intf.getName().equalsIgnoreCase(interfaceName)) continue;
                }
                byte[] mac = intf.getHardwareAddress();
                if (mac==null) return "";
                StringBuilder buf = new StringBuilder();
                for (int idx=0; idx<mac.length; idx++)
                    buf.append(String.format("%02X:", mac[idx]));
                if (buf.length()>0) buf.deleteCharAt(buf.length()-1);
                return buf.toString();
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
        /*try {
            // this is so Linux hack
            return loadFileAsString("/sys/class/net/" +interfaceName + "/address").toUpperCase().trim();
        } catch (IOException ex) {
            return null;
        }*/
    }

    /**
     * Get IP address from first non-localhost interface
     * @param ipv4  true=return ipv4, false=return ipv6
     * @return  address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':')<0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim<0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) { } // for now eat exceptions
        return "";
    }
    public static void toggleWifi(Context context, boolean value)
    {
        WifiManager wifiManager;
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            wifiManager.setWifiEnabled(value);

    }

    public static String formatDate(Long timestamp, String dateFormat)
    {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        return format.format(new Date(timestamp));
    }

    public static Comparator<CloudEndpointsUserMerchant> merchantNameComparator = new Comparator<CloudEndpointsUserMerchant>() {
        @Override
        public int compare(CloudEndpointsUserMerchant lhs, CloudEndpointsUserMerchant rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    };

    public static int toggleFavourite(Context context, CloudEndpointsUserOffer cloudEndpointsUserOffer)
    {

        Uri.Builder uriBuilder = OffersProvider.CONTENT_URI.buildUpon();

        if(isFavourite(context, cloudEndpointsUserOffer))
            context.getContentResolver().delete(uriBuilder.build(),String.valueOf(createOfferId(cloudEndpointsUserOffer)),null);
        else
        {
            ContentValues contentValues = new ContentValues();
            contentValues.put(OffersSQLiteHelper.ID, createOfferId(cloudEndpointsUserOffer));
            contentValues.put(OffersSQLiteHelper.TITLE, cloudEndpointsUserOffer.getTitle());
            contentValues.put(OffersSQLiteHelper.CODE, cloudEndpointsUserOffer.getCode());
            contentValues.put(OffersSQLiteHelper.DESCRIPTION, cloudEndpointsUserOffer.getDescription());
            contentValues.put(OffersSQLiteHelper.MECHANT_ID, cloudEndpointsUserOffer.getMerchantId());
            contentValues.put(OffersSQLiteHelper.TNC, cloudEndpointsUserOffer.getTNC());
            context.getContentResolver().insert(OffersProvider.CONTENT_URI, contentValues);
        }
        return 0;
    }

    public static void saveFavourite(Context context, CloudEndpointsUserOffer cloudEndpointsUserOffer)
    {
        Uri.Builder uriBuilder = OffersProvider.CONTENT_URI.buildUpon();


            ContentValues contentValues = new ContentValues();
            contentValues.put(OffersSQLiteHelper.ID, createOfferId(cloudEndpointsUserOffer));
            contentValues.put(OffersSQLiteHelper.TITLE, cloudEndpointsUserOffer.getTitle());
            contentValues.put(OffersSQLiteHelper.CODE, cloudEndpointsUserOffer.getCode());
            contentValues.put(OffersSQLiteHelper.DESCRIPTION, cloudEndpointsUserOffer.getDescription());
            contentValues.put(OffersSQLiteHelper.MECHANT_ID, cloudEndpointsUserOffer.getMerchantId());
            contentValues.put(OffersSQLiteHelper.TNC, cloudEndpointsUserOffer.getTNC());
            context.getContentResolver().insert(OffersProvider.CONTENT_URI, contentValues);

    }
    public static boolean isFavourite(Context context,CloudEndpointsUserOffer cloudEndpointsUserOffer)
    {
        String URL = OffersProvider.URL;
        Uri movies = Uri.parse(URL);
        Cursor cursor = null;
        cursor = context.getContentResolver().query(movies, null, OffersSQLiteHelper.ID+" = "+createOfferId(cloudEndpointsUserOffer), null, OffersSQLiteHelper.ROW_ID);
        if (cursor != null&&cursor.moveToNext()) {
            return true;
        } else {
            return false;
        }
    }

    public static List<CloudEndpointsUserOffer> getFavouriteOffers(Context context)
    {
        List<CloudEndpointsUserOffer> offers = new ArrayList<>();
        String URL = OffersProvider.URL;
        Uri movie = Uri.parse(URL);
        Cursor cursor = null;
        cursor = context.getContentResolver().query(movie, null, null, null, OffersSQLiteHelper.ROW_ID);
        if (cursor != null) {
            while (cursor.moveToNext())
            {
                CloudEndpointsUserOffer cloudEndpointsUserOffer = new CloudEndpointsUserOffer();
                cloudEndpointsUserOffer.setCode(cursor.getString(cursor.getColumnIndex(OffersSQLiteHelper.CODE)));
                cloudEndpointsUserOffer.setDescription(cursor.getString(cursor.getColumnIndex(OffersSQLiteHelper.DESCRIPTION)));
                cloudEndpointsUserOffer.setMerchantId(cursor.getString(cursor.getColumnIndex(OffersSQLiteHelper.MECHANT_ID)));
                cloudEndpointsUserOffer.setTitle(cursor.getString(cursor.getColumnIndex(OffersSQLiteHelper.TITLE)));
                cloudEndpointsUserOffer.setTNC(cursor.getString(cursor.getColumnIndex(OffersSQLiteHelper.TNC)));
                offers.add(cloudEndpointsUserOffer);
            }
        }
        if(offers.size()==0)
            Toast.makeText(context,context.getResources().getString(R.string.text_no_favourites),Toast.LENGTH_LONG).show();
        return offers;
    }

    public static int createOfferId(CloudEndpointsUserOffer cloudEndpointsUserOffer)
    {
        int id ;
        String s = cloudEndpointsUserOffer.getMerchantId()+"_"+cloudEndpointsUserOffer.getCode();
        id = s.hashCode();
        return id;
    }
}
