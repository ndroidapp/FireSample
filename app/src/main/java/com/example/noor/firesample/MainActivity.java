package com.example.noor.firesample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG ="MainActivity" ;
    private Button btnUpload;
    private Button btnRetrieve;
    private TextView textView;
    private EditText etId, etName, etPrice;

    FirebaseDatabase database;
    DatabaseReference myRef;

    Product product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textView = findViewById(R.id.textView);
        this.btnRetrieve = findViewById(R.id.btnRetrieve);
        this.btnUpload = findViewById(R.id.btnUpload);
        this.etId =findViewById(R.id.etId);
        this.etName =findViewById(R.id.etName);
        this.etPrice =findViewById(R.id.etPrice);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("Product_Details");



        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id=Integer.parseInt(etId.getText().toString());
                String name=etName.getText().toString();
                int price=Integer.parseInt(etPrice.getText().toString());
                product = new Product();
                product.setId(id);
                product.setName(name);
                product.setPrice(price);
                myRef.setValue(product);

            }
        });

        btnRetrieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        Product value = dataSnapshot.getValue(Product.class);
                        //Log.d(TAG, "Value is: " + value);
                        textView.setText(value.getId()+"\n"+value.getName()+"\n"+value.getPrice());

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
            }
        });

    }
}
