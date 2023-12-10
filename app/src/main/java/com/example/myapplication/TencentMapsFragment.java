package com.example.myapplication;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.myapplication.data.DataDownload;
import com.example.myapplication.data.ShopLocation;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

import java.util.ArrayList;

public class TencentMapsFragment extends Fragment {

    private MapView tMapView = null;
    private TencentMap tencentMap = null;

    public TencentMapsFragment() {
        // Required empty public constructor
    }
    /*public static TencentMapsFragment newInstance() {
        TencentMapsFragment fragment = new TencentMapsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }*/
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    public class DataDownloadTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return new DataDownload().download(urls[0]);
        }
        @Override
        protected void onPostExecute(String responseData) {
            super.onPostExecute(responseData);
            if (responseData != null) {
                ArrayList<ShopLocation> shopLocations= new DataDownload().parseJsonObjects(responseData);
                TencentMap tencentMap = tMapView.getMap();
                for (ShopLocation shopLocation : shopLocations) {
                    LatLng point1 = new LatLng(shopLocation.getLatitude(), shopLocation.getLongitude());
                    MarkerOptions markerOptions = new MarkerOptions(point1)
                            .title(shopLocation.getName());
                    Marker marker = tencentMap.addMarker(markerOptions);
                }
            }
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_baidu_maps, container, false);
        tMapView = rootView.findViewById(R.id.tmapView);
        tencentMap = tMapView.getMap();

        new DataDownloadTask().execute("http://file.nidama.net/class/mobile_develop/data/bookstore2023.json");
        TencentMap tencentMap = tMapView.getMap();

        LatLng point1 = new LatLng(22.255453, 113.54145);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        return rootView;
    }

    public void onResume(){
        super.onResume();
        tMapView.onResume();
    }

    public void onPause(){
        super.onPause();
        tMapView.onPause();
    }

    public void onDestroy(){
        super.onDestroy();
        tMapView.onPause();
    }
}