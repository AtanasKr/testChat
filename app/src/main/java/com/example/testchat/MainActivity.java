package com.example.testchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
    private ListView list;
    private ArrayList<String> arrayList;
    private ArrayAdapter<String> adapter;
    private boolean myMessage=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //final TextView myTextView = findViewById(R.id.roomMsg);
        mDatabase = FirebaseDatabase.getInstance().getReference("Message");
        list = findViewById(R.id.list);
        arrayList = new ArrayList<String>();
        adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, arrayList);
        list.setAdapter(adapter);

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if(myMessage==true){
                    arrayList.add("My message");
                    arrayList.add(dataSnapshot.getValue().toString());
                    adapter.notifyDataSetChanged();
                    myMessage = false;
                }
                else {
                    //myTextView.setText(dataSnapshot.getValue().toString());
                    arrayList.add("Placeholder");
                    arrayList.add(dataSnapshot.getValue().toString());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //myTextView.setText("Cancelled");
                arrayList.add("Cancelled");
            }
        });
    }
    public void sendMessage(View view){
        EditText myEditText = findViewById(R.id.inputTxt);
        myMessage = true;
        mDatabase.setValue(myEditText.getText().toString());
        myEditText.setText("");
    }
}
