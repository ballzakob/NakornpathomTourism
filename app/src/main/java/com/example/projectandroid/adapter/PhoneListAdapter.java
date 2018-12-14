package com.example.projectandroid.adapter;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectandroid.R;
import com.example.projectandroid.model.PhoneItem;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PhoneListAdapter extends ArrayAdapter<PhoneItem> {

    private Context mContext;
    private int mResource;
    private List<PhoneItem> mPhoneItemList;

    public PhoneListAdapter(@NonNull Context context,  /// เอาข้อมูลมาแสดงเป็น list
                            int resource,
                            @NonNull List<PhoneItem> phoneItemList) {
        super(context, resource, phoneItemList);
        this.mContext = context;
        this.mResource = resource;
        this.mPhoneItemList = phoneItemList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(mResource, parent, false);

        TextView titleTextView = view.findViewById(R.id.title_text_view); // ดึงชื่อจาก database มาใช้
        TextView numberTextView = view.findViewById(R.id.number_text_view); // ดึงเบอร์จาก database มาใข้
        ImageView imageView = view.findViewById(R.id.image_view); // ดึงรูปจาก database มาใข้

        PhoneItem phoneItem = mPhoneItemList.get(position);
        String title = phoneItem.title;
        String location = phoneItem.location;
        String filename = phoneItem.image;
        if(title.length() > 20){
            title = title.substring(0,20) + "...";
        }
        titleTextView.setText(title);
        numberTextView.setText(location);

        AssetManager am = mContext.getAssets();
        try {
            InputStream is = am.open(filename);//เปิดไฟล์ใน asserts มาอ่าน
            Drawable drawable = Drawable.createFromStream(is," ");
            imageView.setImageDrawable(drawable);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return view;
    }
}