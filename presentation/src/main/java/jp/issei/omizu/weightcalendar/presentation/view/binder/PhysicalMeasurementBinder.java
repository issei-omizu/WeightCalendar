package jp.issei.omizu.weightcalendar.presentation.view.binder;

import android.databinding.BindingAdapter;
import android.databinding.ObservableArrayList;
import android.widget.ListView;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;
import jp.issei.omizu.weightcalendar.presentation.view.adapter.PhysicalMeasurementAdapter;

/**
 * Created by isseiomizu on 2017/05/21.
 */

public class PhysicalMeasurementBinder {

    private static PhysicalMeasurementAdapter physicalMeasurementAdapter;

    @BindingAdapter("list")
    public static void setList(ListView listView, ObservableArrayList<PhysicalMeasurementModel> physicalMeasurements) {
        if (listView.getAdapter() == null) {
            physicalMeasurementAdapter = new PhysicalMeasurementAdapter(listView.getContext(), physicalMeasurements);
            listView.setAdapter(physicalMeasurementAdapter);
        }
        physicalMeasurementAdapter.notifyDataSetChanged();
    }
}
