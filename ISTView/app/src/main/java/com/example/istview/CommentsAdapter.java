package com.example.istview;

import android.app.Activity;
import android.content.Context;
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

import org.w3c.dom.Text;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentsAdapter extends RecyclerView.Adapter <CommentsAdapter.CommentsViewHolder>{

    Context context;
    List<Comments> data;

    public CommentsAdapter(Context context, List<Comments> data){
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public CommentsAdapter.CommentsViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int viewType) {

        View root =
                LayoutInflater.from(context).inflate(R.layout.comment_row_layout, parent, false);

        CommentsAdapter.CommentsViewHolder holder = new CommentsAdapter.CommentsViewHolder(root);

        holder.setIsRecyclable(false);
        return holder;
    }

    @Override
    public void onBindViewHolder (@NonNull CommentsAdapter.CommentsViewHolder holder, int position){

        holder.user_name.setText(data.get(position).getUserName());
        holder.user_comment.setText(data.get(position).getComment());
        holder.userRate.setText(Double.toString(data.get(position).getRating()));

        ISTViewApplication app = (ISTViewApplication)((Activity)context).getApplication();

        /*holder.row.setOnClickListener(v->{

            Intent i = new Intent(context,ActivityDetails.class);
            i.putExtra("id",data.get(position).getId());

            ((Activity)context).startActivity(i);


        });*/
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class CommentsViewHolder extends RecyclerView.ViewHolder{


        ConstraintLayout row;
        TextView user_name;
        TextView user_comment;
        TextView userRate;


        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);

            row = itemView.findViewById(R.id.row_list_comment);
            user_name = itemView.findViewById(R.id.user_name_place);
            user_comment = itemView.findViewById(R.id.user_comment);
            userRate = itemView.findViewById(R.id.textViewCommentRating);

        }

    }
}
