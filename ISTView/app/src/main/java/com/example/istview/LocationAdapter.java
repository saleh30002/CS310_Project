package com.example.istview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class LocationAdapter extends RecyclerView.Adapter <LocationAdapter.LocationViewHolder> {

    Context context;
    List<Locations> data;

    public LocationAdapter(Context context, List<Locations> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {

        View root =
                LayoutInflater.from(context).inflate(R.layout.location_row_layout, parent, false);

        LocationViewHolder holder = new LocationViewHolder(root);

        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder (@NonNull LocationViewHolder holder, int position){
        holder.loc_name.setText(data.get(position).getName());

        ISTViewApplication app = (ISTViewApplication)((Activity)context).getApplication();

        holder.downloadImage(app.srv,data.get(position).getImage());



        holder.row.setOnClickListener(v->{

            Intent i = new Intent(context,ActivityDetails.class);
            i.putExtra("location",data.get(position));
            i.putExtra("avgRating",holder.loc_rating.getText());
            ((Activity)context).startActivity(i);


        });


        Handler handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                List<Comments> comments = (List<Comments>) msg.obj;

                double sum = 0;

                for (Comments com: comments
                     ) {
                    sum = sum + com.getRating();
                }

                holder.loc_rating.setText(Double.toString(sum/comments.size()));

                return true;
            }
        });
        CommentsRepository avgRepo = new CommentsRepository();
        avgRepo.commentByLocation(app.srv, handler, data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class LocationViewHolder extends RecyclerView.ViewHolder{


        ConstraintLayout row;
        ImageView loc_image;
        TextView loc_name;
        TextView loc_rating;
        boolean imageDownloaded = false;

        Handler imageHandler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(@NonNull Message msg) {

                loc_image.setImageBitmap((Bitmap) msg.obj);
                imageDownloaded = true;

                return true;
            }
        });



        public LocationViewHolder(@NonNull View itemView) {
            super(itemView);

            row = itemView.findViewById(R.id.row_list);
            loc_image = itemView.findViewById(R.id.imgList);
            loc_name = itemView.findViewById(R.id.txtListName);
            loc_rating = itemView.findViewById(R.id.textViewMainRating);



        }

        public void downloadImage(ExecutorService srv, String path){

            if(imageDownloaded==false){
                LocationsRepository repo = new LocationsRepository();
                repo.downloadImage(srv,imageHandler,path);
            }




        }

    }
}
