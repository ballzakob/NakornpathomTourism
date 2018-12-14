package com.example.projectandroid;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity {
    private TextView title_s;
    private TextView detail_s;
    private ImageView image_s;
    private long mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String detail = intent.getStringExtra("detail");
        String image = intent.getStringExtra("image");
        mId = intent.getLongExtra("id", 0);

        title_s = findViewById(R.id.title_show);
        detail_s = findViewById(R.id.detial_show);
        image_s = findViewById(R.id.image_show);

        title_s.setText(title);  //todo โชว์ หัวเรื่อง
        detail_s.setText(detail); // todo โชว์ detail

        AssetManager am = getAssets();
        try {
            InputStream is = am.open(image);//todo เปิดไฟล์ใน asserts มาอ่าน
            Drawable drawable = Drawable.createFromStream(is," ");
            image_s.setImageDrawable(drawable); // todo โชว์ รูป
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
