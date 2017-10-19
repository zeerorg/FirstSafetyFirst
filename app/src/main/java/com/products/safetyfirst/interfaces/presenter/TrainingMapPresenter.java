package com.products.safetyfirst.interfaces.presenter;

import com.products.safetyfirst.models.TrainingCenterModel;

import java.util.ArrayList;

/**
 * Created by vikas on 19/10/17.
 */

public interface TrainingMapPresenter {

    void onDestroy();

    void request();

    void getTrainingCenters(ArrayList<TrainingCenterModel> trainingCenterModels);

    void onRequestError(String message);

    void onRequestSuccess(String message);
}
