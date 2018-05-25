package vn.edu.hcmut.agricultureapp.new_feed;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import vn.edu.hcmut.agricultureapp.R;

public class NewFeedActivity extends BaseActivity {
    // UI element
    private StatusAdapter mStatusAdapter;
    private ListView mStatusListView;

    // Firebase instance variables
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mTopicDatabaseReference;

    private String mStatusPath = "Status";
    private String mTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_feed);

        // setup firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        mTopic = intent.getStringExtra("topic");
        mTopicDatabaseReference = mFirebaseDatabase.getReference().child(mStatusPath).child(mTopic);

        // setup listview
        mStatusListView =findViewById(R.id.status_list_view);
        List<Status> statuses = new ArrayList<>();
        mStatusAdapter = new StatusAdapter(this, R.layout.status_item, statuses);
        mStatusListView.setAdapter(mStatusAdapter);

        mTopicDatabaseReference.addChildEventListener(mChildEventListener);

        mStatusListView.setOnItemClickListener(onItemClickListener);

    }

    // receive status
    ChildEventListener mChildEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            Status status1 = dataSnapshot.getValue(Status.class);
            String statusId = dataSnapshot.getKey();
            status1.setStatusId(statusId);

            mStatusAdapter.add(status1);
        }

    public void onChildChanged(DataSnapshot dataSnapshot, String s) {}
    public void onChildRemoved(DataSnapshot dataSnapshot) {}
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
    public void onCancelled(DatabaseError databaseError) {}
};

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(NewFeedActivity.this, FullStatusActivity.class);
            Status child = (Status)parent.getItemAtPosition(position);
            String statusId = child.getStatusId();
            intent.putExtra("topic", mTopic);
            intent.putExtra("status_id", statusId);
            intent.putExtra("isCmtBoxVisible", false);
            startActivity(intent);
        }
    };
}
