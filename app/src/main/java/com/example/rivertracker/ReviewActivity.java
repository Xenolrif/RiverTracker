package com.example.rivertracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ReviewActivity extends AppCompatActivity {

    EditText e1, e2, e3, e4, e5;
    FirebaseAuth mAuth;
    DatabaseReference dbRef;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        sessionManager = new SessionManager(getApplicationContext());
        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(ReviewActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        e1 = (EditText)findViewById(R.id.namebox);
        e2 = (EditText)findViewById(R.id.emailbox);
        e3 = (EditText)findViewById(R.id.phonebox);
        e4 = (EditText)findViewById(R.id.riverbox);
        e5 = (EditText)findViewById(R.id.commentbox);

        mAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference("review");

        Button back = findViewById(R.id.button15);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReviewActivity.this, Home.class);
                startActivity(intent);
            }
        });
    }

    public void createReview(View v){
        if(e1.getText().toString().equals("") && e2.getText().toString().equals("") && e3.getText().toString().equals("") && e4.getText().toString().equals("") && e5.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),"Blank not allowed", Toast.LENGTH_SHORT).show();
        }else{
            String name = e1.getText().toString();
            String email = e2.getText().toString();
            String phone = e3.getText().toString();
            String river = e4.getText().toString();
            String comment = e5.getText().toString();
            String userId = mAuth.getCurrentUser().getUid();
            Map<String, Object> review = new HashMap<>();
            review.put("name", name);
            review.put("email", email);
            review.put("phone no", phone);
            review.put("river name", river);
            review.put("comment", comment);

            dbRef.child(userId).setValue(review)
                    .addOnSuccessListener(aVoid -> Toast.makeText(getApplicationContext(), "Feedback send successfully", Toast.LENGTH_SHORT).show())
                    .addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Error send feedback: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            Toast.makeText(getApplicationContext(),"Feedback send succesfully",Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(getApplicationContext(),ReviewActivity.class);
            startActivity(i);

        }
    }
}