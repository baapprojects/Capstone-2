package com.yabi.yabiuserandroid.fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Toast;

import com.appspot.yabiapp.yabi.Yabi;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserMerchant;
import com.appspot.yabiapp.yabi.model.CloudEndpointsUserMerchantList;
import com.google.gson.Gson;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.adapters.OutletAdapter;
import com.yabi.yabiuserandroid.utils.MyLogger;
import com.yabi.yabiuserandroid.utils.YabiUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.BindDimen;
import butterknife.ButterKnife;


public class MerchantListingFragment extends Fragment implements OutletAdapter.OfferClickInterface{
    ViewGroup rootView;
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindDimen(R.dimen.minimum_column_width)
    int minimumColumnWidth,optimalColumnCount;
    GridLayoutManager gridLayoutManager;
    int actualPosterViewWidth;
    private List<CloudEndpointsUserMerchant> offers;
    private OutletAdapter outletAdapter;
    Yabi service = null;
    private int category;
    public static final String KEY_CATEGORY = "category";
    public MerchantListingFragment() {
        // Required empty public constructor
    }


    public static MerchantListingFragment newInstance(int category) {
        MerchantListingFragment fragment = new MerchantListingFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CATEGORY,category);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null)
        {
            category = getArguments().getInt(KEY_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_outlet_listing, container, false);
        ButterKnife.bind(this,rootView);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int gridWidth = recyclerView.getWidth();
                optimalColumnCount = Math.max(Math.round((1f * gridWidth) / minimumColumnWidth), 1);
                actualPosterViewWidth = gridWidth / optimalColumnCount;
                initViews();

            }
        });
        return rootView;
    }
public void initViews()
{
    swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            fetchOutlets();
        }
    });
    swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
    offers = new ArrayList<>();
    recyclerView.setHasFixedSize(true);
    outletAdapter = new OutletAdapter(getActivity(), offers, actualPosterViewWidth, this);
    gridLayoutManager = new GridLayoutManager(getActivity(), optimalColumnCount, LinearLayoutManager.VERTICAL, false);
    recyclerView.setLayoutManager(gridLayoutManager);
    recyclerView.setAdapter(outletAdapter);
    getOutlets();

}

    @Override
    public void onOfferClick(View itemView, CloudEndpointsUserMerchant merchant, boolean isDefaultSelection) {
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.main_frame, OfferListingFragment.newInstance(merchant)).addToBackStack(null).commit();

    }

    public void getOutlets()
    {
        swipeRefreshLayout.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {

                service = YabiUtils.getBuilder(getActivity()).build();
                fetchOutlets();
            }
        }).start();

    }
    public void fetchOutlets()
    {
       new fetchOutlets().execute();
    }

    private class fetchOutlets extends AsyncTask<Void , String, CloudEndpointsUserMerchantList>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(CloudEndpointsUserMerchantList cloudEndpointsUserMerchantList) {
            super.onPostExecute(cloudEndpointsUserMerchantList);
            MyLogger.d("merchants", "" + new Gson().toJson(cloudEndpointsUserMerchantList));
            swipeRefreshLayout.setRefreshing(false);
            if (cloudEndpointsUserMerchantList!=null && cloudEndpointsUserMerchantList.getMerchants() != null && cloudEndpointsUserMerchantList.getMerchants().size() > 0) {
                offers.addAll(cloudEndpointsUserMerchantList.getMerchants());
                outletAdapter.notifyDataSetChanged();
            }
            else if(cloudEndpointsUserMerchantList!=null && cloudEndpointsUserMerchantList.getMerchants()!=null && cloudEndpointsUserMerchantList.getMerchants().size()==0)
            {
                Toast.makeText(getActivity(),getResources().getString(R.string.string_no_merchants),Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected CloudEndpointsUserMerchantList doInBackground(Void... params) {
            try {
                return service.user().getMerchantsList().setCategory((long) category).execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

}
