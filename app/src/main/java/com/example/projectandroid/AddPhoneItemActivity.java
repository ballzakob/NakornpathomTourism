package com.example.projectandroid;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.projectandroid.db.DatabaseHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import pl.aprilapps.easyphotopicker.DefaultCallback;
import pl.aprilapps.easyphotopicker.EasyImage;

import static com.example.projectandroid.db.DatabaseHelper.COL_DETAIL;
import static com.example.projectandroid.db.DatabaseHelper.COL_IMAGE;
import static com.example.projectandroid.db.DatabaseHelper.COL_LOCATION;
import static com.example.projectandroid.db.DatabaseHelper.COL_TITLE;
import static com.example.projectandroid.db.DatabaseHelper.TABLE_NAME;

public class AddPhoneItemActivity extends AppCompatActivity {

    private static final String TAG = AddPhoneItemActivity.class.getName();

    private DatabaseHelper mHelper;
    private SQLiteDatabase mDb;
    private String mLogoFilename = "วัด.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phone_item);

        mHelper = new DatabaseHelper(this);
        mDb = mHelper.getWritableDatabase();

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doInsertPhoneItem();
            }
        });

        ImageView logoImageView = findViewById(R.id.image_show2);
        logoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EasyImage.openChooserWithGallery(AddPhoneItemActivity.this, "เลือกรูปภาพ",0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagesPicked(@NonNull List<File> list, EasyImage.ImageSource imageSource, int i) {
                File logoFile = list.get(0);
                Log.i(TAG, logoFile.getAbsolutePath());

                File privateDir = getFilesDir();
                File dstFile = new File(privateDir, logoFile.getName());
                try {
                    copy(logoFile,dstFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mLogoFilename = logoFile.getName();
                ImageView logoImageView = findViewById(R.id.image_show2);

                Bitmap bitmap = BitmapFactory.decodeFile(logoFile.getAbsolutePath(), null);
                logoImageView.setImageBitmap(bitmap);
            }
        });
    }

    public static void copy(File src, File dst) throws IOException {
        InputStream in = new FileInputStream(src);
        try {
            OutputStream out = new FileOutputStream(dst);
            try {
                // Transfer bytes from in to out
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
            } finally {
                out.close();
            }
        } finally {
            in.close();
        }
    }

    private void doInsertPhoneItem() {
        EditText titleEditText = findViewById(R.id.title_add_text3);
        EditText locationEditText = findViewById(R.id.location_add_text3);
        EditText detailEditText = findViewById(R.id.detail_add_text3);

        String title = titleEditText.getText().toString();
        String location = locationEditText.getText().toString();
        String detail = detailEditText.getText().toString();

        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, title);
        cv.put(COL_LOCATION, location);
        cv.put(COL_DETAIL, detail);
        cv.put(COL_IMAGE,mLogoFilename);
        mDb.insert(TABLE_NAME, null, cv);

        finish();
    }
}
