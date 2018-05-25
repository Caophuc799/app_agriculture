package vn.edu.hcmut.agricultureapp.new_feed;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.sql.BatchUpdateException;

import vn.edu.hcmut.agricultureapp.R;

public class QAActivity extends AppCompatActivity implements View.OnClickListener{

    Button mTrongTrotButton;
    Button mVatNuoiButton;
    Button mThuySanButton;
    Button mQAButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa);

        mTrongTrotButton = findViewById(R.id.trong_trot_button);
        mVatNuoiButton = findViewById(R.id.vat_nuoi_button);
        mThuySanButton = findViewById(R.id.thuy_san_button);
        mQAButton = findViewById(R.id.question_answer_button);

        mTrongTrotButton.setOnClickListener(this);
        mVatNuoiButton.setOnClickListener(this);
        mThuySanButton.setOnClickListener(this);
        mQAButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.trong_trot_button){
            Intent intent = new Intent(QAActivity.this, NewFeedActivity.class);
            intent.putExtra("topic", "cay_trong");
            startActivity(intent);
        }
        else if(v.getId()==R.id.vat_nuoi_button){
            Intent intent = new Intent(QAActivity.this, NewFeedActivity.class);
            intent.putExtra("topic", "vat_nuoi");
            startActivity(intent);
        }
        else if(v.getId()==R.id.thuy_san_button) {
            Intent intent = new Intent(QAActivity.this, NewFeedActivity.class);
            intent.putExtra("topic", "thuy_san");
            startActivity(intent);
        }
        else if(v.getId()==R.id.question_answer_button) {
            Intent intent = new Intent(QAActivity.this, StatusPostingActivity.class);
            startActivity(intent);
        }
    }
}
