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

import vn.edu.hcmut.agricultureapp.R;


public class EditPasswordActivity extends AppCompatActivity {
    private final String TAG = "EditPasswordActivity";
    private EditText mPasswordField;
    private FirebaseUser mUser;
    Button mSaveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        mUser = FirebaseAuth.getInstance().getCurrentUser();

        mPasswordField = findViewById(R.id.password_edit_text);
        mSaveButton = findViewById(R.id.save_password_button);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidPassword()){
                    String newPassword = mPasswordField.getText().toString();

                    mUser.updatePassword(newPassword)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated.");
                                        startActivity(new Intent(EditPasswordActivity.this, EditInfoActivity.class));
                                        finish();
                                    }
                                    else {
                                        Log.w(TAG, "User password updated:fail");
                                        Toast.makeText(EditPasswordActivity.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    boolean isValidPassword(){
        boolean valid = true;
        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password) ) {
            mPasswordField.setError(getString(R.string.error_field_required));
            valid = false;
        }
        else if(mPasswordField.length() < 6){
            mPasswordField.setError(getString(R.string.error_invalid_password));
            valid = false;
        }
        else {
            mPasswordField.setError(null);
        }

        return valid;
    }
}
