package com.example.tictactoe;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.databinding.DataBindingUtil;

import com.example.tictactoe.database.User;
import com.example.tictactoe.databinding.UserListItemBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

import static com.example.tictactoe.Util.getCurrentUserId;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Holder extends RecyclerView.ViewHolder {
    public UserListItemBinding binding;

    public Holder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
        binding.invite.setOnClickListener(v -> {
            DatabaseReference db = FirebaseDatabase.getInstance().getReference();
            db.child("users")
                    .child(getCurrentUserId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User me = dataSnapshot.getValue(User.class);

                            OkHttpClient client = new OkHttpClient();

                            String to = binding.getUser().getPushId();

                            Request request = new Request.Builder()
                                    .url(String
                                            .format("%s/sendNotification?to=%s&fromPushId=%s&fromId=%s&fromName=%s&type=%s",
                                                    "https://tictactoe-3618d-default-rtdb.europe-west1.firebasedatabase.app/",
                                                    to,
                                                    me.getPushId(),
                                                    getCurrentUserId(),
                                                    me.getName(),
                                                    "invite"))
                                    .build();

                            client.newCall(request).enqueue(new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        });
    }
}