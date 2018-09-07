package moin.wunder.main;

import moin.wunder.models.ApiResponse;

public interface MainViewInterface {

    void showToast(String s);
    void showProgressBar();
    void hideProgressBar();
    void displayCarListView(ApiResponse apiResponse);
    void displayError(String s);
}
