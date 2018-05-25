package vn.edu.hcmut.agricultureapp.myAccount;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import vn.edu.hcmut.agricultureapp.R;

public class EditPhoneNumberActivity extends AppCompatActivity {
   // private final String TAG = "EditPhoneNumberActivity";
    private EditText mPhoneField;
    Button mSaveButton;
    FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mAccountsRef;
    private FirebaseUser mFirebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone_number);


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mAccountsRef = mFirebaseDatabase.getReference("User");
        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        mPhoneField = findViewById(R.id.phone_number_edit_text);
        mSaveButton = findViewById(R.id.save_phone_button);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidPhoneNumber()){
                    String phoneNumber = mPhoneField.getText().toString();
                    String uid = mFirebaseUser.getUid();

                    Map<String, Object> accountUpdates = new HashMap<>();
                    accountUpdates.put(uid+"/"+"phone_number", phoneNumber);
                    mAccountsRef.updateChildren(accountUpdates);

                    startActivity(new Intent(EditPhoneNumberActivity.this, EditInfoActivity.class ));
                    finish();
                }
            }
        });



    }


    private boolean isValidPhoneNumber(){
        boolean valid = true;
        String name = mPhoneField.getText().toString();
        if (TextUtils.isEmpty(name)) {
            mPhoneField.setError(getString(R.string.error_field_required));
            valid = false;
        }
        else {
            mPhoneField.setError(null);
        }
        return  valid;
    }
}
