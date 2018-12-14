package com.example.projectandroid;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.projectandroid.adapter.PhoneListAdapter;
import com.example.projectandroid.db.DatabaseHelper;
import com.example.projectandroid.model.NakornpathomTourismItem;

import java.util.ArrayList;
import java.util.List;

import static com.example.projectandroid.db.DatabaseHelper.COL_DETAIL;
import static com.example.projectandroid.db.DatabaseHelper.COL_ID;
import static com.example.projectandroid.db.DatabaseHelper.COL_IMAGE;
import static com.example.projectandroid.db.DatabaseHelper.COL_LOCATION;
import static com.example.projectandroid.db.DatabaseHelper.COL_TITLE;
import static com.example.projectandroid.db.DatabaseHelper.TABLE_NAME;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getName();

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private List<NakornpathomTourismItem> mNakornpathomTourismItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) { // todo เตรียม data base
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // todo เตรียม Item

        mHelper = new DatabaseHelper(MainActivity.this);
        mDb = mHelper.getWritableDatabase();

        Button addPhoneItemButton = findViewById(R.id.add_phone_item_button);
        addPhoneItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddPhoneItemActivity.class);
                startActivity(intent);
            }
        });

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                finish();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadPhoneData();
        setupListView();
    }

    private void loadPhoneData() {
        Cursor c = mDb.query(TABLE_NAME, null, null, null, null, null, null);

        mNakornpathomTourismItemList = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(COL_ID));
            String title = c.getString(c.getColumnIndex(COL_TITLE));
            String location = c.getString(c.getColumnIndex(COL_LOCATION));
            String detail = c.getString(c.getColumnIndex(COL_DETAIL));
            String image = c.getString(c.getColumnIndex(COL_IMAGE));

            NakornpathomTourismItem item = new NakornpathomTourismItem(id, title, location,detail, image);
            mNakornpathomTourismItemList.add(item);
        }
        c.close();
    }

    private void setupListView() {
        PhoneListAdapter adapter = new PhoneListAdapter(
                MainActivity.this,
                R.layout.item_phone,
                mNakornpathomTourismItemList
        );
        ListView lv = findViewById(R.id.result_list_view);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) { // todo ถ้ากดที่ list มด list หนึ่งจะทำการโทร
                NakornpathomTourismItem item = mNakornpathomTourismItemList.get(position);

                Toast t = Toast.makeText(MainActivity.this, item.title, Toast.LENGTH_SHORT);
                t.show();

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                intent.putExtra("title", item.title);
                intent.putExtra("location", item.location);
                intent.putExtra("detail", item.detail);
                intent.putExtra("image", item.image);
                intent.putExtra("id", item._id);
                startActivity(intent);

//                Intent intent = new Intent(
//                        Intent.ACTION_DIAL,
//                        Uri.parse("tel:" + item.location)
//                );
//                startActivity(intent);

            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) { // todo หากกดค้างจะเป็นการให้เลือกว่าจะลบหรือจะแก้ไข
                String[] items = new String[]{
                        "Edit",
                        "Delete"
                };

                new AlertDialog.Builder(MainActivity.this)
                        .setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final NakornpathomTourismItem item = mNakornpathomTourismItemList.get(position);

                                switch (i) {
                                    case 0: // Edit
                                        Intent intent = new Intent(MainActivity.this, EditPhoneItemActivity.class);
                                        intent.putExtra("title", item.title);
                                        intent.putExtra("location", item.location);
                                        intent.putExtra("id", item._id);
                                        startActivity(intent);
                                        break;
                                    case 1: // Delete
                                        new AlertDialog.Builder(MainActivity.this)
                                                .setMessage("ต้องการลบข้อมูลสถานที่นี้ ใช่หรือไม่")
                                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        mDb.delete(
                                                                TABLE_NAME,
                                                                COL_ID + " = ?",
                                                                new String[]{String.valueOf(item._id)}
                                                        );
                                                        loadPhoneData();
                                                        setupListView();
                                                    }
                                                })
                                                .setNegativeButton("No", null)
                                                .show();
                                        break;
                                }
                            }
                        })
                        .show();

                return true;
            }
        });
    }
}
