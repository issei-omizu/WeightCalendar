package jp.issei.omizu.weightcalendar.presentation.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import jp.issei.omizu.weightcalendar.R;
import jp.issei.omizu.weightcalendar.databinding.ViewRowPhysicalMeasurementBinding;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;

/**
 * Created by isseiomizu on 2017/05/21.
 */

public class PhysicalMeasurementAdapter extends ArrayAdapter<PhysicalMeasurementModel> {
    public PhysicalMeasurementAdapter(Context context, ObservableArrayList<PhysicalMeasurementModel> physicalMeasurements) {
        super(context, 0, physicalMeasurements);
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        ViewRowPhysicalMeasurementBinding binding;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            binding = DataBindingUtil.inflate(inflater, R.layout.view_row_physical_measurement, parent, false);

            convertView = binding.getRoot();
            convertView.setTag(binding);
        } else {
            binding = (ViewRowPhysicalMeasurementBinding) convertView.getTag();
        }

        binding.setPhysicalMeasurement(getItem(position));

        return binding.getRoot();
    }
}
