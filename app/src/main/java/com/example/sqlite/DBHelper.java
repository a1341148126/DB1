package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, int version) {
        //上下文，数据库文件名，null，版本号
        super(context, "user.db", null, version);
    }
    /**
     * 什么时候调用？
     * 当数据库文件创建时调用
     * 在此方法中做什么？
     * 建表，初始化数据
     @param sqLiteDatabase 是操作数据库的对象
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.e("TAG", "onCreate: " );
        String sql = "create table if not exists user (phone varchar primary key ,email varchar not null,name varchar not null)";
        sqLiteDatabase.execSQL(sql);
    }
    /**
     * 当传入的版本号大于当前数据库的版本号时调用     *
     * 用于更新数据库     *
     @param sqLiteDatabase
     @param i
     @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.d("TAG", "onUpgrade: ");
    }
}

