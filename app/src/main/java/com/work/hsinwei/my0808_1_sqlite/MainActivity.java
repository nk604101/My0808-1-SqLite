package com.work.hsinwei.my0808_1_sqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    File DB_PATH;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CopyDatabase();

        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(DB_PATH.toString(),null);
        Cursor c = db.rawQuery("Select * from contacts",null);
        c.moveToFirst();
        do{
            String str = c.getString(1);
            Log.d("T0808-DB",str);
        }while(c.moveToNext());
    }

    private void CopyDatabase()
    {
        final int BUFFER_SIZE=400000;
        final String DB_NAME = "test1.sqlite";   //保存的資料庫檔案名
        String PACKAGE_NAME = getPackageName();
        DB_PATH = getDatabasePath(DB_NAME);
        Log.d("T0808-DB",DB_PATH.toString());

        //當路徑不存在 建立路徑
        if(!(DB_PATH.exists()))
        {
            File DIR = new File(getApplicationInfo().dataDir+"/databases");
            DIR.mkdir();
            Log.d("T0808-DB","DIR:"+DIR.toString());

            InputStream is = getResources().openRawResource(R.raw.test1);
            try {
                FileOutputStream fos = new FileOutputStream(DB_PATH);

                byte[] buffer = new byte[BUFFER_SIZE];
                int count = 0;
                while ((count = is.read(buffer))>0)
                {
                    fos.write(buffer,0,count);
                }
                fos.close();
                is.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
