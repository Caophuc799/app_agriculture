package vn.edu.hcmut.agricultureapp.myAccount;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import vn.edu.hcmut.agricultureapp.BaseActivity;
import vn.edu.hcmut.agricultureapp.R;
import vn.edu.hcmut.agricultureapp.login.LoginActivity;
import vn.edu.hcmut.agricultureapp.myAccount.EditEmailActivity;
import vn.edu.hcmut.agricultureapp.myAccount.EditNameActivity;
import vn.edu.hcmut.agricultureapp.myAccount.EditPasswordActivity;
import vn.edu.hcmut.agricultureapp.myAccount.EditPhoneNumberActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditInfoActivity extends BaseActivity implements View.OnClickListener {
    TextView mFullNameField;
    TextView mEmailField;
    private TextView mPhoneNumberField;
    private ImageView mProfileImage;
    private ImageView mBackgroundImage;
    private TextView mCalendarField;
    private TextView mSexField;
    TextView mPasswordView;
    private final int RC_PHOTO_PICKER = 10;
    private final int BACKGROUND_PHOTO_PICKER =20;
    FirebaseStorage mFirebaseStorage;
    private StorageReference mProfilePhotoStorRef;
    private DatabaseReference mUserDataRef;
    FirebaseAuth mAuth ;
    private FirebaseUser mUser;
    private final int CALENDAR_PICKER = 10;
    private final int SEX_PICKER = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        //       getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // findViewById(R.id.user_name).setOnClickListener(this);
        findViewById(R.id.password).setOnClickListener(this);
        findViewById(R.id.full_name).setOnClickListener(this);
        findViewById(R.id.email).setOnClickListener(this);
        findViewById(R.id.phone_number).setOnClickListener(this);
        findViewById(R.id.calendar_button).setOnClickListener(this);
        findViewById(R.id.sex_button).setOnClickListener(this);
        findViewById(R.id.logout_button).setOnClickListener(this);
        findViewById(R.id.background_image).setOnClickListener(this);

        mBackgroundImage = findViewById(R.id.background_image);
        mPasswordView = findViewById(R.id.password);
        mSexField = findViewById(R.id.sex_text_view);
        mCalendarField =findViewById(R.id.calendar_text_view);
        mFullNameField = findViewById(R.id.full_name_text_view);
        mEmailField = findViewById(R.id.email_text_view);
        mPhoneNumberField = findViewById(R.id.phone_number_text_view);
        mProfileImage = findViewById(R.id.profile_image);
        mProfileImage.setOnClickListener(this);

        // Setup firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mFirebaseStorage = FirebaseStorage.getInstance();
        mProfilePhotoStorRef = mFirebaseStorage.getReference().child("profileImages");
        mUserDataRef = FirebaseDatabase.getInstance().getReference().child("User");

        setupData();

    }
    //
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id =item.getItemId();
//        if(id==android.R.id.home) this.finish();
///*     if(mTonggle.onOptionsItemSelected(item)){
//         return true;
//     }
//  */
//        return super.onOptionsItemSelected(item);
//
//    }
    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == CALENDAR_PICKER) {
            return new DatePickerDialog(this,
                    myDateListener, 1990, 1, 1);
        }
        else if(id == SEX_PICKER){
            return sexPickerDialog();
        }
        return null;
    }

    private Dialog sexPickerDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(EditInfoActivity.this);
        builder.setTitle(R.string.sex)
                .setItems(R.array.sex_picker_array, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        String sex = "Không xác định";
                        if(which == 0){
                            sex = "Nam";
                        }
                        else if(which == 1)
                            sex = "Nữ";
                        mSexField.setText(sex);
                        saveSexData(sex);
                    }
                });
        return builder.create();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    Calendar newCalendar = Calendar.getInstance();
                    arg0.setMaxDate(newCalendar.getTimeInMillis());
                    newCalendar.set(1990, 1, 1);
                    arg0.setMinDate(newCalendar.getTimeInMillis());

                    StringBuilder s =new StringBuilder().append(arg3).append("/")
                            .append(arg2).append("/").append(arg1);
                    mCalendarField.setText(s);
                    saveDate(s.toString());

                }
            };

    private void saveDate(String str){
        Map<String, Object> accountUpdates = new HashMap<>();
        accountUpdates.put(mUser.getUid()+"/date_of_birth", str);
        mUserDataRef.updateChildren(accountUpdates);
    }

    private void saveSexData(String str){
        Map<String, Object> accountUpdates = new HashMap<>();
        accountUpdates.put(mUser.getUid()+"/sex", str);
        mUserDataRef.updateChildren(accountUpdates);
    }
