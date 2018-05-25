package vn.edu.hcmut.agricultureapp.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import vn.edu.hcmut.agricultureapp.BaseActivity;
import vn.edu.hcmut.agricultureapp.R;

public class ForgotPassword_1Activity extends BaseActivity {

    private TextView mEmailField;
    Button mButton;
    private FirebaseAuth mAuth;
    private final String TAG = "ForgotPasswordActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_1);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        // Get a support ActionBar corresponding to this toolbar
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        if(ab!= null)
            ab.setDisplayHomeAsUpEnabled(true);

        mEmailField = findViewById(R.id.forgot_pass_text);
        mButton = findViewById(R.id.forgot_pass_button);

        mAuth = FirebaseAuth.getInstance();
        mAuth.setLanguageCode("vi");

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailAddress = mEmailField.getText().toString();
                resetPassword(emailAddress);
            }
        });

    }

    private void resetPassword(String email){
        Log.d(TAG, "reset Password:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            startActivity(new Intent(ForgotPassword_1Activity.this, ForgotPassword_2Activity.class));
                        }
                        else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ForgotPassword_1Activity.this, "Email này chưa đăng ký Best Market!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        hideProgressDialog();
    }

    private boolean validateForm() {
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

        return valid;
    }
}
