package vn.edu.hcmut.agricultureapp.new_feed;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import vn.edu.hcmut.agricultureapp.R;

/**
 * Created by vutuananh on 5/13/2018.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    public CommentAdapter(Context context, int resource, List<Comment> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.comment_item, parent, false);
        }

        final ImageView profileImage = convertView.findViewById(R.id.comment_profile_image);
        final TextView profileNameField = convertView.findViewById(R.id.comment_profile_name_text_view);
        TextView timeField = convertView.findViewById(R.id.comment_time_text_view);
        TextView contentField = convertView.findViewById(R.id.comment_content_text_view);
        ImageView contentImage = convertView.findViewById(R.id.comment_content_image);
        final ImageView starImage = convertView.findViewById(R.id.star_image_view);

        Comment comment = getItem(position);

        DatabaseReference statusDataRef = FirebaseDatabase.getInstance().getReference().child("User").child(comment.getUserId());
        statusDataRef.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Object nameDataSnapshot = dataSnapshot.child("full_name").getValue();
                        if(nameDataSnapshot !=null) {
                            String name = nameDataSnapshot.toString();
                            if (name != null&&!name.equals(""))
                                profileNameField.setText(name);
                        }

                        Object imageSnapshot = dataSnapshot.child("image_uri").getValue();
                        if(imageSnapshot!=null){
                            String imageUri = imageSnapshot.toString();
                            if(imageUri!=null && !imageUri.isEmpty()){
                                Glide.with(profileImage.getContext())
                                        .load(imageUri)
                                        .into(profileImage);
                            }

                        }

                        Object isFarmerSnapshot = dataSnapshot.child("isFarmer").getValue();
                        if(imageSnapshot!=null){
                            Boolean isFarmer = Boolean.parseBoolean( isFarmerSnapshot.toString());
                            if(isFarmer== false)
                                starImage.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                }
        );


        timeField.setText(comment.getAtTime());
        contentField.setText(comment.getTextContent());

        String contentImageUrl = comment.getImageContentUrl();
        if(contentImageUrl!=null && !contentImageUrl.isEmpty()){
            contentImage.setVisibility(View.VISIBLE);
            Glide.with(contentImage.getContext())
                    .load(contentImageUrl)
                    .into(contentImage);
        }
        else {
            contentImage.setVisibility(View.GONE);
        }

        return convertView;
    }
}