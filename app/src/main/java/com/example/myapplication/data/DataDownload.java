package com.example.myapplication.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class DataDownload{

    public String download(String url_) {
        try {
            URL url = new URL(url_);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);
            connection.connect();

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                String responseData = stringBuilder.toString();
                reader.close();
                inputStream.close();
                connection.disconnect();
                Log.v("download",responseData);
                return responseData;
            } else {}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";

    }

    public ArrayList<ShopLocation> parseJsonObjects(String content) {
        ArrayList<ShopLocation> locations=new ArrayList<>();
        try {
            //这里的text就是上边获取到的数据，一个String.
            JSONObject jsonObject = new JSONObject(content);
            JSONArray jsonDatas = jsonObject.getJSONArray("shops");
            int length = jsonDatas.length();
            String test;
            for (int i = 0; i < length; i++) {
                JSONObject shopJson = jsonDatas.getJSONObject(i);
                ShopLocation shop = new ShopLocation();
                shop.setName(shopJson.getString("name"));
                shop.setLatitude(shopJson.getDouble("latitude"));
                shop.setLongitude(shopJson.getDouble("longitude"));
                shop.setMemo(shopJson.getString("memo"));
                locations.add(shop);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  locations;
    }
}
