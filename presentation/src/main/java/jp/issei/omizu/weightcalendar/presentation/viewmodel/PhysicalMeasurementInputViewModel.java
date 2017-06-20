package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.widget.DatePicker;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementList;
import jp.issei.omizu.weightcalendar.presentation.mapper.PhysicalMeasurementModelDataMapper;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;

public class PhysicalMeasurementInputViewModel {

    public final ObservableInt year = new ObservableInt();
    public final ObservableInt month = new ObservableInt();
    public final ObservableInt day = new ObservableInt();

    private GetPhysicalMeasurementList getPhysicalMeasurementListUseCase;
    private ObservableArrayList<PhysicalMeasurementModel> physicalMeasurements;
    private final PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper;

    @Inject
    public PhysicalMeasurementInputViewModel(GetPhysicalMeasurementList getPhysicalMeasurementListUseCase,
                                             PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper) {
        this.getPhysicalMeasurementListUseCase = getPhysicalMeasurementListUseCase;
        this.physicalMeasurementModelDataMapper = physicalMeasurementModelDataMapper;

        Calendar calendar = Calendar.getInstance();
        year.set(calendar.get(Calendar.YEAR));
        month.set(calendar.get(Calendar.MONTH));
        day.set(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public void initialize() {
        this.physicalMeasurements = new ObservableArrayList<>();
    }

    private void loadPhysicalMeasurementList(GoogleAccountCredential googleAccountCredential) {
        GetPhysicalMeasurementList.Params params = GetPhysicalMeasurementList.Params.forCredential(googleAccountCredential);
        this.getPhysicalMeasurementListUseCase.execute(new PhysicalMeasurementListObserver(), params);
    }

    public void executeGoogleApi(GoogleAccountCredential googleAccountCredential) {
        this.loadPhysicalMeasurementList(googleAccountCredential);
    }

    public ObservableArrayList<PhysicalMeasurementModel> getPhysicalMeasurements() {
        return this.physicalMeasurements;
    }

    public void setPhysicalMeasurements(List<PhysicalMeasurement> physicalMeasurements) {
        final Collection<PhysicalMeasurementModel> physicalMeasurementModels =
                this.physicalMeasurementModelDataMapper.transform(physicalMeasurements);
        this.physicalMeasurements.addAll(physicalMeasurementModels);
    }

    public void dateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        this.year.set(year);
        this.month.set(monthOfYear);
        this.day.set(dayOfMonth);
    }

    private final class PhysicalMeasurementListObserver extends DefaultObserver<List<PhysicalMeasurement>> {

        @Override public void onComplete() {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onNext(List<PhysicalMeasurement> physicalMeasurements) {
            PhysicalMeasurementInputViewModel.this.setPhysicalMeasurements(physicalMeasurements);
        }
    }
}