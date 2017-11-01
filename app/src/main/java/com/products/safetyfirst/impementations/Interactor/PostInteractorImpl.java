package com.products.safetyfirst.impementations.Interactor;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.products.safetyfirst.interfaces.interactor.PostInteractor;
import com.products.safetyfirst.interfaces.presenter.PostPresenter;
import com.products.safetyfirst.models.PostModel;

import java.util.ArrayList;

import static com.products.safetyfirst.utils.DatabaseUtil.getDatabase;

/**
 * Created by vikas on 01/11/17.
 */

public class PostInteractorImpl implements PostInteractor {

    private PostPresenter presenter;

    public PostInteractorImpl(PostPresenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void requestFirstPosts() {
        Query query;

        query = getDatabase().getReference()
                .child("posts");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PostModel> mListOfPosts = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    mListOfPosts.add(x.getValue(PostModel.class));
                }
                presenter.getChildren(mListOfPosts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Post Interacter", "Could not fetch initial posts");
            }
        });
    }

    @Override
    public void requestPost(String key) {
        Query query;

        query = getDatabase().getReference()
                .child("posts");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<PostModel> mListOfPosts = new ArrayList<>();
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    mListOfPosts.add(x.getValue(PostModel.class));
                }
                presenter.getAnother(mListOfPosts);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Post Interacter", "Could not fetch initial posts");
            }
        });
    }
}
