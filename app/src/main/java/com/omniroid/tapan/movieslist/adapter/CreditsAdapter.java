package com.omniroid.tapan.movieslist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.omniroid.tapan.movieslist.R;
import com.omniroid.tapan.movieslist.model.Credits;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by DELL on 5/18/2017.
 */

public class CreditsAdapter extends RecyclerView.Adapter<CreditsAdapter.MyViewHolder> {

    private Context context;
    private List<Credits> creditses;


    public class MyViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView mTextName,mTextCharcterName;

        public MyViewHolder(View itemView) {
            super(itemView);

            circleImageView = (CircleImageView) itemView.findViewById(R.id.profile_image);
            mTextName = (TextView) itemView.findViewById(R.id.tv_name);
            mTextCharcterName = (TextView) itemView.findViewById(R.id.tv_charcterName);

        }
    }

    public CreditsAdapter(Context context, List<Credits> creditses) {
        this.context = context;
        this.creditses = creditses;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_credits, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Credits credits = creditses.get(position);

        final String imageUrl = "https://image.tmdb.org/t/p/w342"+credits.getProfile_path();

        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.user_placeholder)
                .error(R.drawable.user_placeholder_error)
                .resize(96, 96) // resizes the image to these dimensions (in pixel)
                .centerCrop()
                .into(holder.circleImageView);

        //Log.v("castingAdapter",credits.getProfile_path());
        holder.mTextName.setText(credits.getName());
        holder.mTextCharcterName.setText(credits.getCharcterName());


    }

    @Override
    public int getItemCount() {
        return creditses.size();
    }



}
