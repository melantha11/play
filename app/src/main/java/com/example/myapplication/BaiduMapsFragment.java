package com.example.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaiduMapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaiduMapsFragment extends Fragment {
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;

    public BaiduMapsFragment() {
        // Required empty public constructor
    }
    public static BaiduMapsFragment newInstance(String param1, String param2) {
        BaiduMapsFragment fragment = new BaiduMapsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_baidu_maps, container, false);
        mMapView = rootView.findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();


        LatLng point1 = new LatLng(22.255453,113.54145);

        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(point1);
        mBaiduMap.animateMapStatus(mapStatusUpdate);
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(15));

        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.book_1);
        MarkerOptions markerOptions = new MarkerOptions().position(point1).icon(bitmap);
        mBaiduMap.addOverlay(markerOptions);

        LatLng point2 = new LatLng(22.255453,113.54145);
        TextOptions textOptions = new TextOptions().position(point2).text("Hello Baidu Map!");
        mBaiduMap.addOverlay(textOptions);

        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(requireContext(),"text",Toast.LENGTH_LONG);
                return true;
            }
        });

        return rootView;
    }

    public void onResume(){
        super.onResume();
        mMapView.onResume();
    }

    public void onPause(){
        super.onPause();
        mMapView.onPause();
    }

    public void onDestroy(){
        super.onDestroy();
        mMapView.onPause();
    }
}