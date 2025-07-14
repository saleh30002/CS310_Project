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
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class CommentsRepository {

    public void commentByLocation (ExecutorService srv, Handler uiHandler, Locations location){

        srv.submit(() -> {
            try {
                List<Comments> data = new ArrayList<>();

                URL url =
                        new URL("http://10.0.2.2:8080/tourism/comments/reviews");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject outputData = new JSONObject();

                outputData.put("id", location.getId());
                /*outputData.put("name", location.getName());
                outputData.put("category", location.getCategory());
                outputData.put("fee", location.getFee());
                outputData.put("address", location.getAddress());
                outputData.put("description", location.getDescription());
                outputData.put("image", location.getImage());*/


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

                    Locations loc = new Locations(current.getJSONObject("locations").getString("id"),
                            current.getJSONObject("locations").getString("name"),
                            current.getJSONObject("locations").getString("category"),
                            current.getJSONObject("locations").getInt("fee"),
                            current.getJSONObject("locations").getString("address"),
                            current.getJSONObject("locations").getString("description"),
                            current.getJSONObject("locations").getString("image")
                            );

                    Comments comment = new Comments(current.getString("id"),
                            current.getString("userName"),
                            current.getString("comment"),
                            current.getDouble("rating"),
                            loc
                    );

                    data.add(comment);
                }

                Message msg = new Message();
                msg.obj = data;
                uiHandler.sendMessage(msg);

                conn.disconnect();

            } catch (MalformedURLException e){
                Log.e("DEV", e.getMessage());
            } catch (IOException e){
                Log.e("DEV", e.getMessage());
            } catch (JSONException e){
                Log.e("DEV", e.getMessage());
            }
        });
    }

    public void postComment (ExecutorService srv, Handler uiHandler, String user_name,
                             String user_comment, Double user_rating, Locations comment_loc){

        srv.execute(() -> {

            try {

                URL url =
                        new URL("http://10.0.2.2:8080/tourism/comments/addReview");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/JSON");

                JSONObject outputData = new JSONObject();

                outputData.put("userName", user_name);
                outputData.put("comment", user_comment);
                outputData.put("rating", user_rating);
                outputData.put("locationId", comment_loc.getId());

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());
                Log.i("DEV", outputData.toString());
                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();


                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder buffer = new StringBuilder();
                String line = "";

                while((line=reader.readLine())!=null){
                    buffer.append(line);
                }

                uiHandler.sendEmptyMessage(0);




            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (JSONException e) {
                throw new RuntimeException(e);
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
