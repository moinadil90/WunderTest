package moin.wunder.main;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import moin.wunder.R;
import moin.wunder.adapters.PlacemarksAdapter;
import moin.wunder.models.ApiResponse;
import moin.wunder.models.Placemarks;

public class MainActivity extends AppCompatActivity implements MainViewInterface,
    OnMapReadyCallback {

    @BindView(R.id.placemarks) RecyclerView placemarks;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.switch_button) LinearLayout switchButton;
    @BindView(R.id.car_name_layout) LinearLayout carNameLayout;
    @BindView(R.id.car_name) TextView carNameText;
    @BindView(R.id.view_selector_list) TextView listSelector;
    @BindView(R.id.view_selector_map) TextView mapSelector;
    @BindView(R.id.mapLayout) FrameLayout map;
    @BindDrawable(R.drawable.ic_list) Drawable listImage;
    @BindDrawable(R.drawable.ic_location) Drawable mapImage;

    private String TAG = "MainActivity";
    private GoogleMap mMap;
    private List<Placemarks> coordinatesList;
    private RecyclerView.Adapter adapter;
    private MainPresenter mainPresenter;
    private LatLng latLng;
    private boolean isMarkerClicked;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setupMVP();
        setupViews();
        getMovieList();
    }



    private void setupMVP() {
        mainPresenter = new MainPresenter(this);
    }

    private void setupViews(){
        setSupportActionBar(toolbar);
        placemarks.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getMovieList() {
     mainPresenter.getCarData();
    }



    @Override
    public void showToast(String str) {
        Toast.makeText(MainActivity.this,str,Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void displayCarListView(ApiResponse apiResponse) {
        if(apiResponse!=null) {
            adapter = new PlacemarksAdapter(apiResponse.getPlacemarks(), MainActivity.this);
            placemarks.setAdapter(adapter);
            coordinatesList = Arrays.asList(apiResponse.getPlacemarks());
            if(coordinatesList != null && coordinatesList.size() > 0){
                plotMarkeronMap(coordinatesList);
            }
        }else{
            Log.d(TAG,"Response null");
        }
    }

    private void plotMarkeronMap(List<Placemarks> coordinatesList) {
        for (int i=0;i<coordinatesList.size(); i++){
            Double lat = Double.parseDouble(coordinatesList.get(i).getCoordinates()[0]);
            Double lng = Double.parseDouble(coordinatesList.get(i).getCoordinates()[1]);
            latLng = new LatLng(lat,lng);
            mMap.addMarker(new MarkerOptions().position(latLng)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng) , 14.0f) );
        }
    }

    @Override
    public void displayError(String e) {

        showToast(e);

    }

    @OnClick(R.id.switch_button)
    public void onListTypeClick() {
        map.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.switch_button_map)
    public void onMapTypeClick(){
        map.setVisibility(View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (!isMarkerClicked) {
                    isMarkerClicked = true;
                    mMap.clear();
                    Double lat = marker.getPosition().latitude;
                    Double lng = marker.getPosition().longitude;
                    LatLng latLng = new LatLng(lat, lng);
                    mMap.addMarker(new MarkerOptions().position(latLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14.0f));

                    for(int i=0; i<coordinatesList.size(); i++) {
                        if(marker.getPosition().latitude == Double.parseDouble(coordinatesList.get(i).getCoordinates()[0]) &&
                        marker.getPosition().longitude == Double.parseDouble(coordinatesList.get(i).getCoordinates()[1])){
                            carNameLayout.setVisibility(View.VISIBLE);
                            carNameText.setText(coordinatesList.get(i).getName());
                            break;
                        }
                    }
                    return true;
                } else {
                    isMarkerClicked = false;
                    carNameLayout.setVisibility(View.GONE);
                    for (int i = 0; i < coordinatesList.size(); i++) {
                        Double lat = Double.parseDouble(coordinatesList.get(i).getCoordinates()[0]);
                        Double lng = Double.parseDouble(coordinatesList.get(i).getCoordinates()[1]);
                        latLng = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.car)));
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 14.0f));
                    }
                    return true;
                }
            }
        });
    }
}
