package moin.wunder.main;

import android.util.Log;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import moin.wunder.models.ApiResponse;
import moin.wunder.network.NetworkClient;
import moin.wunder.network.NetworkInterface;

public class MainPresenter implements MainPresenterInterface {

    MainViewInterface mvi;
    private String TAG = "MainPresenter";

    public MainPresenter(MainViewInterface mvi) {
        this.mvi = mvi;
    }

    @Override
    public void getCarData() {
        getObservable().subscribeWith(getObserver());
    }

    public Observable<ApiResponse> getObservable(){
        return NetworkClient.getRetrofit().create(NetworkInterface.class)
            .getCarData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread());
    }

    public DisposableObserver<ApiResponse> getObserver(){
        return new DisposableObserver<ApiResponse>() {

            @Override
            public void onNext(@NonNull ApiResponse apiResponse) {
                Log.d(TAG,"OnNext"+apiResponse.getPlacemarks());
                mvi.displayCarListView(apiResponse);

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error"+e);
                e.printStackTrace();
                mvi.displayError("Error fetching Data");
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"Completed");
                mvi.hideProgressBar();
            }
        };
    }
}
