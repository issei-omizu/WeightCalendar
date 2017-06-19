package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.databinding.ObservableArrayList;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementList;
import jp.issei.omizu.weightcalendar.presentation.mapper.PhysicalMeasurementModelDataMapper;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;

public class PhysicalMeasurementViewModel {

    private GetPhysicalMeasurementList getPhysicalMeasurementListUseCase;
    private ObservableArrayList<PhysicalMeasurementModel> physicalMeasurements;
    private final PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper;

    @Inject
    public PhysicalMeasurementViewModel(GetPhysicalMeasurementList getPhysicalMeasurementListUseCase,
                                        PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper) {
        this.getPhysicalMeasurementListUseCase = getPhysicalMeasurementListUseCase;
        this.physicalMeasurementModelDataMapper = physicalMeasurementModelDataMapper;
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

    private final class PhysicalMeasurementListObserver extends DefaultObserver<List<PhysicalMeasurement>> {

        @Override public void onComplete() {
        }

        @Override public void onError(Throwable e) {
        }

        @Override public void onNext(List<PhysicalMeasurement> physicalMeasurements) {
            PhysicalMeasurementViewModel.this.setPhysicalMeasurements(physicalMeasurements);
        }
    }
}