/////////////////////////////////////////////////////////////////

    private boolean isLoginWithPassword(){
        boolean result = false;
        for (UserInfo user: mUser.getProviderData()) {
            if(user.getProviderId().equals("password")){
                result = true;
                break;
            }
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == BACKGROUND_PHOTO_PICKER && resultCode == RESULT_OK){
            Uri selectedImageUri = data.getData();
            updateBacgroundImage(selectedImageUri);
        }else if (requestCode == RC_PHOTO_PICKER && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            updateProfileImage(selectedImageUri);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //case R.id.user_name:
            //intent =new Intent(EditInfoActivity.this, EditUserNameActivity.class);
            //  break;
            case R.id.background_image:
                getLocalImage(BACKGROUND_PHOTO_PICKER);
                break;
            case R.id.profile_image:
                getLocalImage(RC_PHOTO_PICKER);
                break;

            case R.id.password:
                startActivity(new Intent(EditInfoActivity.this, EditPasswordActivity.class));
                break;

            case R.id.full_name:
                startActivity( new Intent(EditInfoActivity.this, EditNameActivity.class));
                break;

            case R.id.email:
                startActivity( new Intent(EditInfoActivity.this,EditEmailActivity.class));
                break;

            case R.id.phone_number:
                startActivity( new  Intent(EditInfoActivity.this, EditPhoneNumberActivity.class));
                break;
            case R.id.calendar_button:
                showDialog(CALENDAR_PICKER);
                break;
            case R.id.sex_button:
                showDialog(SEX_PICKER);
                break;
            case R.id.logout_button:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(EditInfoActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private void setupData(){
        DatabaseReference myUserdRef = FirebaseDatabase.getInstance().getReference().child("User").child(mUser.getUid());
        myUserdRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Object nameDataSnapshot = dataSnapshot.child("full_name").getValue();
                if(nameDataSnapshot !=null) {
                    String name = nameDataSnapshot.toString();
                    if (name != null&&!name.equals(""))
                        mFullNameField.setText(name);
                }

                Object emailDataSnapshot = dataSnapshot.child("email_address").getValue();
                if(emailDataSnapshot !=null) {
                    String email = emailDataSnapshot.toString();
                    if (email != null&&!email.equals(""))
                        mEmailField.setText(email);
                }


                Object phoneDataSnapshot = dataSnapshot.child("phone_number").getValue();
                if(phoneDataSnapshot !=null) {
                    String phoneNumber = phoneDataSnapshot.toString();
                    if (phoneNumber != null&&!phoneNumber.equals(""))
                        mPhoneNumberField.setText(phoneNumber);
                }

                Object dateDataSnapshot = dataSnapshot.child("date_of_birth").getValue();
                if(dateDataSnapshot!=null){
                    String dateOfBirth = dateDataSnapshot.toString();
                    if(dateOfBirth!=null && !dateOfBirth.equals("")){
                        mCalendarField.setText(dateOfBirth);
                    }
                }

                Object sexDataSnapshot = dataSnapshot.child("sex").getValue();
                if(sexDataSnapshot!=null){
                    String sexString = sexDataSnapshot.toString();
                    if(sexString!=null && !sexString.equals("")){
                        mSexField.setText((sexString));
                    }
                }

                Object backgroundSnapshot = dataSnapshot.child("background_uri").getValue();
                if(backgroundSnapshot!=null){
                    String backgroundUri = backgroundSnapshot.toString();
                    if(backgroundUri!=null && !backgroundUri.isEmpty()){
                        Glide.with(mBackgroundImage.getContext())
                                .load(backgroundUri)
                                .into(mBackgroundImage);
                    }
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if(isLoginWithPassword())
            mPasswordView.setVisibility(View.VISIBLE);
        else
            mPasswordView.setVisibility(View.GONE);
    }

    private void updateProfileImage(Uri selectedImageUri){
//        showProgressDialog();
//        // Get a reference to store file at chat_photos/<FILENAME>
//        StorageReference photoRef = mProfilePhotoStorRef.child( mUser.getUid()+ selectedImageUri.getLastPathSegment());
//
//        // Upload file to Firebase Storage
//        photoRef.putFile(selectedImageUri)
//                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // When the image has successfully uploaded, we get its download URL
//                        final Uri downloadUri = taskSnapshot.getDownloadUrl();
//
//                        // Update image
//                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                .setPhotoUri(downloadUri)
//                                .build();
//
//                        mUser.updateProfile(profileUpdates)
//                                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<Void> task) {
//                                        if (task.isSuccessful()) {
//                                            // Save to database
//                                            DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("Accounts");
//                                            String imageUri = downloadUri.toString();
//                                            String uid = mUser.getUid();
//                                            Map<String, Object> accountUpdates = new HashMap<>();
//                                            String s = "image_uri";
//                                            accountUpdates.put(uid+"/"+ s , imageUri);
//                                            accountsRef.updateChildren(accountUpdates);
//
//                                            recreate();
//                                            //Toast.makeText(getApplicationContext(), "Cập nhật thành công.", Toast.LENGTH_SHORT).show();
//                                            // Log.d(TAG, "User profile updated.");
//                                        }
//
//                                        hideProgressDialog();
//                                    }
//                                });
//                    }
//                });
        if (selectedImageUri== null) return;
        // Reset background photo in local
        String photoResource = selectedImageUri.toString();
        if (photoResource != null&& !photoResource.equals(""))
            Glide.with(mProfileImage.getContext())
                    .load(photoResource)
                    .into(mProfileImage);


        // Upload file to Firebase Storage
        // Get a reference to store file at chat_photos/<FILENAME>
        StorageReference photoRef = mProfilePhotoStorRef.child( mUser.getUid()+ selectedImageUri.getLastPathSegment());
        photoRef.putFile(selectedImageUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // When the image has successfully uploaded, we get its download URL
                        final Uri downloadUri = taskSnapshot.getDownloadUrl();
//                        // Update image
//                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
//                                .setPhotoUri(downloadUri)
//                                .build();
                        // Save to database
                        DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("User");
                        if(downloadUri!=null) {
                            String imageUri = downloadUri.toString();
                            String uid = mUser.getUid();
                            Map<String, Object> accountUpdates = new HashMap<>();
                            String s = "image_uri";
                            accountUpdates.put(uid + "/" + s, imageUri);
                            accountsRef.updateChildren(accountUpdates);
                        }
                    }
                });

    }

    private void updateBacgroundImage(final Uri selectedImageUri){
        if (selectedImageUri== null) return;
        // Reset background photo in local
        String photoResource = selectedImageUri.toString();
        if (photoResource != null&& !photoResource.isEmpty())
            Glide.with(mBackgroundImage.getContext())
                    .load(photoResource)
                    .into(mBackgroundImage);

        // Upload file to Firebase Storage
        // Get a reference to store file at chat_photos/<FILENAME>
        StorageReference photoRef = mProfilePhotoStorRef.child( mUser.getUid()+ selectedImageUri.getLastPathSegment());
        photoRef.putFile(selectedImageUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // When the image has successfully uploaded, we get its download URL
                        final Uri downloadUri = taskSnapshot.getDownloadUrl();

                        // Save to database
                        DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("User");
                        if(downloadUri!=null) {
                            String imageUri = downloadUri.toString();
                            String uid = mUser.getUid();
                            Map<String, Object> accountUpdates = new HashMap<>();
                            String s = "background_uri";
                            accountUpdates.put(uid + "/" + s, imageUri);
                            accountsRef.updateChildren(accountUpdates);
                        }
                    }
                });
    }

    private void getLocalImage(int number){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), number);

    }
}
