package com.yabi.yabiuserandroid.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.appspot.yabiapp.yabi.model.CloudEndpointsUserOffer;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.adapters.OfferAdapter;
import com.yabi.yabiuserandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class FavouritesActivityFragment extends Fragment {
    @Bind(R.id.list_view)
    ListView listView;
    OfferAdapter offerAdapter;
    List<CloudEndpointsUserOffer> offers;

    public FavouritesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_favourites, container, false);
        ButterKnife.bind(this,rootView);
        initViews();
        return rootView;
    }
    public void initViews()
    {

        offers = new ArrayList<>();
        offers.addAll(Utils.getFavouriteOffers(getActivity()));
        offerAdapter = new OfferAdapter(getActivity(),offers);
        listView.setAdapter(offerAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showOfferDetails(offers.get(position));
            }
        });
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
        builder.setNeutralButton(getActivity().getString(R.string.string_remove_coupon),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.toggleFavourite(getActivity(),cloudEndpointsUserOffer);
                        refreshData();
                    }
                });
        AlertDialog dialog = builder.show();
        TextView messageView = (TextView)dialog.findViewById(android.R.id.message);
        messageView.setGravity(Gravity.CENTER);
    }

    public void refreshData()
    {
        offers.clear();
        offers.addAll(Utils.getFavouriteOffers(getActivity()));
        offerAdapter.notifyDataSetChanged();
    }
}
