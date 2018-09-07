package moin.wunder.network;

import io.reactivex.Observable;
import moin.wunder.models.ApiResponse;
import retrofit2.http.GET;

public interface NetworkInterface {

    @GET("/wunderbucket/locations.json")
    Observable<ApiResponse> getCarData();
}
