package com.cred.music.view.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cred.music.helper.PermissionStatus;

import javax.inject.Inject;

public class MainActivityViewModel extends ViewModel {


    private MutableLiveData<PermissionStatus> persmissionStatus;

    @Inject
    public MainActivityViewModel() {


    }

    public void setPersmissionStatus(PermissionStatus newPermissionStatus) {
        if (persmissionStatus == null)
            persmissionStatus = new MutableLiveData<>();

        if (persmissionStatus.getValue() != newPermissionStatus) {
            persmissionStatus.setValue(newPermissionStatus);
        }
    }


    public LiveData<PermissionStatus> getPermissionStatus() {
        return persmissionStatus;
    }
}
