package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.widget.DatePicker;

import org.apache.commons.lang.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.BR;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementDetails;
import jp.issei.omizu.weightcalendar.domain.interactor.SetPhysicalMeasurementDetails;
import jp.issei.omizu.weightcalendar.presentation.mapper.PhysicalMeasurementModelDataMapper;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;
import lombok.Getter;

public class PhysicalMeasurementInputViewModel extends BaseObservable {

    public final ObservableInt year = new ObservableInt();
    public final ObservableInt month = new ObservableInt();
    public final ObservableInt day = new ObservableInt();

//    public final ObservableField<String> weight = new ObservableField<>();
//    public final ObservableField<String> bodyFatPercentage = new ObservableField<>();
//    public final ObservableField<String> bodyTemperature = new ObservableField<>();
    private String weight;
    private String bodyFatPercentage;
    private String bodyTemperature;

    private GetPhysicalMeasurementDetails getPhysicalMeasurementDetails;
    private SetPhysicalMeasurementDetails setPhysicalMeasurementDetails;
    private final PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper;


    @Bindable
    public String getWeight() {
        return this.weight;
    }

    @Bindable
    public String getBodyFatPercentage() {
        return this.bodyFatPercentage;
    }

    @Bindable
    public String getBodyTemperature() {
        return this.bodyTemperature;
    }

    public void setWeight(String weight) {
        this.weight = weight;
        notifyPropertyChanged(jp.issei.omizu.weightcalendar.BR.weight);
    }

    public void setBodyFatPercentage(String weight) {
        this.bodyFatPercentage = weight;
        notifyPropertyChanged(BR.bodyFatPercentage);
    }

    public void setBodyTemperature(String weight) {
        this.bodyTemperature = weight;
        notifyPropertyChanged(jp.issei.omizu.weightcalendar.BR.bodyTemperature);
    }

    private void updatePhysicalMeasurement() {
        PhysicalMeasurement physicalMeasurement = new PhysicalMeasurement();

        Calendar calendar = Calendar.getInstance();
        calendar.set(this.year.get(), this.month.get(), this.day.get());
        Date date = DateUtils.truncate(calendar.getTime(), Calendar.DAY_OF_MONTH);

        physicalMeasurement.setDate(date);

        if (this.weight != null && !weight.isEmpty()) {
            physicalMeasurement.setWeight(Float.parseFloat(weight));
        }
        if (this.bodyFatPercentage != null && !this.bodyFatPercentage.isEmpty()) {
            physicalMeasurement.setBodyFatPercentage(Float.parseFloat(this.bodyFatPercentage));
        }

        SetPhysicalMeasurementDetails.Params params = SetPhysicalMeasurementDetails.Params.forPhysicalMeasurement(physicalMeasurement);
        this.setPhysicalMeasurementDetails.execute(new UpdatePhysicalMeasurementObserver(), params);
    }

    public void onWeightChanged(CharSequence s, int start, int before, int count) {
        this.weight = s.toString();
        this.updatePhysicalMeasurement();
    }

    public void onBodyFatPercentageChanged(CharSequence s, int start, int before, int count) {
        this.bodyFatPercentage = s.toString();
        this.updatePhysicalMeasurement();
    }

    public void onBodyTemperatureChanged(CharSequence s, int start, int before, int count) {
        this.bodyTemperature = s.toString();
        this.updatePhysicalMeasurement();
    }

    @Inject
    public PhysicalMeasurementInputViewModel(GetPhysicalMeasurementDetails getPhysicalMeasurementDetails,
                                             SetPhysicalMeasurementDetails setPhysicalMeasurementDetails,
                                             PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper) {
        this.getPhysicalMeasurementDetails = getPhysicalMeasurementDetails;
        this.setPhysicalMeasurementDetails = setPhysicalMeasurementDetails;
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

//            this.weight.set(physicalMeasurementModel.getWeight());

            if (physicalMeasurementModel.getWeight() != null) {
                this.setWeight(physicalMeasurementModel.getWeight().toString());
            }
            if (physicalMeasurementModel.getBodyFatPercentage() != null) {
                this.setBodyFatPercentage(physicalMeasurementModel.getBodyFatPercentage().toString());
            }
            if (physicalMeasurementModel.getBodyTemperature() != null) {
                this.setBodyTemperature(physicalMeasurementModel.getBodyTemperature().toString());
            }
        }
    }

    public void dateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//        this.weight.set("");
        this.setWeight("");
        this.setBodyFatPercentage("");
        this.setBodyTemperature("");

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

    private final class UpdatePhysicalMeasurementObserver extends DefaultObserver<PhysicalMeasurement> {

        @Override public void onComplete() {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onNext(PhysicalMeasurement physicalMeasurement) {
            String test = "";
//            PhysicalMeasurementInputViewModel.this.setPhysicalMeasurement(physicalMeasurement);
        }
    }
}