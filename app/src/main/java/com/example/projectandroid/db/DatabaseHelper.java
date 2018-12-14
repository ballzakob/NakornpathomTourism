package com.example.projectandroid.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "phone.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tourism_location";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_LOCATION = "location";
    public static final String COL_DETAIL = "detail";
    public static final String COL_IMAGE = "image";

    private static final String SQL_CREATE_TABLE_PHONE
            = "CREATE TABLE " + TABLE_NAME + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COL_TITLE + " TEXT,"
            + COL_LOCATION + " TEXT,"
            + COL_DETAIL + " TEXT,"
            + COL_IMAGE + " TEXT "
            + ")";


    public DatabaseHelper(Context context) {
        super(context, "phone.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_PHONE);

        ContentValues cv = new ContentValues();
        cv.put(COL_TITLE, "วัดพระปฐมเจดีย์ราชวรมหาวิหาร");
        cv.put(COL_LOCATION, "ตำบลพระปฐมเจดีย์ อำเภอเมืองนครปฐม จังหวัดนครปฐม 73000");
        cv.put(COL_DETAIL, "องค์พระปฐมเจดีย์ เป็นปูชนียสถานอันสำคัญของประเทศไทย อยู่ภายในวัดพระปฐมเจดีย์ราชวรมหาวิหาร มีประวัติความเป็นมายาวนาน เชื่อว่าเป็นที่ประดิษฐานพระบรมสารีริกธาตุขององค์พระโคตมพุทธเจ้า\n"
                +"\n" + "องค์พระปฐมเจดีย์ เป็นพระเจดีย์ใหญ่ รูป ระฆังคว่ำ ปากผายมหึมา โครงสร้างเป็นไม้ซุง รัดด้วยโซ่เส้นมหึมาก่ออิฐ ถือปูน ประดับด้วยกระเบื้องปูทับ ประกอบด้วยวิหาร 4 ทิศ กำแพงแก้ว 2 ชั้น ถือเป็นเจดีย์ที่ใหญ่ที่สุด เป็นอันดับ 1 ของประเทศไทยอีกด้วย เป็นที่ประดิษฐานพระบรมสารีริกธาตุ ของพระพุทธเจ้า เป็นที่เคารพสักการบูชาของบรรดาพุทธศาสนิกชนทั่วโลก ทางวัดกำหนดให้มีงานเทศกาลนมัสการองค์พระปฐมเจดีย์ ในวันขึ้น 12 ค่ำ เดือน 12 ถึง วันแรม 5 ค่ำ เดือน 12 รวม 9 วัน 9 คืน เป็น ประจำทุกปี");
        cv.put(COL_IMAGE,"emergency_medicine_1669.png");
        db.insert(TABLE_NAME, null, cv);

        cv = new ContentValues();
        cv.put(COL_TITLE, "พระราชวังสนามจันทร์");
        cv.put(COL_LOCATION, "ตำบลพระปฐมเจดีย์ อำเภอเมืองนครปฐม จังหวัดนครปฐม 73000");
        cv.put(COL_DETAIL, "พระราชวังสนามจันทร์ ตั้งอยู่ในตำบลพระปฐมเจดีย์ อำเภอเมืองนครปฐม จังหวัดนครปฐม ห่างจาก พระปฐมเจดีย์ ประมาณ 2 กิโลเมตร มีเนื้อที่ประมาณ 888 ไร่ 3 งาน 24 ตารางวา สร้างขึ้นโดย พระบาทสมเด็จพระมงกุฎเกล้าเจ้าอยู่หัว รัชกาลที่ 6 หลังจากพระองค์สวรรคต พระราชวังสนามจันทร์ใช้เป็นที่ทำการของส่วนราชการต่าง ๆ ของจังหวัดนครปฐม รวมทั้งเป็นวิทยาเขตหนึ่งของ มหาวิทยาลัยศิลปากร\n"
                +"\n" + "ตั้งแต่วันที่ 1 เมษายน พ.ศ. 2561 พระราชวังสนามจันทร์ เปิดให้ประชาชนเข้ามาออกกำลังกาย ช่วงเช้า เวลา 5:00 – 8:00 น. และช่วงเย็น เวลา 16:00 – 20:00 น.");
        cv.put(COL_IMAGE,"bangkok_ems_1646.png");
        db.insert(TABLE_NAME, null, cv);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
