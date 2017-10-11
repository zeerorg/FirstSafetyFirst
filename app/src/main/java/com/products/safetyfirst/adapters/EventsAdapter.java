package com.products.safetyfirst.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.products.safetyfirst.R;
import com.products.safetyfirst.impementations.EventsPresenterImpl;
import com.products.safetyfirst.interfaces.EventsAdapterView;
import com.products.safetyfirst.interfaces.EventsPresenter;
import com.products.safetyfirst.models.Event_model;
import java.util.ArrayList;

/**
 * Created by VIKAS on 11-Oct-17.
 */

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder> implements EventsAdapterView{


    private final ArrayList<Event_model> mEventsList = new ArrayList<>();
    private final EventsPresenter presenter;
    private Context context;
    private String mUserId;

    public EventsAdapter(Context context, String userId){
        this.presenter = new EventsPresenterImpl(this);
        this.context = context;
        this.mUserId = userId;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.anim_recycler_item_show);
        holder.mView.startAnimation(animation);

        Event_model event = mEventsList.get(position);

        if(event.getTitle() != null) holder.title.setText(event.getTitle());
        if(event.getTimestamp() != null) {
          //  SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
          //  sfd.format(new Date(timestamp))
            holder.dateTime.setText(DateUtils.getRelativeTimeSpanString(
                    (long) event.getTimestamp()).toString());
        }

        if(event.getActions() != null && event.getActions().containsKey(mUserId)){
            switch (String.valueOf(event.getActions().get(mUserId))){
                case "0": //not going
                    holder.going.setText("You are going");
                    break;

                case "1": //maybe
                    holder.maybe.setText("Maybe you will go");
                    break;
                case "2": //going
                    holder.notGoing.setText("You are not going");
                    break;
                default:
                    holder.going.setText("Going");
                    holder.maybe.setText("Maybe");
                    holder.notGoing.setText("Not Going");
                    break;
            }
        }


       // holder.bookmark.setImageDrawable();
    }

    @Override
    public int getItemCount() {
        return mEventsList.size();
    }

    @Override
    public void addAll(ArrayList<Event_model> events) {
        mEventsList.clear();
        mEventsList.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public void request() {
        presenter.request();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View mView;
        public ImageView images;
        public TextView title;
        public TextView dateTime;
        public ImageView bookmark;
        public TextView going;
        public TextView maybe;
        public TextView notGoing;


        public ViewHolder(View view) {

            super(view);
            mView = view;
            images = (ImageView) view.findViewById(R.id.event_avtar);
            title = (TextView) view.findViewById(R.id.title);
            dateTime = (TextView) view.findViewById(R.id.dateTime);
            bookmark = (ImageView) view.findViewById(R.id.bookmark);

            going = (TextView) view.findViewById(R.id.going);
            maybe = (TextView) view.findViewById(R.id.maybe);
            notGoing = (TextView) view.findViewById(R.id.not_going);

        }
    }
}