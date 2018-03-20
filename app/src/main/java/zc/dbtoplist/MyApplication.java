package zc.dbtoplist;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyApplication extends Application {
    private static final String DB_NAME = "address.db";
    private static MyApplication mApplication;

    public static MyApplication getApplication() {
        return mApplication;
    }
    private static DaoSession daoSession;
    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        copyDBToDatabases();
        setupDatabase();
    }

    private void setupDatabase() {
        //address.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "address.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    private void copyDBToDatabases() {
        String DB_PATH = "/data/data/" + getPackageName() + "/databases/";
        try {
            String outFileName = DB_PATH + DB_NAME;
            File file = new File(DB_PATH);
            if (!file.mkdirs()) {
                file.mkdirs();
            }
            File dataFile = new File(outFileName);
            if (dataFile.exists()) {
                dataFile.delete();
            }
            InputStream myInput;
            myInput = this.getAssets().open(DB_NAME);
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
            Log.d("Test", "copy db");
        } catch (IOException e) {
            Log.d("Test", "error " + e.toString());
            e.printStackTrace();
        }
    }
}
