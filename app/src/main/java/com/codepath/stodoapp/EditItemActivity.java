package com.codepath.stodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    EditText etName;
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);


        // Prepare data intent
        etName = (EditText)findViewById(R.id.editText);
        etName.setText(getIntent().getStringExtra("item"));
        etName.setSelection(etName.getText().length());

    }

    // launched for a result
    public void onSubmit(View v) {

        // Pass relevant data back as a result
        Intent data = new Intent();
        data.putExtra("pos", pos);
        data.putExtra("item", etName.getText().toString());
        data.putExtra("code", 200);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

}
