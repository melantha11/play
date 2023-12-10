package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.Marker;
import com.tencent.tencentmap.mapsdk.maps.model.MarkerOptions;

public class TencentMapsFragment extends Fragment {

    private MapView tMapView = null;
    private TencentMap tencentMap = null;

    public TencentMapsFragment() {
        // Required empty public constructor
    }
    public static TencentMapsFragment newInstance() {
        TencentMapsFragment fragment = new TencentMapsFragment();
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
        tMapView = rootView.findViewById(R.id.tmapView);
        tencentMap = tMapView.getMap();

        LatLng point1 = new LatLng(22.255453, 113.54145);
        tencentMap.moveCamera(CameraUpdateFactory.newLatLng(point1));

        // 创建一个Marker对象
        MarkerOptions markerOptions = new MarkerOptions(point1)
                .title("标记标题");

        // 添加标记到地图上
        Marker marker = tencentMap.addMarker(markerOptions);

        tencentMap.setOnMarkerClickListener(new TencentMap.OnMarkerClickListener() {
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