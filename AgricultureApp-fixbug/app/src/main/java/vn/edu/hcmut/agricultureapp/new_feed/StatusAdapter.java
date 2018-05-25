package vn.edu.hcmut.agricultureapp.new_feed;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class StatusAdapter extends ArrayAdapter<Status> {
    public StatusAdapter(Context context, int resource, List<Status> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.status_item, parent, false);
        }
        final ImageView profileImage = convertView.findViewById(R.id.profile_image);
        final TextView profileNameField = convertView.findViewById(R.id.profile_name_text_view);
        TextView timeField = convertView.findViewById(R.id.time_text_view);
        TextView contentField = convertView.findViewById(R.id.content_text_view);
        ImageView contentImage = convertView.findViewById(R.id.content_image);
        final ImageView starImage = convertView.findViewById(R.id.star_image_view);
        final TextView likeNumberField = convertView.findViewById(R.id.like_number_text_view);
        TextView titleField = convertView.findViewById(R.id.title_tex_view);

        ImageView cmtButton = convertView.findViewById(R.id.comment_button);
        final ImageView plusOneButton = convertView.findViewById(R.id.plus_one_button);

        final Status status = getItem(position);

        //set event for click cmt button
        cmtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), FullStatusActivity.class);
                intent.putExtra("topic", status.getTopic());
                intent.putExtra("status_id", status.getStatusId());
                intent.putExtra("isCmtBoxVisible", true );
               parent.getContext().startActivity(intent);


            }
        });

        plusOneButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View v) {
                status.setLikeNumber(status.getLikeNumber()+1);
                likeNumberField.setText(Integer.toString (status.getLikeNumber()));
                DatabaseReference statusDataRef = FirebaseDatabase.getInstance().getReference("Status/"+status.getTopic()+"/"+status.getStatusId()).child("likeNumber");
                statusDataRef.setValue(status.getLikeNumber());
            }
        });



        DatabaseReference statusDataRef = FirebaseDatabase.getInstance().getReference().child("User").child(status.getUserId());
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

        String contentText = status.getTextContent();
        if(contentText!= null)
            contentField.setText(contentText);

        String atTime = status.getAtTime();
        if(atTime!=null)
            timeField.setText(atTime);

        String contentImageUrl = status.getImageContentUrl();
        if(contentImageUrl!=null && !contentImageUrl.isEmpty()){
            contentImage.setVisibility(View.VISIBLE);
            Glide.with(contentImage.getContext())
                    .load(contentImageUrl)
                    .into(contentImage);
        }
        else {
            contentImage.setVisibility(View.GONE);
        }

        likeNumberField.setText(Integer.toString(status.getLikeNumber()));
        if(status.getTitle()!= null)
            titleField.setText(status.getTitle());
        return convertView;
    }
}
