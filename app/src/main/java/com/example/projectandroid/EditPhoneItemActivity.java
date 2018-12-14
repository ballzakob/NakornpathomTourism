package com.example.projectandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectandroid.db.DatabaseHelper;

import static com.example.projectandroid.db.DatabaseHelper.COL_DETAIL;
import static com.example.projectandroid.db.DatabaseHelper.COL_ID;
import static com.example.projectandroid.db.DatabaseHelper.COL_LOCATION;
import static com.example.projectandroid.db.DatabaseHelper.COL_TITLE;
import static com.example.projectandroid.db.DatabaseHelper.TABLE_NAME;

public class EditPhoneItemActivity extends AppCompatActivity {

    private EditText mTitleEditText;
    private EditText mLocationEditText;
    private EditText mDetailEditText;
    private Button mSaveButton;

    private long mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phone_item);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String location = intent.getStringExtra("location");
        String detail = intent.getStringExtra("detail");
        mId = intent.getLongExtra("id", 0);

        mTitleEditText = findViewById(R.id.title_edit_text);
        mLocationEditText = findViewById(R.id.location_edit_text);
        mDetailEditText = findViewById(R.id.detail_edit_text);
        mSaveButton = findViewById(R.id.save_button);

        mTitleEditText.setText(title);
        mLocationEditText.setText(location);
        mDetailEditText.setText(detail);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo: บันทึกข้อมูลใหม่ลง db
                DatabaseHelper helper = new DatabaseHelper(EditPhoneItemActivity.this);
                SQLiteDatabase db = helper.getWritableDatabase();

                String newTitle = mTitleEditText.getText().toString().trim();
                String newLocation = mLocationEditText.getText().toString().trim();
                String newDetail = mDetailEditText.getText().toString().trim();

                ContentValues cv = new ContentValues();
                cv.put(COL_TITLE, newTitle);
                cv.put(COL_LOCATION, newLocation);
                cv.put(COL_DETAIL, newDetail);

                db.update(
                        TABLE_NAME,
                        cv,
                        COL_ID + " = ?",
                        new String[]{String.valueOf(mId)}
                );
                finish();
            }
        });
    }
}
