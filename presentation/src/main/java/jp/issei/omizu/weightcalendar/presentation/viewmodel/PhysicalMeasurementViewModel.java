package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.databinding.ObservableArrayList;

import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.ExportPhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementList;
import jp.issei.omizu.weightcalendar.domain.interactor.ImportPhysicalMeasurement;
import jp.issei.omizu.weightcalendar.presentation.mapper.PhysicalMeasurementModelDataMapper;
import jp.issei.omizu.weightcalendar.presentation.model.PhysicalMeasurementModel;

public class PhysicalMeasurementViewModel {

    private GetPhysicalMeasurementList getPhysicalMeasurementListUseCase;
    private ImportPhysicalMeasurement importPhysicalMeasurementUseCase;
    private ExportPhysicalMeasurement exportPhysicalMeasurementUseCase;
    private ObservableArrayList<PhysicalMeasurementModel> physicalMeasurements;
    private final PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper;

    public enum ExecApi {
        Import,
        Export
    }

    @Inject
    public PhysicalMeasurementViewModel(GetPhysicalMeasurementList getPhysicalMeasurementListUseCase,
                                        ImportPhysicalMeasurement importPhysicalMeasurementUseCase,
                                        ExportPhysicalMeasurement exportPhysicalMeasurementUseCase,
                                        PhysicalMeasurementModelDataMapper physicalMeasurementModelDataMapper) {
        this.getPhysicalMeasurementListUseCase = getPhysicalMeasurementListUseCase;
        this.importPhysicalMeasurementUseCase = importPhysicalMeasurementUseCase;
        this.exportPhysicalMeasurementUseCase = exportPhysicalMeasurementUseCase;
        this.physicalMeasurementModelDataMapper = physicalMeasurementModelDataMapper;
    }

    public void initialize() {
        this.physicalMeasurements = new ObservableArrayList<>();
    }

    public void loadPhysicalMeasurementList() {
        this.getPhysicalMeasurementListUseCase.execute(new PhysicalMeasurementListObserver(), null);
    }

    private void importPhysicalMeasurement(GoogleAccountCredential googleAccountCredential) {
        ImportPhysicalMeasurement.Params params = ImportPhysicalMeasurement.Params.forCredential(googleAccountCredential);
        this.importPhysicalMeasurementUseCase.execute(new PhysicalMeasurementListObserver(), params);
    }

    private void exportPhysicalMeasurement(GoogleAccountCredential googleAccountCredential) {
        ExportPhysicalMeasurement.Params params = ExportPhysicalMeasurement.Params.forCredential(googleAccountCredential);
        this.exportPhysicalMeasurementUseCase.execute(new PhysicalMeasurementListObserver(), params);
    }

    public void executeGoogleApi(GoogleAccountCredential googleAccountCredential, ExecApi execApi) {
        switch (execApi)
        {
            case Import:
                this.importPhysicalMeasurement(googleAccountCredential);
                break;
            case Export:
                this.exportPhysicalMeasurement(googleAccountCredential);
                break;
        }
    }

    public ObservableArrayList<PhysicalMeasurementModel> getPhysicalMeasurements() {
        return this.physicalMeasurements;
    }

    public void setPhysicalMeasurements(List<PhysicalMeasurement> physicalMeasurements) {
        final Collection<PhysicalMeasurementModel> physicalMeasurementModels =
                this.physicalMeasurementModelDataMapper.transform(physicalMeasurements);

        // clear
        this.physicalMeasurements.clear();

        // add all
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