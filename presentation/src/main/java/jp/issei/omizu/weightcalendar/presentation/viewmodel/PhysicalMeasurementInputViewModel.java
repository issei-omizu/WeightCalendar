package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.databinding.ObservableInt;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.Observable;
import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementDetails;
import jp.issei.omizu.weightcalendar.presentation.mapper.PhysicalMeasurementModelDataMapper;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;

public class PhysicalMeasurementInputViewModel {

    public final ObservableInt year = new ObservableInt();
    public final ObservableInt month = new ObservableInt();
    public final ObservableInt day = new ObservableInt();

    private GetPhysicalMeasurementDetails getPhysicalMeasurementDetails;
    private Observable<PhysicalMeasurementModel> physicalMeasurement;
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
        this.physicalMeasurement = null;
    }

    public Observable<PhysicalMeasurementModel> getPhysicalMeasurement() {
        return this.physicalMeasurement;
    }

    public void setPhysicalMeasurement(PhysicalMeasurement physicalMeasurement) {
        final PhysicalMeasurementModel physicalMeasurementModel =
                this.physicalMeasurementModelDataMapper.transform(physicalMeasurement);
//        this.physicalMeasurement = physicalMeasurementModel;
    }

    public void dateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year.set(year);
        this.month.set(monthOfYear);
        this.day.set(dayOfMonth);

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, monthOfYear, dayOfMonth);
        Date date = calendar.getTime();

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