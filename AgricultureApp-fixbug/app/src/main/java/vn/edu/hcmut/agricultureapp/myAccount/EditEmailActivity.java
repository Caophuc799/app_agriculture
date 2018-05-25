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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import vn.edu.hcmut.agricultureapp.R;


public class EditEmailActivity extends AppCompatActivity {
    private EditText mEmailField ;
    Button mSaveButton;
    private FirebaseUser mUser;
    private final String TAG = "EditEmailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_email);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mEmailField = findViewById(R.id.email_edit_text);
        mSaveButton = findViewById(R.id.save_email_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidEmail()){

                    mUser.updateEmail(mEmailField.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User email address updated.");

                                        // Save to database
                                        DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("User");
                                        String emailAddress = mEmailField.getText().toString();
                                        String uid = mUser.getUid();
                                        Map<String, Object> accountUpdates = new HashMap<>();
                                        accountUpdates.put(uid+"/"+"email_address", emailAddress);
                                        accountsRef.updateChildren(accountUpdates);

                                        // Come back parent activity
                                        startActivity(new Intent(EditEmailActivity.this, EditInfoActivity.class ));
                                        finish();
                                    }else{
                                        Toast.makeText(EditEmailActivity.this ,"Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                                        Log.w(TAG, "User email address update:fail");
                                    }
                                }
                            });
                }

            }
        });


    }

    private  boolean isValidEmail(){
        boolean valid = true;
        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError(getString(R.string.error_field_required));
            valid = false;
        }else if(!email.contains("@") || email.contains(" ")){
            mEmailField.setError(getString(R.string.error_invalid_email));
            valid = false;
        }
        else {
            mEmailField.setError(null);
        }
        return  valid;
    }
}
