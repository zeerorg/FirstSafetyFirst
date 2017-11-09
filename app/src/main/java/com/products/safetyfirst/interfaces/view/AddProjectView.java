package com.products.safetyfirst.interfaces.view;

/**
 * Created by vikas on 04/10/17.
 */

@SuppressWarnings({"ALL", "EmptyMethod"})
public interface AddProjectView {

    void showAddProjectDialog();

    void setCompanyError();

    void setUsernameError();

    void setDesignationError();

    void showProgress();

    void hideProgress();

    void navigateToHome();
}
