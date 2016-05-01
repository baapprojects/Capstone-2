package com.yabi.yabiuserandroid.ui.uiutils;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.activities.FaqActivity;
import com.yabi.yabiuserandroid.activities.FavouritesActivity;
import com.yabi.yabiuserandroid.activities.HomeActivity;
import com.yabi.yabiuserandroid.activities.ProfileActivity;
import com.yabi.yabiuserandroid.activities.ReferActivity;
import com.yabi.yabiuserandroid.utils.SharedPrefUtils;

/**
 * Created by yogeshmadaan on 11/11/15.
 */
public class DrawerUtil {
    public static Drawer drawer;
    public static Drawer setUpDrawer(final AppCompatActivity activity, Toolbar toolbar, int selectedItem)
    {
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIcon(R.drawable.ic_home_black_24dp).withName(activity.getResources().getString(R.string.drawer_home)).withTintSelectedIcon(true).withIconTinted(false).withIconTintingEnabled(true);
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIcon(R.drawable.ic_face_black_24dp).withName(activity.getResources().getString(R.string.drawer_profile)).withTintSelectedIcon(true).withIconTinted(false).withIconTintingEnabled(true);
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIcon(R.drawable.ic_credit_card_black_24dp).withName(activity.getResources().getString(R.string.drawer_saved_deals)).withTintSelectedIcon(true).withIconTinted(false).withIconTintingEnabled(true);
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIcon(R.drawable.ic_card_giftcard_black_24dp).withName(activity.getResources().getString(R.string.drawer_refer)).withTintSelectedIcon(true).withIconTinted(false).withIconTintingEnabled(true);
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIcon(R.drawable.ic_star_black_24dp).withName(activity.getResources().getString(R.string.drawer_rate)).withTintSelectedIcon(true).withIconTinted(false).withIconTintingEnabled(true);
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIcon(R.drawable.ic_feedback_black_24dp).withName(activity.getResources().getString(R.string.drawer_feedback)).withTintSelectedIcon(true).withIconTinted(false).withIconTintingEnabled(true);
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIcon(R.drawable.ic_error_outline_black_24dp).withName(activity.getResources().getString(R.string.drawer_faq)).withTintSelectedIcon(true).withIconTinted(false).withIconTintingEnabled(true);

        final AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(activity)
                .withHeaderBackground(R.drawable.drawer)
                .withSelectionListEnabledForSingleProfile(false)
                .withOnlyMainProfileImageVisible(true)
                .withCurrentProfileHiddenInList(true)
                .withProfileImagesVisible(false)
                .withCompactStyle(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(new SharedPrefUtils(activity).getUserName()).withEmail(new SharedPrefUtils(activity).getUserEmail())
                )
                .withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                    @Override
                    public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                        return false;
                    }
                })
                .build();

       drawer = new DrawerBuilder()
               .addDrawerItems(item1,item2,item3,item4,item5,item6,item7)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        switch(i)
                        {

                            case 1:
                                activity.startActivity(new Intent(activity, HomeActivity.class));
                                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                return false;
                            case 2:
                                activity.startActivity(new Intent(activity, ProfileActivity.class));
                                activity.overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                                return false;
                            case 3:
                                activity.startActivity(new Intent(activity, FavouritesActivity.class));
                                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                return false;
                            case 4:
                                activity.startActivity(new Intent(activity, ReferActivity.class));
                                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                return false;
                            case 5:
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                intent.setData(Uri.parse("market://details?id=" + activity.getPackageName()));
                                if (!MyStartActivity(intent,activity)) {
                                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id="+activity.getPackageName()));
                                    if (!MyStartActivity(intent,activity)) {
                                        //Well if this also fails, we have run out of options, inform the user.
                                        Toast.makeText(activity, "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                drawer.closeDrawer();
                                return false;
                            case 7:
                                activity.startActivity(new Intent(activity, FaqActivity.class));
                                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                return false;
//                            case 6:
//                                Intent intent = new Intent(activity, OutletsListActivity.class);
//                                Bundle bundle = new Bundle();
//                                bundle.putString("choiceMode", OutletsAdapter.OutletChoiceMode.BrowseOutlet.toString());
//                                intent.putExtras(bundle);
//                                activity.startActivity(intent);
//                                activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
//                                return false;
                        }
                        return false;
                    }
                })
                .withActivity(activity)
               .withActionBarDrawerToggle(true)
                .withToolbar(toolbar)
                .withFullscreen(false)
               .withSelectedItemByPosition(selectedItem)
                .withAccountHeader(headerResult)
                .withHeaderDivider(true)
                .build();
//        for (PrimaryDrawerItem item : items)
//        {
//            drawer.addItem(item);
//        }
//        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        Glide.with(activity).load(new SharedPrefUtils(activity).getUserCoverPic()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Log.e("cover pic","complete");
////                headerResult.setBackground(new BitmapDrawable(activity.getResources(),resource));
////                drawer.getDrawerLayout().requestLayout();
//            }
//        });
//        Glide.with(activity).load(new SharedPrefUtils(activity).getUserProfilePic()).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                Log.e("user pic","complete");
////                headerResult.getActiveProfile().withIcon(resource);
////                drawer.getDrawerLayout().requestLayout();
////                drawer.getHeader().refreshDrawableState();
////                drawer.getHeader().requestLayout();
//            }
//        });
//        DrawerImageLoader.init(new AbstractDrawerImageLoader() {
//            @Override
//            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
//                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
//            }
//
//            @Override
//            public void cancel(ImageView imageView) {
//                Glide.clear(imageView);
//            }
//        });
//        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
//            @Override
//            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
//                Glide.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
//            }
//
//            @Override
//            public void cancel(ImageView imageView) {
//                Glide.clear(imageView);
//            }
//
//            @Override
//            public Drawable placeholder(Context ctx) {
//                return null;
//            }
//
//            @Override
//            public Drawable placeholder(Context ctx, String tag) {
//                return null;
//            }
//        });
        return drawer;
    }
    private static boolean MyStartActivity(Intent aIntent,AppCompatActivity activity) {
        try
        {
            activity.startActivity(aIntent);
            return true;
        }
        catch (ActivityNotFoundException e)
        {
            return false;
        }
    }
}
