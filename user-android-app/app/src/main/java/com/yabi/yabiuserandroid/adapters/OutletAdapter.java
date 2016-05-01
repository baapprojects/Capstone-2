package com.yabi.yabiuserandroid.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.appspot.yabiapp.yabi.model.CloudEndpointsUserMerchant;
import com.bumptech.glide.Glide;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.ui.uiutils.palette.CustomFontTextView;

import java.util.List;

/**
 * Created by yogeshmadaan on 09/02/16.
 */
public class OutletAdapter extends RecyclerView.Adapter<OutletAdapter.OfferViewHolder>{

    Context context = null;
    List<CloudEndpointsUserMerchant> merchants = null;
    int actualPosterViewWidth =0;
    private OfferClickInterface offerClickInterface;
    private static final double TMDB_POSTER_SIZE_RATIO = 2/3;

    public OutletAdapter(Context context, List<CloudEndpointsUserMerchant> merchants, int actualPosterViewWidth, OfferClickInterface offerClickInterface)
    {
        this.context = context;
        this.merchants = merchants;
        this.actualPosterViewWidth = actualPosterViewWidth;
        this.offerClickInterface = offerClickInterface;
    }
    @Override
    public OfferViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_outlet, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new OfferViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final OfferViewHolder holder, final int position) {
//        Glide.with(context).load(TMDbUtils.buildPosterUrl(movies.get(position).getPosterPath(), actualPosterViewWidth)).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.drawable.movie_placeholder).error(R.drawable.movie_placeholder).
//                into(new SimpleTarget<Bitmap>(actualPosterViewWidth,(int)(actualPosterViewWidth/TMDB_POSTER_SIZE_RATIO)) {
//                    @Override
//                    public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
//                        holder.imageView.setImageBitmap(resource); // Possibly runOnUiThread()
//                    }
//                });
//        holder.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                offerClickInterface.onOfferClick(holder.itemView,movies.get(position),false);
//            }
//        });
        final CloudEndpointsUserMerchant cloudEndpointsUserMerchant = merchants.get(position);
        holder.txtMerchantName.setText(cloudEndpointsUserMerchant.getName());
        holder.txtMerchantDesciption.setText(cloudEndpointsUserMerchant.getDescription());
        holder.txtMerchantOfferCount.setText(cloudEndpointsUserMerchant.getNoOfOffers()+" Offer Available");
        Glide.with(context).load(cloudEndpointsUserMerchant.getLogo()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(holder.imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                offerClickInterface.onOfferClick(holder.itemView,cloudEndpointsUserMerchant,false);
            }
        });
    }

    @Override
    public int getItemCount() {
//        return 100;
        return merchants.size();
    }

    public class OfferViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public CustomFontTextView txtMerchantName;
        public CustomFontTextView txtMerchantDesciption;
        public CustomFontTextView txtMerchantOfferCount;

        public OfferViewHolder(View itemView)
        {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_logo);
            txtMerchantName = (CustomFontTextView) itemView.findViewById(R.id.txt_merchant_title);
            txtMerchantDesciption = (CustomFontTextView) itemView.findViewById(R.id.txt_merchant_description);
            txtMerchantOfferCount = (CustomFontTextView) itemView.findViewById(R.id.txt_merchant_offer_count);

        }

    }

    public interface OfferClickInterface
    {
        void onOfferClick(View itemView, CloudEndpointsUserMerchant merchant, boolean isDefaultSelection);
    }


}

