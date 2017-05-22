package jp.issei.omizu.weightcalendar.presentation.viewmodel;

import android.databinding.ObservableArrayList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import jp.issei.omizu.weightcalendar.domain.PhysicalMeasurement;
import jp.issei.omizu.weightcalendar.domain.interactor.DefaultObserver;
import jp.issei.omizu.weightcalendar.domain.interactor.GetPhysicalMeasurementList;

public class PhysicalMeasurementViewModel extends GoogleApiViewModel {

    private GetPhysicalMeasurementList getPhysicalMeasurementListUseCase;
    private ObservableArrayList<PhysicalMeasurement> physicalMeasurements;

    @Inject
    public PhysicalMeasurementViewModel(GetPhysicalMeasurementList getPhysicalMeasurementListUseCase) {
        this.getPhysicalMeasurementListUseCase = getPhysicalMeasurementListUseCase;
    }

    public void initialize() {
        this.physicalMeasurements = new ObservableArrayList<>();
    }

    private void loadPhysicalMeasurementList() {
        GetPhysicalMeasurementList.Params params = GetPhysicalMeasurementList.Params.forCredential(this.getCredential());
        this.getPhysicalMeasurementListUseCase.execute(new PhysicalMeasurementListObserver(), params);
    }

    @Override
    public void executeGoogleApi() {
        super.executeGoogleApi();
        this.loadPhysicalMeasurementList();
    }

    public ObservableArrayList<PhysicalMeasurement> getPhysicalMeasurements() {
        return this.physicalMeasurements;
    }

    public void setPhysicalMeasurements(List<PhysicalMeasurement> physicalMeasurements) {
//        this.physicalMeasurements.clear();
        this.physicalMeasurements.addAll(physicalMeasurements);
    }

    private final class PhysicalMeasurementListObserver extends DefaultObserver<List<PhysicalMeasurement>> {

        @Override public void onComplete() {
            String test = "";
//      UserListPresenter.this.hideViewLoading();
        }

        @Override public void onError(Throwable e) {
//      UserListPresenter.this.hideViewLoading();
//      UserListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception) e));
//      UserListPresenter.this.showViewRetry();
        }

        @Override public void onNext(List<PhysicalMeasurement> physicalMeasurements) {
            PhysicalMeasurementViewModel.this.setPhysicalMeasurements(physicalMeasurements);
        }
    }
}