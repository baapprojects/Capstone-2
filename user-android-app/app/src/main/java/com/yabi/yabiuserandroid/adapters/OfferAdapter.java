package com.yabi.yabiuserandroid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appspot.yabiapp.yabi.model.CloudEndpointsUserOffer;
import com.yabi.yabiuserandroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yogeshmadaan on 18/02/16.
 */
public class OfferAdapter extends ArrayAdapter {

    Context context;
    List<CloudEndpointsUserOffer> items;

    public OfferAdapter(Context context, List<CloudEndpointsUserOffer> items) {
        super(context, R.layout.list_item_offer);
        this.context = context;
        this.items = items;
        if(this.items == null)
            this.items = new ArrayList<>();
    }

    @Override
    public int getCount() {
//        return i;
        return items.size();
    }

    @Override
    public CloudEndpointsUserOffer getItem(int position) {
        return items.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ChildViewHolder childViewHolder;
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.list_item_offer, null, false);
                childViewHolder = new ChildViewHolder();
                childViewHolder.offerTitle = (TextView) convertView.findViewById(R.id.txt_offer_title);
                childViewHolder.offerDescription = (TextView) convertView.findViewById(R.id.txt_offer_description);
                childViewHolder.offerExpiry = (TextView) convertView.findViewById(R.id.txt_offer_tnc);

                convertView.setTag(childViewHolder);
            } else {
                childViewHolder = (ChildViewHolder) convertView.getTag();
            }
        final CloudEndpointsUserOffer cloudEndpointsUserOffer = items.get(position);
        childViewHolder.offerTitle.setText(cloudEndpointsUserOffer.getTitle());
        childViewHolder.offerDescription.setText(cloudEndpointsUserOffer.getDescription());
        childViewHolder.offerExpiry.setText(cloudEndpointsUserOffer.getTNC());
        return convertView;
    }

    static class ChildViewHolder {
        TextView offerTitle, offerDescription,offerExpiry;

    }
}
