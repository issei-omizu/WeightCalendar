package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.databinding.ObservableArrayList;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementList;
import jp.issei.omizu.weightcalendar.domain.interactor.ImportPhysicalMeasurement;
import jp.issei.omizu.weightcalendar.presentation.mapper.PhysicalMeasurementModelDataMapper;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;
import jp.issei.omizu.weightcalendar.presentation.view.activity.PhysicalMeasurementChartActivity;

public class PhysicalMeasurementChartViewModel {

    private GetPhysicalMeasurementList getPhysicalMeasurementListUseCase;
    private List<PhysicalMeasurementModel> physicalMeasurements;
    private final PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper;

    private PhysicalMeasurementChartActivity physicalMeasurementChartActivity;

    @Inject
    public PhysicalMeasurementChartViewModel(GetPhysicalMeasurementList getPhysicalMeasurementListUseCase,
                                             ImportPhysicalMeasurement importPhysicalMeasurementUseCase,
                                             PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper) {
        this.getPhysicalMeasurementListUseCase = getPhysicalMeasurementListUseCase;
        this.physicalMeasurementModelDataMapper = physicalMeasurementModelDataMapper;
    }

    public void initialize() {
        this.physicalMeasurements = new ObservableArrayList<>();
    }

    public void loadPhysicalMeasurementList() {
        this.getPhysicalMeasurementListUseCase.execute(new PhysicalMeasurementListObserver(), null);
    }

    public void setView(PhysicalMeasurementChartActivity activity) {
        this.physicalMeasurementChartActivity = activity;
    }

    public void setPhysicalMeasurements(List<PhysicalMeasurement> physicalMeasurements) {
        final Collection<PhysicalMeasurementModel> physicalMeasurementModels =
                this.physicalMeasurementModelDataMapper.transform(physicalMeasurements);

        // clear
        this.physicalMeasurements.clear();

        // add all
        this.physicalMeasurements.addAll(physicalMeasurementModels);

        this.physicalMeasurementChartActivity.setChartData(this.physicalMeasurements);
    }

    private final class PhysicalMeasurementListObserver extends DefaultObserver<List<PhysicalMeasurement>> {

        @Override public void onComplete() {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onNext(List<PhysicalMeasurement> physicalMeasurements) {
            PhysicalMeasurementChartViewModel.this.setPhysicalMeasurements(physicalMeasurements);
        }
    }
}