package com.example.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private DBHelper dbHelper;
    private SQLiteDatabase sqldb;
    private EditText et_name;
    private EditText et_email;
    private EditText et_phone;
    private TextView tv_output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_name=(EditText)findViewById(R.id.et_name);
        et_email=(EditText)findViewById(R.id.et_email);
        et_phone=(EditText)findViewById(R.id.et_phone);
        tv_output=(TextView)findViewById(R.id.tv_output);
        tv_output.setMovementMethod(ScrollingMovementMethod.getInstance());

        dbHelper=new DBHelper(this,1);
    }

    public void write(View view) {
        if (et_name.getText()==null) {
            Toast.makeText(this,"you forgot your name!",Toast.LENGTH_SHORT).show();
        }else if(et_email.getText()==null){
            Toast.makeText(this,"you forgot your email!",Toast.LENGTH_SHORT).show();
        }else if(et_phone.getText()==null){
            Toast.makeText(this,"you forgot your mobile number!",Toast.LENGTH_SHORT).show();
        }else if(search_phone(sqldb = dbHelper.getReadableDatabase(),et_phone.getText().toString())){
            Toast.makeText(this,"the mobile number has used!",Toast.LENGTH_SHORT).show();
        } else {
            sqldb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", et_name.getText().toString());
            values.put("email",et_email.getText().toString());
            values.put("phone",et_phone.getText().toString());
            sqldb.insert("user", null, values);
            Toast.makeText(this,"insert one line",Toast.LENGTH_SHORT).show();
            tv_output.setText(" ");
            values.clear();
            sqldb.close();
        }
        et_phone.setText(" ");
        et_email.setText(" ");
        et_name.setText(" ");
    }

    public void read(View view){
        StringBuffer result=new StringBuffer("");
        sqldb = dbHelper.getReadableDatabase();
        Cursor cursor=sqldb.query("user", null, null, null, null, null, null);
        if(cursor.moveToFirst()) {
             do{
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String email = cursor.getString(cursor.getColumnIndex("email"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                result.append("name:" + name + "\n" + "email:" + email + "\n" + "phone:" + phone + "\n\n");
            }while (cursor.moveToNext());
            tv_output.setText(String.valueOf(result));
        }else{
            Toast.makeText(this,"No Data!",Toast.LENGTH_SHORT).show();
        }
        sqldb.close();
    }

    public void update(View view){
        if (et_name.getText()==null) {
            Toast.makeText(this,"you forgot your name!",Toast.LENGTH_SHORT).show();
        }else if(et_email.getText()==null){
            Toast.makeText(this,"you forgot your email!",Toast.LENGTH_SHORT).show();
        }else if(et_phone.getText()==null){
            Toast.makeText(this,"you forgot your mobile number!",Toast.LENGTH_SHORT).show();
        }else if(!search_phone(sqldb = dbHelper.getReadableDatabase(),et_phone.getText().toString())){
            Toast.makeText(this,"the mobile number has not login!",Toast.LENGTH_SHORT).show();
        } else {
            sqldb = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", et_name.getText().toString());
            values.put("email",et_email.getText().toString());
            values.put("phone",et_phone.getText().toString());
            String s=et_phone.getText().toString();
            int i=sqldb.update("user", values,"phone=?",
                    new String[]{et_phone.getText().toString()});
            Toast.makeText(this,"update 1 line",Toast.LENGTH_SHORT).show();
            tv_output.setText(" ");
            values.clear();
            sqldb.close();
        }
        et_phone.setText(" ");
        et_email.setText(" ");
        et_name.setText(" ");
    }

    public void delete(View view){
        sqldb = dbHelper.getWritableDatabase();
        int i=sqldb.delete("user",null,null);
        Toast.makeText(this,"delete "+i+" lines",Toast.LENGTH_SHORT).show();
        tv_output.setText(" ");
        sqldb.close();
        et_phone.setText(" ");
        et_email.setText(" ");
        et_name.setText(" ");
    }


    private boolean search_phone(SQLiteDatabase db,String str) {
        Cursor cursor = db.rawQuery("select * from   user  where   phone=? ",	new String[] { str });
        while (cursor.moveToNext()) {
            db.close();
            Log.i(" search_city_name_exist", str + "在数据库已存在,return true");
            return true;
        }
        db.close();
        Log.i("search_phone_name_exist", str + "在数据库不存在，return false");
        return false;
    }


}
