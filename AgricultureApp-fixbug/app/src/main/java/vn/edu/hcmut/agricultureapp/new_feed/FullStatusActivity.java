package vn.edu.hcmut.agricultureapp.new_feed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.hcmut.agricultureapp.R;

public class FullStatusActivity extends AppCompatActivity {
    private String mUid;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mStatusIdDataRef;
    DatabaseReference mCmtDataRef;
    StorageReference mStatusImagesStorageRef;

    CommentAdapter mCommentAdapter;
    ImageView mStarImage;
    ImageView mProfileImage;
    TextView mProfileNameField;
    TextView mTimeField;
    TextView mContentField;
    ImageView mContentImage;
    TextView mLikedNumberField;
    TextView mCommentNumberField;
    ListView mCmtListView;
    View mCmtBoxView;
    ImageView mCmtButton;
    ImageView mLikeButton;

    ImageView mCmtBoxProfileImage;
    EditText mCmtBoxTextContent;
    Button mCmtBoxSelectImageButton;
    Button mCmtBoxPostButton;

    private Uri mPostingImageUri;

    private final int RESULT_LOAD_IMG =3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_status);

        mCmtListView = findViewById(R.id.comment_list_view);
        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup)inflater.inflate(R.layout.status_item,mCmtListView,false);
        mCmtListView.addHeaderView(header);

        mStarImage = header.findViewById(R.id.star_image_view);
        mProfileImage = header.findViewById(R.id.profile_image);
        mProfileNameField = header.findViewById(R.id.profile_name_text_view);
        mTimeField = header.findViewById(R.id.time_text_view);
        mContentField = header.findViewById(R.id.content_text_view);
        mContentImage =header.findViewById(R.id.content_image);
        mLikedNumberField =header.findViewById(R.id.like_number_text_view);

        mCmtBoxView =findViewById(R.id.comment_box);
        mCmtBoxProfileImage = findViewById(R.id.cmt_box_profile_image);
        mCmtBoxTextContent = mCmtBoxView.findViewById(R.id.cmt_box_edit_text);
        mCmtBoxPostButton = mCmtBoxView.findViewById(R.id.cmt_box_post_button);
        mCmtBoxSelectImageButton = mCmtBoxView.findViewById(R.id.cmt_box_select_image_button);
        mCmtBoxSelectImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLocalImage(RESULT_LOAD_IMG);
            }
        });

        mCmtBoxPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               pushStatusToDatabase();
            }
        });


        mCmtButton = header.findViewById(R.id.comment_button);
        mLikeButton = header.findViewById(R.id.plus_one_button);
        mCmtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCmtBoxView.setVisibility(View.VISIBLE);
            }
        });

        Intent intent = getIntent();
        String topic = intent.getExtras().getString("topic");
        String statusId =  intent.getExtras().getString("status_id");
        Boolean isCmtBoxVisible = intent.getExtras().getBoolean("isCmtBoxVisible");
        if(!isCmtBoxVisible)
            mCmtBoxView.setVisibility(View.GONE);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mStatusImagesStorageRef = FirebaseStorage.getInstance().getReference("StatusImages");
        mStatusIdDataRef = mFirebaseDatabase.getReference().child("Status").child(topic).child(statusId);
        mUid = FirebaseAuth.getInstance().getUid();
        mCmtDataRef = mStatusIdDataRef.child("Comments");
        mStatusIdDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Status status = dataSnapshot.getValue(Status.class);
                if(status== null)
                    return;

                DatabaseReference statusDataRef = FirebaseDatabase.getInstance().getReference().child("User").child(status.getUserId());
                statusDataRef.addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Object nameDataSnapshot = dataSnapshot.child("full_name").getValue();
                                if(nameDataSnapshot !=null) {
                                    String name = nameDataSnapshot.toString();
                                    if (name != null&&!name.equals(""))
                                        mProfileNameField.setText(name);
                                }

                                Object imageSnapshot = dataSnapshot.child("image_uri").getValue();
                                if(imageSnapshot!=null){
                                    String imageUri = imageSnapshot.toString();
                                    if(imageUri!=null && !imageUri.isEmpty()){
                                        Glide.with(mProfileImage.getContext())
                                                .load(imageUri)
                                                .into(mProfileImage);
                                    }

                                }

                                Object isFarmerSnapshot = dataSnapshot.child("isFarmer").getValue();
                                if(imageSnapshot!=null){
                                    Boolean isFarmer = Boolean.parseBoolean( isFarmerSnapshot.toString());
                                    if(isFarmer== false)
                                        mStarImage.setVisibility(View.VISIBLE);

                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        }
                );


                String atTime = status.getAtTime();
                if(atTime!=null&& !atTime.isEmpty())
                    mTimeField.setText(atTime);

                String content = status.getTextContent();
                if(content!=null && !content.isEmpty())
                    mContentField.setText(content);

                String contentImageUrl = status.getImageContentUrl();
                if(contentImageUrl!=null&&!contentImageUrl.isEmpty())
                    Glide.with(mContentImage.getContext())
                    .load(contentImageUrl)
                    .into(mContentImage);
                else
                    mContentImage.setVisibility(View.GONE);

                String likedNumber = Integer.toString(status.getLikeNumber());
                if(likedNumber!=null && !likedNumber.isEmpty())
                    mLikedNumberField.setText(likedNumber);


            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DatabaseReference myUserDataRef = mFirebaseDatabase.getReference().child("User").child(mUid);
        myUserDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object profileImageDataSnapshot = dataSnapshot.child("image_uri").getValue();
                if(profileImageDataSnapshot !=null) {
                    String profileImageUrl = profileImageDataSnapshot.toString();
                    if(profileImageUrl!=null &&!profileImageUrl.isEmpty())
                        Glide.with(mCmtBoxProfileImage.getContext())
                                .load(profileImageUrl)
                                .into(mCmtBoxProfileImage);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Initialize message ListView and its adapter
        List<Comment> comments = new ArrayList<>();
        mCommentAdapter = new CommentAdapter(this, R.layout.comment_item, comments);
        mCmtListView.setAdapter(mCommentAdapter);

        // receive cmt
         ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Comment comment = dataSnapshot.getValue(Comment.class);
                mCommentAdapter.add(comment);
            }

            public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            public void onCancelled(DatabaseError databaseError) {}
        };

        mCmtDataRef.addChildEventListener(childEventListener);



    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if(resultCode == RESULT_OK)
            if(reqCode == RESULT_LOAD_IMG) {
                mPostingImageUri = data.getData();
            }
    }

    private void getLocalImage(int number){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), number);

    }

    private void pushStatusToDatabase(){
        String textContent = mCmtBoxTextContent.getText().toString();
        if(mPostingImageUri!=null) {
            pushImageStatus(textContent);
            Toast.makeText(getApplicationContext(), "Đăng thành công!",Toast.LENGTH_SHORT).show();
            mPostingImageUri = null;
            mCmtBoxTextContent.setText("");
            mCmtBoxView.setVisibility(View.GONE);
        }
        else if(!textContent.isEmpty()){
            String atTime = Calendar.getInstance().getTime().toString().substring(0, 20);
            Comment comment = new Comment( mUid, atTime, mCmtBoxTextContent.getText().toString(), null);
            mCmtDataRef.push().setValue(comment);
            Toast.makeText(getApplicationContext(), "Đăng thành công!",Toast.LENGTH_SHORT).show();
            mPostingImageUri = null;
            mCmtBoxTextContent.setText("");
            mCmtBoxView.setVisibility(View.GONE);
        }
    }

    private void pushImageStatus(final String text){
        StorageReference photoRef = mStatusImagesStorageRef.child(mUid + mPostingImageUri.getLastPathSegment());
        photoRef.putFile(mPostingImageUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // When the image has successfully uploaded, we get its download URL
                        final Uri downloadUri = taskSnapshot.getDownloadUrl();
                        if(downloadUri!=null) {
                            String imageUri = downloadUri.toString();
                            String atTime = Calendar.getInstance().getTime().toString().substring(0, 20);
                            Comment comment = new Comment( mUid, atTime, text, imageUri );
                            mCmtDataRef.push().setValue(comment);
                        }
                    }
                });
    }

}
