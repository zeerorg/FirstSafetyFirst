package com.products.safetyfirst.impementations;

import com.products.safetyfirst.adapters.EventsAdapter;
import com.products.safetyfirst.interfaces.EventsAdapterView;
import com.products.safetyfirst.interfaces.EventsInteractor;
import com.products.safetyfirst.interfaces.EventsPresenter;
import com.products.safetyfirst.interfaces.EventsView;
import com.products.safetyfirst.models.Event_model;

import java.util.ArrayList;

/**
 * Created by vikas on 11/10/17.
 */

public class EventsPresenterImpl implements EventsPresenter, EventsInteractor.OnUpdateFinishedListener {

    private EventsView eventsView;
    private EventsInteractor eventsInteractor;
    private EventsAdapterView eventsAdapterView;

    public EventsPresenterImpl(EventsView eventsView){

        this.eventsView = eventsView;
        this.eventsInteractor = new EventsInteractorImpl(this);

    }

    public EventsPresenterImpl(EventsAdapter adapter){
        this.eventsAdapterView = adapter;
        this.eventsInteractor = new EventsInteractorImpl(this);
    }

    @Override
    public void onDestroy() {
        eventsView = null;
    }

    @Override
    public void request() {
        eventsInteractor.requestEvents();
    }

    @Override
    public void getChildren(ArrayList<Event_model> events) {
        eventsAdapterView.addAll(events);
    }

    @Override
    public void addAction(String mEventId,String mProfileKey, int mAction) {
        if (eventsView != null) {
            eventsView.showProgress();
        }
        eventsInteractor.addAction(mEventId, mProfileKey, mAction);
    }

    @Override
    public void onActionError(String message) {
        if (eventsView != null) {
            eventsView.hideProgress();
        }
    }

    @Override
    public void onActionSuccess(String message) {
        if (eventsView != null) {
            eventsView.hideProgress();
        }
    }

    @Override
    public void onError(String message) {

    }

    @Override
    public void onSuccess(String message) {

    }
}