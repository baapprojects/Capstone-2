package com.yabi.yabiuserandroid.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.ui.uiutils.palette.CustomFontTextView;
import com.yabi.yabiuserandroid.utils.SharedPrefUtils;
import com.yabi.yabiuserandroid.utils.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class ReferActivityFragment extends Fragment {

    @Bind(R.id.txt_invite_code)
    CustomFontTextView txtInviteCode;
    @Bind(R.id.btn_invite)
    Button btnInvite;
    @OnClick(R.id.btn_invite)
    public void btnInviteClick()
    {
        Utils.shareUsingApps(getActivity(),txtInviteCode.getText().toString(),getResources().getString(R.string.string_share_message));
    }
    public ReferActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_refer, container, false);
        ButterKnife.bind(this,rootView);
        txtInviteCode.setText(new SharedPrefUtils(getActivity()).getUserName().substring(0,4)+"1234");
        return rootView;
    }
}
