package com.products.safetyfirst.modelhelper;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.products.safetyfirst.models.PostModel;
import com.products.safetyfirst.utils.DatabaseUtil;
import com.products.safetyfirst.utils.StringHelper;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Random;

/**
 * Created by rishabh on 12/10/17.
 */

public class PostHelper {

    private UserHelper userhelper = UserHelper.getInstance();
    private StringHelper stringHelper = StringHelper.getInstance();

    public void createNewPost(final String postKey, final String title, final String body, final List<String> fileList, final List<String> imageList ) {
        final int time = (int) System.currentTimeMillis();
        latestPost(new OnRetrievePost() {
            @Override
            public void onComplete(String key) {
                PostModel post = new PostModel(
                        title, body, userhelper.getUserId(), imageList, fileList, time, key
                );
                DatabaseUtil.getDatabase().getReference().child("posts").child(postKey).setValue(post);  // Create post in /posts/
                DatabaseUtil.getDatabase().getReference().child("user-posts").child(userhelper.getUserId()).child(postKey).setValue(true);  // push post key in /user-posts/
            }
        });
    }

    public String createPostKey() {
        return DatabaseUtil.getDatabase().getReference().child("posts").push().getKey();
    }

    public void createImageUrls(final String postKey, final List<Bitmap> images, final List<String> imageList, final int position, final UploadCallbacks uploadCallbacks){
        if(position == images.size()){
            uploadCallbacks.onComplete(imageList);
            return;
        }
        if(position == 0) {
            uploadCallbacks.onStartUpload();
        }
        uploadImage(images.get(position), postKey, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                String downloadUrl = taskSnapshot.getDownloadUrl().toString();
                imageList.add(downloadUrl);
                uploadCallbacks.onProgress(position+1, images.size());
                createImageUrls(postKey, images, imageList, position+1, uploadCallbacks);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                uploadCallbacks.onFail(e);
                imageList.clear();
            }
        });
    }

    private void uploadImage(Bitmap bitmap, String postKey, OnSuccessListener<UploadTask.TaskSnapshot> success, OnFailureListener fail) {
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("posts").child(postKey).child(randomName());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(fail).addOnSuccessListener(success);
    }

    public void latestPost(final OnRetrievePost onRetrievePost) {
        Query ref = DatabaseUtil.getDatabase().getReference("posts").orderByKey().limitToFirst(1);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String key = dataSnapshot.getKey();
                onRetrievePost.onComplete(key);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                /* Maybe Later */
            }
        });
    }

    private static String randomName() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(20);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString()+".jpg";
    }

    public interface UploadCallbacks {

        void onComplete(List<String> imageList);

        void onStartUpload();

        void onProgress(int progress, int total);

        void onFail(Exception e);
    }

    public interface OnRetrievePost {
        void onComplete(String key);
    }

}
