package jp.issei.omizu.weightcalendar.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.presentation.internal.di.HasComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.DaggerPhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.internal.di.components.PhysicalMeasurementComponent;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;
import jp.issei.omizu.weightcalendar.presentation.viewmodel.PhysicalMeasurementChartViewModel;

public class PhysicalMeasurementChartActivity extends BaseActivity
        implements HasComponent<PhysicalMeasurementComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, PhysicalMeasurementChartActivity.class);
    }

    @Inject
    PhysicalMeasurementChartViewModel physicalMeasurementChartViewModel;

    private PhysicalMeasurementComponent physicalMeasurementComponent;

    private LineChart mChart;
    protected Typeface mTfLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_physical_measurement_chart);
        this.initializeInjector();

        this.physicalMeasurementChartViewModel.initialize();
        this.physicalMeasurementChartViewModel.setView(this);
        this.physicalMeasurementChartViewModel.loadPhysicalMeasurementList();
    }

    private void initializeInjector() {
        this.physicalMeasurementComponent = DaggerPhysicalMeasurementComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();

        this.physicalMeasurementComponent.inject(this);
    }

    public void setChartData(List<PhysicalMeasurementModel> listPhysicalMeasurement) {
        mTfLight = Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf");


        mChart = (LineChart) findViewById(R.id.chart1);

        // no description text
        mChart.getDescription().setEnabled(false);

        // enable touch gestures
        mChart.setTouchEnabled(true);

        mChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.setDrawGridBackground(false);
        mChart.setHighlightPerDragEnabled(true);

        // set an alternative background color
//        mChart.setBackgroundColor(Color.WHITE);
        mChart.setBackgroundColor(Color.LTGRAY);
//        mChart.setViewPortOffsets(0f, 0f, 0f, 0f);

        // add data
        this.setData(listPhysicalMeasurement);
        mChart.invalidate();

        // get the legend (only possible after setting data)
        Legend l = mChart.getLegend();
//        l.setEnabled(false);
        // modify the legend ...
        l.setForm(Legend.LegendForm.LINE);
        l.setTypeface(mTfLight);
        l.setTextSize(11f);
        l.setTextColor(Color.WHITE);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setDrawInside(false);
//        l.setYOffset(11f);

        XAxis xAxis = mChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
        xAxis.setTypeface(mTfLight);
        xAxis.setTextSize(10f);
        xAxis.setTextColor(Color.WHITE);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setTextColor(Color.rgb(255, 192, 56));
//        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new IAxisValueFormatter() {

//            private SimpleDateFormat mFormat = new SimpleDateFormat("dd MMM HH:mm");
            private SimpleDateFormat mFormat = new SimpleDateFormat("yyMMdd");

            @Override
            public String getFormattedValue(float value, AxisBase axis) {
//                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format((long) value);
            }
        });

        YAxis leftAxis = mChart.getAxisLeft();
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        leftAxis.setTypeface(mTfLight);
        leftAxis.setTextColor(ColorTemplate.getHoloBlue());
        leftAxis.setDrawGridLines(true);
        leftAxis.setGranularityEnabled(true);
        leftAxis.setAxisMinimum(50f);
        leftAxis.setAxisMaximum(70f);
//        leftAxis.setYOffset(-9f);
        leftAxis.setTextColor(Color.rgb(255, 192, 56));

//        YAxis rightAxis = mChart.getAxisRight();
//        rightAxis.setEnabled(false);
        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setTypeface(mTfLight);
        rightAxis.setTextColor(Color.RED);
        rightAxis.setAxisMaximum(20);
        rightAxis.setAxisMinimum(0);
        rightAxis.setDrawGridLines(false);
        rightAxis.setDrawZeroLine(false);
        rightAxis.setGranularityEnabled(false);
    }

    private void setData(List<PhysicalMeasurementModel> listPhysicalMeasurement) {
        ArrayList<Entry> weight = new ArrayList<>();
        ArrayList<Entry> bodyFatPercentage = new ArrayList<>();
        for (PhysicalMeasurementModel val : listPhysicalMeasurement) {
            if (val.getWeight() != null) {
                weight.add(new Entry(val.getDate().getTime(), val.getWeight()));
            }
            if (val.getBodyFatPercentage() != null) {
                bodyFatPercentage.add(new Entry(val.getDate().getTime(), val.getBodyFatPercentage()));
            }
        }

        // create a dataset and give it a type
        LineDataSet set1 = new LineDataSet(weight, "体重");
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set1.setColor(ColorTemplate.getHoloBlue());
        set1.setValueTextColor(ColorTemplate.getHoloBlue());
        set1.setLineWidth(1.5f);
        set1.setDrawCircles(false);
        set1.setDrawValues(false);
        set1.setFillAlpha(65);
        set1.setFillColor(ColorTemplate.getHoloBlue());
        set1.setHighLightColor(Color.rgb(244, 117, 117));
        set1.setDrawCircleHole(false);


        LineDataSet set3 = new LineDataSet(bodyFatPercentage, "体脂肪率");
        set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
        set3.setColor(Color.YELLOW);
        set3.setCircleColor(Color.WHITE);
        set3.setLineWidth(2f);
//        set3.setCircleRadius(3f);
        set3.setDrawCircles(false);
        set3.setFillAlpha(65);
        set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
        set3.setDrawCircleHole(false);
        set3.setHighLightColor(Color.rgb(244, 117, 117));


        // create a data object with the datasets
        LineData data = new LineData(set1, set3);
        data.setValueTextColor(Color.WHITE);
        data.setValueTextSize(9f);

        // set data
        mChart.setData(data);
    }

    private float getRandom(float range, float startsfrom) {
        return (float) (Math.random() * range) + startsfrom;
    }

    @Override public PhysicalMeasurementComponent getComponent() {
        return this.physicalMeasurementComponent;
    }
}
