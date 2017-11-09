package com.products.safetyfirst.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;

/**
 * Created by ishita sharma on 11/4/2017.
 */

public class NewsViewHolder extends RecyclerView.ViewHolder{

    public final ImageView images;
    private final ImageView favicon;
    private final ImageView bookmark;
    public final TextView title;
    public final TextView timestamp;
    public final Button detail;

    public NewsViewHolder(View view) {

        super(view);
        images = view.findViewById(R.id.news_avtar);
        favicon = view.findViewById(R.id.favicon);
        bookmark = view.findViewById(R.id.bookmark);
        title = view.findViewById(R.id.title);
        timestamp = view.findViewById(R.id.dateTime);
        detail = view.findViewById(R.id.view_details);

    }
}
