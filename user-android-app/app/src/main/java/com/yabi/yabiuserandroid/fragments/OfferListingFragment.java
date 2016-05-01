package com.yabi.yabiuserandroid.fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.appspot.yabiapp.yabi.Yabi;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserMerchant;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserMerchantDetails;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserOffer;
import com.bumptech.glide.Glide;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.adapters.OfferAdapter;
import com.yabi.yabiuserandroid.ui.uiutils.palette.CustomFontTextView;
import com.yabi.yabiuserandroid.utils.Utils;
import com.yabi.yabiuserandroid.utils.YabiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OfferListingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OfferListingFragment extends Fragment implements LoaderManager.LoaderCallbacks<CloudEndpointsUserMerchantDetails>{
   public static final String KEY_MERCHANT = "merchant";
    static CloudEndpointsUserMerchant merchant;
    @Bind(R.id.list_view)
    ListView listView;
    View header;
    OfferAdapter offerAdapter;
    List<CloudEndpointsUserOffer> offers;
    static Yabi service = null;
    CustomFontTextView txtMerchantTitle;
    ImageView imgBackdrop;
    public static OfferListingFragment newInstance(CloudEndpointsUserMerchant merchant) {
        OfferListingFragment fragment = new OfferListingFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_MERCHANT, merchant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            merchant = (CloudEndpointsUserMerchant) getArguments().getSerializable(KEY_MERCHANT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_offer_listing, container, false);
        ButterKnife.bind(this,rootView);
        header = inflater.inflate(R.layout.offer_list_header,null,false);
        initViews();
        getActivity().getSupportLoaderManager().initLoader(1, null, this).forceLoad();
        return rootView;
    }
    public void initViews()
    {
        txtMerchantTitle = (CustomFontTextView)header.findViewById(R.id.txt_merchant_title);
        txtMerchantTitle.setText(merchant.getName());
        imgBackdrop = (ImageView) header.findViewById(R.id.img_backdrop);
        Glide.with(getActivity()).load(merchant.getCover()).placeholder(R.drawable.drawer).error(R.drawable.drawer).into(imgBackdrop);
        offers = new ArrayList<>();
        offerAdapter = new OfferAdapter(getActivity(),offers);
        listView.setAdapter(offerAdapter);
        listView.addHeaderView(header);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOfferDetails(offers.get(position-1));
            }
        });
        getOffers();
    }
    public void getOffers()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                service = YabiUtils.getBuilder(getActivity()).build();

//                fetchOffers();
            }
        }).start();

    }



    @Override
    public Loader<CloudEndpointsUserMerchantDetails> onCreateLoader(int id, Bundle args) {
        return new fetchOffers(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<CloudEndpointsUserMerchantDetails> loader, CloudEndpointsUserMerchantDetails cloudEndpointsUserMerchantDetails) {
        if (cloudEndpointsUserMerchantDetails.getOffers() != null && cloudEndpointsUserMerchantDetails.getOffers().size() > 0) {
            offers.clear();
            offers.addAll(cloudEndpointsUserMerchantDetails.getOffers());
            offerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<CloudEndpointsUserMerchantDetails> loader) {
        offers.clear();
        offerAdapter.notifyDataSetChanged();

    }

    public static class fetchOffers extends AsyncTaskLoader<CloudEndpointsUserMerchantDetails>{

        Context context =null;
        public fetchOffers(Context context)
        {
            super(context);
            this.context = context;
        }
        @Override
        public CloudEndpointsUserMerchantDetails loadInBackground() {
            try {
                if(service ==null)
                    service = YabiUtils.getBuilder(context).build();
                return service.user().getMerchant().setId(merchant.getId()).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    public void showOfferDetails(final CloudEndpointsUserOffer cloudEndpointsUserOffer)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(cloudEndpointsUserOffer.getTitle());
        SpannableString spannableString = new SpannableString("Use Coupon \n" + cloudEndpointsUserOffer.getCode() +"\n to avail the offer.\n\n*"+cloudEndpointsUserOffer.getTNC());
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 11, 11+cloudEndpointsUserOffer.getCode().length()+1, 0);
        spannableString.setSpan(new RelativeSizeSpan(2.5f), 11, 11+cloudEndpointsUserOffer.getCode().length()+1, 0);
        spannableString.setSpan(new ForegroundColorSpan(Color.GREEN), 11, 11+cloudEndpointsUserOffer.getCode().length()+1, 0);
        spannableString.setSpan(new ForegroundColorSpan(Color.GRAY), spannableString.length() - cloudEndpointsUserOffer.getTNC().length(), spannableString.length(), 0);

        builder.setMessage(spannableString);
        builder.setPositiveButton(getActivity().getString(android.R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        String buttonText = Utils.isFavourite(getActivity(),cloudEndpointsUserOffer)?getResources().getString(R.string.string_remove_coupon):getResources().getString(R.string.string_save_coupon);
        builder.setNeutralButton(buttonText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(cloudEndpointsUserOffer.getMerchantId()==null)
                            cloudEndpointsUserOffer.setMerchantId(String.valueOf(merchant.getId()));
                        Utils.toggleFavourite(getActivity(),cloudEndpointsUserOffer);
                    }
                });
        AlertDialog dialog = builder.show();
        TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }

}
