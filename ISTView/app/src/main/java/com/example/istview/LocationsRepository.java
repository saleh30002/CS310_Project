package com.example.istview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class LocationsRepository {


    public void getAllLocations(ExecutorService srv, Handler uiHandler){


        srv.submit(()->{
            try {

                List<Locations> data = new ArrayList<>();

                URL url =
                        new URL("http://10.0.2.2:8080/tourism/locations");


                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line ="";
                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());

                for (int i = 0; i <arr.length() ; i++) {

                    JSONObject current = arr.getJSONObject(i);

                    Locations loc = new Locations(current.getString("id"),
                            current.getString("name"),
                            current.getString("category"),
                            current.getInt("fee"),
                            current.getString("address"),
                            current.getString("description"),
                            current.getString("image"));

                    data.add(loc);
                }
                Log.i("DEV", "WE ARE HERE");
                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);



            } catch (MalformedURLException e) {
                Log.e("DEV",e.getMessage());
            } catch (IOException e) {
                Log.e("DEV",e.getMessage());
            } catch (JSONException e) {
                Log.e("DEV",e.getMessage());
            }


        });

    }

    public void locationByCategory (ExecutorService srv, Handler uiHandler, String category) {

        srv.submit(() -> {
            try {
                List<Locations> data = new ArrayList<>();

                URL url =
                        new URL("http://10.0.2.2:8080/tourism/locations/searchbycategory");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject outputData = new JSONObject();

                outputData.put("category", category);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());

                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(
                                new InputStreamReader(
                                        conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine()) !=null){
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());

                for (int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);

                    Locations loc = new Locations(current.getString("id"),
                            current.getString("name"),
                            current.getString("category"),
                            current.getInt("fee"),
                            current.getString("address"),
                            current.getString("description"),
                            current.getString("image"));

                    data.add(loc);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e){
                Log.e("DEV", e.getMessage());
            } catch (IOException e){
                Log.e("DEV", e.getMessage());
            } catch (JSONException e){
                Log.e("DEV", e.getMessage());
            }
        });
    }

    public void locationByName (ExecutorService srv, Handler uiHandler, String loc_name){

        srv.submit(() -> {
            try {
                List<Locations> data = new ArrayList<>();

                URL url =
                        new URL("http://10.0.2.2:8080/tourism/locations/search");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject outputData = new JSONObject();

                outputData.put("name", loc_name);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());

                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(
                        new InputStreamReader(
                                conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine()) !=null){
                    buffer.append(line);
                }

                JSONArray arr = new JSONArray(buffer.toString());

                for (int i = 0; i < arr.length(); i++){
                    JSONObject current = arr.getJSONObject(i);

                    Locations loc = new Locations(current.getString("id"),
                            current.getString("name"),
                            current.getString("category"),
                            current.getInt("fee"),
                            current.getString("address"),
                            current.getString("description"),
                            current.getString("image"));

                    data.add(loc);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e){
                Log.e("DEV", e.getMessage());
            } catch (IOException e){
                Log.e("DEV", e.getMessage());
            } catch (JSONException e){
                Log.e("DEV", e.getMessage());
            }
        });
    }

    public void downloadImage(ExecutorService srv, Handler uiHandler, String path) {

        srv.submit(() -> {
            try {
                URL url =
                        new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                Bitmap bmp = BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bmp;
                uiHandler.sendMessage(msg);

            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });

    }
}
