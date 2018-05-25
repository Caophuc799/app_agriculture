package vn.edu.hcmut.agricultureapp.new_feed;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.hcmut.agricultureapp.R;

public class StatusPostingActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    Spinner mSpinner;
    EditText mTitle;
    EditText mContent;
    ImageButton mAddImageButton;
    Button mPostingButton;
    private String mTopic= "cay_trong";
    private Uri mImageUri;

    private DatabaseReference mStatusDataRef;
    private StorageReference mStatusImagesStorageRef;
    private String mUid;

    private final int IMAGE_PICKER=9;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status_posting);

        mStatusDataRef = FirebaseDatabase.getInstance().getReference("Status");
        mUid = FirebaseAuth.getInstance().getUid();
        mStatusImagesStorageRef = FirebaseStorage.getInstance().getReference("StatusImages");
        List<String> categories = new ArrayList<String>();
        categories.add("Cây trồng");
        categories.add("Vật nuôi");
        categories.add("Thủy sản");

        mTitle = findViewById(R.id.title_edit_text);
        mContent = findViewById(R.id.thac_mac_edit_view);
        mAddImageButton = findViewById(R.id.add_image_button);
        mPostingButton= findViewById(R.id.posting_button);
        mSpinner =findViewById(R.id.spinner);

        mPostingButton.setOnClickListener(this);
        mAddImageButton.setOnClickListener(this);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mSpinner.setAdapter(dataAdapter);
        mSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        if(item.equals("Cây trồng"))
            mTopic = "cay_trong";
        else if(item.equals("Vật nuôi"))
            mTopic = "vat_nuoi";
        else if(item.equals("Thủy sản"))
            mTopic = "thuy_san";
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

//
    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if(resultCode == RESULT_OK)
            if(reqCode == IMAGE_PICKER) {
                mImageUri = data.getData();
            }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.posting_button){
            if(mImageUri!=null)
                pushImageToStorage();
            else
                pushStatusToFirebase(null);


        }

        else if(v.getId()== R.id.add_image_button){
            getLocalImage(IMAGE_PICKER);
        }

    }


    private void getLocalImage(int number) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), number);
    }

    private void pushStatusToFirebase(String imagePath){
        DatabaseReference topicDataRef = mStatusDataRef.child(mTopic);
        DatabaseReference dataRef = topicDataRef.push();
        String atTime = Calendar.getInstance().getTime().toString().substring(0, 20);
        Status status = new Status(mTopic, dataRef.getKey(), mUid, atTime, mTitle.getText().toString(), mContent.getText().toString(), imagePath, 0);
        dataRef.setValue(status);
        Toast.makeText(getApplicationContext(),"Đăng thành công!", Toast.LENGTH_SHORT).show();
    }


    private void pushImageToStorage(){
        StorageReference photoRef = mStatusImagesStorageRef.child(mUid + mImageUri.getLastPathSegment());
        photoRef.putFile(mImageUri)
                .addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // When the image has successfully uploaded, we get its download URL
                        final Uri downloadUri = taskSnapshot.getDownloadUrl();
                        if(downloadUri!=null) {
                            String imagePath = downloadUri.toString();
                            pushStatusToFirebase(imagePath);
                        }
                    }
                });
    }
}
