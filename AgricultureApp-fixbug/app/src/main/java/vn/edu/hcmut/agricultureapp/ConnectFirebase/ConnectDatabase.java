package vn.edu.hcmut.agricultureapp.ConnectFirebase;

import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import vn.edu.hcmut.agricultureapp.myAccount.Account;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ConnectDatabase {
    private DatabaseReference mDatabase;
    // ...
    public  ConnectDatabase(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    ///Đăng bai len firebase với tham số là object Post   -post là bài đằng
    public void WriteNewPost(Post post) {

        String key = mDatabase.child("Posts").push().getKey();
        post.setUid(key);
        mDatabase.child("Posts").child(key).setValue(post);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/Post/" + key, post);
//        mDatabase.updateChildren(childUpdates);
    }
//
//    public Post GetPostByUid(String uid) {
//        DatabaseReference mRef = mDatabase.child("Post").child(uid);
//        final List<Post> listpost = new ArrayList<Post>();
//        while (listpost.isEmpty()) {
//            mRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(DataSnapshot dataSnapshot) {
//                    Post post = dataSnapshot.getValue(Post.class);
//
//                    listpost.setText(post);
//
//                }
//
//                @Override
//                public void onCancelled(DatabaseError databaseError) {
//
//                }
//            });
//        }
//        if (listpost.isEmpty()) return null;
//        return listpost.get(0);
//    }

    ///Tạo một user mới có userId có sẵng, truyền tham số là userId và object User
    public void WriteNewUserDatabase(final Account account) {
        mDatabase.child("Users").child(account.uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(getApplicationContext(),dataSnapshot.exists()+"",Toast.LENGTH_SHORT).show();
                if(!dataSnapshot.exists()){
                    mDatabase.child("Users").child(account.uid).setValue(account);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
