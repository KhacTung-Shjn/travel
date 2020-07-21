package com.example.mytravel.ui.detailexplore.map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Location;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mytravel.R;
import com.example.mytravel.base.BaseFragment;
import com.example.mytravel.models.city.PlaceHot;
import com.example.mytravel.utils.CenterZoomLayoutManager;
import com.example.mytravel.utils.OnClickItem;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mytravel.utils.ConstApp.KEY_HOT_PLACE;
import static com.example.mytravel.utils.ConstApp.KEY_HOT_PLACE_LAT;
import static com.example.mytravel.utils.ConstApp.KEY_HOT_PLACE_LNG;
import static com.example.mytravel.utils.ConstApp.KEY_LIST_HOT_PLACE;
import static com.example.mytravel.utils.ConstApp.REQUEST_PERMISSION_CODE;

public class MapFragment extends BaseFragment implements SampleFrMvpView, OnMapReadyCallback, GoogleMap.InfoWindowAdapter, GoogleMap.OnMarkerClickListener, OnClickItem<PlaceHot> {
    public static final String TAG = MapFragment.class.getSimpleName();

    private MapFrMvpPresenter presenter;

    @BindView(R.id.rcvListHotMarker)
    RecyclerView rcvListHotMarker;

    private String lat, lng;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private Location location;
    private GoogleMap mMap;
    private LatLng mCurrentLocation;
    private LatLng placeHotLocation;
    private PlaceHot placeHot;
    private ArrayList<PlaceHot> listPlaceHots;
    private HotMarkerAdapter hotMarkerAdapter;

    public static MapFragment newInstance(String lat, String lng, PlaceHot placeHot) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_HOT_PLACE_LAT, lat);
        bundle.putString(KEY_HOT_PLACE_LNG, lng);
        bundle.putParcelable(KEY_HOT_PLACE, placeHot);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    public static MapFragment newInstanceFromExplore(ArrayList<PlaceHot> listPlaceHots) {
        MapFragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(KEY_LIST_HOT_PLACE, listPlaceHots);
        mapFragment.setArguments(bundle);
        return mapFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MapFrPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            setUnbinder(ButterKnife.bind(this, getActivity()));

            Window w = getActivity().getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            setUnbinder(ButterKnife.bind(this, getActivity()));

            RelativeLayout rlActionBar = getActivity().findViewById(R.id.rlActionBar);
            View viewActionBar = getActivity().findViewById(R.id.viewActionBar);
            if (rlActionBar != null) {
                rlActionBar.setVisibility(View.GONE);
                viewActionBar.setVisibility(View.GONE);
            }

            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        }
        if (getArguments() != null) {
            lat = getArguments().getString(KEY_HOT_PLACE_LAT);
            lng = getArguments().getString(KEY_HOT_PLACE_LNG);
            placeHot = getArguments().getParcelable(KEY_HOT_PLACE);
            listPlaceHots = getArguments().getParcelableArrayList(KEY_LIST_HOT_PLACE);
        }

        if (getContext() != null && getActivity() != null) {
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_PERMISSION_CODE);
            } else {
                getLastLocation();
            }
        }
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(it -> {
            location = it;
            if (getFragmentManager() != null) {
                SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.maps);
                if (mapFragment != null) {
                    mapFragment.getMapAsync(this);
                }
            }
        }).addOnFailureListener(e -> Log.d(TAG, Objects.requireNonNull(e.getMessage())));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.mMap = googleMap;
        this.mMap.setInfoWindowAdapter(this); // detailMarker
        this.mMap.setOnMapLoadedCallback(() -> {
            if (location != null) {
                //my current location
                mCurrentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions()
                        .position(mCurrentLocation)
                        .icon(BitmapDescriptorFactory.fromBitmap(createMaker(Objects.requireNonNull(getContext()), R.drawable.ic_marker)))
                );
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mCurrentLocation, 15));

                if (listPlaceHots != null) {
                    //list place hot from explore
                    rcvListHotMarker.setVisibility(View.VISIBLE);
                    for (PlaceHot place : listPlaceHots) {
                        if (!TextUtils.isEmpty(place.getLat()) && !TextUtils.isEmpty(place.getLng())) {
                            LatLng latLng = new LatLng(Double.parseDouble(place.getLat()), Double.parseDouble(place.getLng()));
                            if (latLng != mCurrentLocation) {
                                mMap.addMarker(new MarkerOptions()
                                        .position(latLng)
                                        .icon(BitmapDescriptorFactory.fromBitmap(createMaker(Objects.requireNonNull(getContext()), R.drawable.ic_flag)))
                                        .title(place.getNamePlaceHot())
                                        .snippet(place.getTimeOpen())
                                );
                            }
                        }
                    }
                    hotMarkerAdapter = new HotMarkerAdapter(getContext());
                    hotMarkerAdapter.setListHotMarker(listPlaceHots);
                    hotMarkerAdapter.setOnClickItem(this);
                    CenterZoomLayoutManager centerZoomLayoutManager = new CenterZoomLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
                    rcvListHotMarker.setLayoutManager(centerZoomLayoutManager);
                    rcvListHotMarker.setHasFixedSize(true);
                    rcvListHotMarker.setAdapter(hotMarkerAdapter);
                } else {
                    //place hot
                    rcvListHotMarker.setVisibility(View.GONE);
                    if (placeHot != null && !TextUtils.isEmpty(lat) && !TextUtils.isEmpty(lng)) {
                        placeHotLocation = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                        if (placeHotLocation != mCurrentLocation) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(placeHotLocation)
                                    .icon(BitmapDescriptorFactory.fromBitmap(createMaker(Objects.requireNonNull(getContext()), R.drawable.ic_flag)))
                                    .title(placeHot.getNamePlaceHot())
                                    .snippet(placeHot.getTimeOpen())
                            );
                        }
                    }
                }
            }
        });

        this.mMap.setOnMarkerClickListener(this);
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        //Detail Marker
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                Log.d("location", "Permission has been denied");
            }
        }
    }


    @OnClick(R.id.ivBackMap)
    public void onClickBackMap() {
        if (getActivity() != null && getFragmentManager() != null) {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStackImmediate();
            } else {
                getActivity().finish();
            }
        }
    }


    public static Bitmap createMaker(Context context, @DrawableRes int resource) {

        @SuppressLint("InflateParams") View marker = ((LayoutInflater) Objects.requireNonNull(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.item_icon_marker, null);

        ImageView markerImage = marker.findViewById(R.id.ivMarker);
        markerImage.setImageResource(resource);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (this.mMap != null && placeHotLocation != null) {
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(placeHotLocation);
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
            this.mMap.moveCamera(cameraUpdate);
            this.mMap.animateCamera(CameraUpdateFactory.zoomTo(17f), 2000, null);
        }
        return false;
    }

    @Override
    public void onClickItem(PlaceHot placeHot) {
        if (this.mMap != null && placeHot != null) {
            LatLng latLng = new LatLng(Double.parseDouble(placeHot.getLat()), Double.parseDouble(placeHot.getLng()));
            LatLngBounds.Builder builder = new LatLngBounds.Builder();
            builder.include(latLng);
            LatLngBounds bounds = builder.build();
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 200);
            this.mMap.moveCamera(cameraUpdate);
            this.mMap.animateCamera(CameraUpdateFactory.zoomTo(17f), 2000, null);
        }
    }

    public int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
