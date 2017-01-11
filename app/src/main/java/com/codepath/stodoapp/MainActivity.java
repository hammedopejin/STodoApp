package com.codepath.stodoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.codepath.stodoapp.R.id.eeNewItem;


public class MainActivity extends AppCompatActivity {

    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    int iPos;
    String iValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        items.add("First Item");
        items.add("Second Item");
        setupListViewListener();
        setupListViewCListener();


    }


    public void onAddItem(View v) {
        EditText etNewItem = (EditText)findViewById(eeNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        itemsAdapter.notifyDataSetChanged();
        etNewItem.setText("");
        writeItems();
    }

    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos,
                                                   long id) {
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
    }
    private void setupListViewCListener(){
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter, View item, int pos,
                                            long id) {
                        iPos = ((int)adapter.getItemIdAtPosition(pos));
                        iValue = adapter.getItemAtPosition(pos).toString();
                        onClick(item);

                        return;
                    }
                });
    }


    // launching an activity for a result
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("pos", iPos);
        i.putExtra("item", iValue);
        startActivityForResult(i, REQUEST_CODE);
    }

    // time to handle the result of the sub-activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract name value from result extras
            String item = data.getExtras().getString("item");
            itemsAdapter.insert(item, iPos);
            itemsAdapter.remove(iValue);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
        }
    }


    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
