package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.widget.DatePicker;

import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementDetails;
import jp.issei.omizu.weightcalendar.presentation.mapper.PhysicalMeasurementModelDataMapper;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;

public class PhysicalMeasurementInputViewModel {

    public final ObservableInt year = new ObservableInt();
    public final ObservableInt month = new ObservableInt();
    public final ObservableInt day = new ObservableInt();

    public final ObservableField<String> weight = new ObservableField<>();
    public final ObservableField<String> bodyFatPercentage = new ObservableField<>();
    public final ObservableField<String> bodyTemperature = new ObservableField<>();

    private GetPhysicalMeasurementDetails getPhysicalMeasurementDetails;
    private final PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper;

    @Inject
    public PhysicalMeasurementInputViewModel(GetPhysicalMeasurementDetails getPhysicalMeasurementDetails,
                                             PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper) {
        this.getPhysicalMeasurementDetails = getPhysicalMeasurementDetails;
        this.physicalMeasurementModelDataMapper = physicalMeasurementModelDataMapper;

        Calendar calendar = Calendar.getInstance();
        year.set(calendar.get(Calendar.YEAR));
        month.set(calendar.get(Calendar.MONTH));
        day.set(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void initialize() {
    }

    public void setPhysicalMeasurement(PhysicalMeasurement physicalMeasurement) {
        if (physicalMeasurement != null) {
            final PhysicalMeasurementModel physicalMeasurementModel =
                    this.physicalMeasurementModelDataMapper.transform(physicalMeasurement);

            this.weight.set(physicalMeasurementModel.getWeight());
            this.bodyFatPercentage.set(physicalMeasurementModel.getBodyFatPercentage());
            this.bodyTemperature.set(physicalMeasurementModel.getBodyTemperature());
        }
    }

    public void dateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.weight.set("");
        this.bodyFatPercentage.set("");
        this.bodyTemperature.set("");

        this.year.set(year);
        this.month.set(monthOfYear);
        this.day.set(dayOfMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);


        GetPhysicalMeasurementDetails.Params params = GetPhysicalMeasurementDetails.Params.forDate(date);
        this.getPhysicalMeasurementDetails.execute(new PhysicalMeasurementObserver(), params);
    }

    private final class PhysicalMeasurementObserver extends DefaultObserver<PhysicalMeasurement> {

        @Override public void onComplete() {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onNext(PhysicalMeasurement physicalMeasurement) {
            PhysicalMeasurementInputViewModel.this.setPhysicalMeasurement(physicalMeasurement);
        }
    }
}