package com.example.hack_reboot_team;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "[DBVTB]";
    public static final String TABLE_NAME_USER = "[users]";
    public static final String TABLE_NAME_PRODUCT = "[product]";
    public static final String TABLE_NAME_CON = "[con]";

    public static final String NAME = "[name]";
    public static final String STATYS = "[statys]";
    public static final String IMGID = "[img_id]";
    public static final String PRICE = "[price]";
    public static final String ID_USER = "[id_user]";
    public static final String ID_PRODUCT = "[id_product]";


    public DBhelper(Context context){
        super(context,DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String zp = "CREATE TABLE " + TABLE_NAME_USER + " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                NAME + " text, " +
                STATYS + " text, " +
                IMGID + " integer)";
        db.execSQL(zp);
        zp = "CREATE TABLE " + TABLE_NAME_PRODUCT + " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                NAME + " text, " +
                PRICE + " text)";
        db.execSQL(zp);
        zp = "CREATE TABLE " + TABLE_NAME_CON + " (id integer NOT NULL PRIMARY KEY AUTOINCREMENT, " +
                ID_USER + " integer, " +
                ID_PRODUCT + " integer)";

        db.execSQL(zp);

        zp = "INSERT INTO " + TABLE_NAME_USER + "("+ NAME +","+ STATYS +","+ IMGID +") VALUES ('Вася', 'creator', '2131689473')";
        db.execSQL(zp);

        zp = "INSERT INTO " + TABLE_NAME_PRODUCT + "("+ NAME +","+ PRICE +") VALUES ('Бургер', '500')";
        db.execSQL(zp);

        zp = "INSERT INTO " + TABLE_NAME_CON + "("+ ID_USER +","+ ID_PRODUCT +") VALUES (1, 1)";
        db.execSQL(zp);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_CON);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_USER);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_CON);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME_USER);
        onCreate(db);
    }
}