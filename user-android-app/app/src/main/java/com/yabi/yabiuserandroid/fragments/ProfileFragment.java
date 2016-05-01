package com.yabi.yabiuserandroid.fragments;


import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.yabi.yabiuserandroid.R;
import com.yabi.yabiuserandroid.ui.uiutils.palette.CustomFontTextView;
import com.yabi.yabiuserandroid.utils.SharedPrefUtils;
import com.yabi.yabiuserandroid.utils.Utils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    @Bind(R.id.txt_profile_phone)
    CustomFontTextView profilePhone;
    @Bind(R.id.txt_profile_gender)
    CustomFontTextView profileGender;
    @Bind(R.id.txt_profile_joining)
    CustomFontTextView profileJoining;
    @Bind(R.id.icon_email)
    ImageButton iconEmail;
    @Bind(R.id.icon_gender)
    ImageButton iconGender;
    @Bind(R.id.icon_joining)
    ImageButton iconJoining;
    @Bind(R.id.icon_phone)
    ImageButton iconPhone;
    @Bind(R.id.chart1)
    PieChart pieChart;

    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,rootView);
        initViews();
        return rootView;
    }

    public void initViews()
    {
        SharedPrefUtils sharedPrefUtils = new SharedPrefUtils(getActivity());
        profilePhone.setText(String.valueOf(sharedPrefUtils.getUserPhone()));
        profileGender.setText(sharedPrefUtils.getUserGender() +" , " + sharedPrefUtils.getUserAge()+" years");
        profileJoining.setText("Joined on "+ Utils.formatDate(sharedPrefUtils.getUserJoiningTimestamp(),"dd-MM-yyyy"));
        iconEmail.setColorFilter(getResources().getColor(R.color.color_primary), PorterDuff.Mode.SRC_IN);
        iconGender.setColorFilter(getResources().getColor(R.color.color_primary), PorterDuff.Mode.SRC_IN);
        iconJoining.setColorFilter(getResources().getColor(R.color.color_primary), PorterDuff.Mode.SRC_IN);
        iconPhone.setColorFilter(getResources().getColor(R.color.color_primary), PorterDuff.Mode.SRC_IN);
        setUpChart();
    }
    public void setUpChart()
    {
        pieChart.setUsePercentValues(true);
        pieChart.setDescription("");
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColorTransparent(true);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(58f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener

        setData(3, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

    }
    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

//         for (int i = 0; i < count + 1; i++)
            xVals.add("West Delhi");
        xVals.add("East Delhi");
        xVals.add("South Delhi");
        xVals.add("North Delhi");

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pieChart.setData(data);

        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Your favourite  \ncheckin history");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
//        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
//        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
//        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 15, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 15, s.length(), 0);
        return s;
    }
}
