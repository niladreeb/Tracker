package com.demo.tracker.viewController;


import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.demo.tracker.MainActivity;
import com.demo.tracker.R;
import com.demo.tracker.common.Const;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.PolyUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class JourneyDetailsFragment extends Fragment implements OnMapReadyCallback {

    private double[] mCoordinate;
    private GoogleMap mGoogleMap;
    private String mRouteString;
    private String[] mLocality;
    private List<LatLng> list;
    private SupportMapFragment mSupportMapFragment;

    public static JourneyDetailsFragment newInstance() {
        JourneyDetailsFragment fragment = new JourneyDetailsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_journey_details, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.title_fragment_journey_detail);
        if (getArguments() != null) {
            mCoordinate = getArguments().getDoubleArray(Const.BundleKey.COORDINATE.toString());
            mRouteString = getArguments().getString(Const.BundleKey.ROUTE.toString());
            mLocality = getArguments().getStringArray(Const.BundleKey.LOCALITY.toString());
        }
        // Decoding encoded polyline string
        try {
            list = PolyUtil.decode(mRouteString);
        } catch (IndexOutOfBoundsException e) {    //For malformed encoded route
            e.printStackTrace();
        }
        mSupportMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mSupportMapFragment.getMapAsync(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getChildFragmentManager().beginTransaction().remove(mSupportMapFragment).commitAllowingStateLoss();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng startPos = new LatLng(mCoordinate[0], mCoordinate[1]);
        LatLng endPos = new LatLng(mCoordinate[2], mCoordinate[3]);

        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mGoogleMap.setMyLocationEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(startPos, 12));
        mGoogleMap.addMarker(new MarkerOptions().position(startPos).title(mLocality[0]));
        mGoogleMap.addMarker(new MarkerOptions().position(endPos).title(mLocality[1]));
        //NullPointerException handled
        if(list!=null)
            mGoogleMap.addPolyline(new PolylineOptions()).setPoints(list);
        else
            mGoogleMap.addPolyline(new PolylineOptions().add(startPos,endPos));
    }


}

