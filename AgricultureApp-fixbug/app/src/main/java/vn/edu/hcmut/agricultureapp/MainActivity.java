package vn.edu.hcmut.agricultureapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import vn.edu.hcmut.agricultureapp.HomeActivity.HomeActivity;
import vn.edu.hcmut.agricultureapp.login.LoginActivity;
import vn.edu.hcmut.agricultureapp.myAccount.EditInfoActivity;
import vn.edu.hcmut.agricultureapp.new_feed.NewFeedActivity;
import vn.edu.hcmut.agricultureapp.new_feed.QAActivity;

public class MainActivity extends AppCompatActivity {
    TextView txtShow;
    DatabaseReference  mDatabase = FirebaseDatabase.getInstance().getReference();

    FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if(currentUser==null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }
//        Toast.makeText(this,"OnCreate",Toast.LENGTH_SHORT).show();



        Button btnUserInformation = (Button) findViewById(R.id.btnUserInformation);
        btnUserInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, QAActivity.class);

            }
        });
        Button btnHomeActivity = findViewById(R.id.btnHomeActivity);
        btnHomeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent4);
            }
        });

//        mDatabase.child("Users").push();
//        mDatabase.child("Users").push().setValue("100");

//


//
//        Post post = connectDatabase.GetPostByUid("-LAN9aGxsHNvtAZlpwbG");
//        if(post!=null){
//            txtShow.setText(post.getName());
//        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else{
//            Toast.makeText(this,currentUser.getUid(),Toast.LENGTH_SHORT).show();
        }
    }


}
