package vn.edu.hcmut.agricultureapp.myAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import vn.edu.hcmut.agricultureapp.R;

public class EditNameActivity extends AppCompatActivity {
    private final String TAG = "EditNameActivity";
    private EditText mNameField;
    Button mSaveButton;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mNameField= findViewById(R.id.full_name_edit_text);
        mSaveButton = findViewById(R.id.save_name_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidName()){
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setDisplayName(mNameField.getText().toString())
                            .build();

                    mUser.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "Name updated.");

                                        // Save to database
                                        DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("User");
                                        String name = mNameField.getText().toString();
                                        String uid = mUser.getUid();
                                        Map<String, Object> accountUpdates = new HashMap<>();
                                        accountUpdates.put(uid+"/"+"full_name", name);
                                        accountsRef.updateChildren(accountUpdates);

                                        startActivity(new Intent(EditNameActivity.this, EditInfoActivity.class));
                                        finish();
                                    }
                                    else {
                                        Log.w(TAG, "Name update fail");
                                        Toast.makeText(EditNameActivity.this, "Thay đổi tên thất bại!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


    }

    private boolean isValidName(){
        boolean valid = true;
        String name = mNameField.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mNameField.setError(getString(R.string.error_field_required));
            valid = false;
        }
        else {
            mNameField.setError(null);
        }
        return  valid;
    }
}
