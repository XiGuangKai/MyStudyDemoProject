package com.mycontentproviderdemo;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

    private SQLiteDatabase db;
    private static final UriMatcher uriMatcher;
    private MySqliteOpenHelper mySqliteOpenHelper;

    private static int QUERYSUCCESS = 0;
    private static int INSERTSUCCESS = 1;
    private static int DELETESUCCESS = 2;
    private static int UPDATESUCCESS = 3;
    private Cursor cursor;

    @Override
    public boolean onCreate() {
        // 创建数据库
        MySqliteOpenHelper mySqliteOpenHelper = new MySqliteOpenHelper(getContext());
        db = mySqliteOpenHelper.getWritableDatabase();
        return true;
    }

    //静态代码块，随着类的加载而加载
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI("com.mycontentproviderdemo.provider","query", QUERYSUCCESS);
        uriMatcher.addURI("com.mycontentproviderdemo.provider","1", INSERTSUCCESS);
        uriMatcher.addURI("com.mycontentproviderdemo.provider","delete", DELETESUCCESS);
        uriMatcher.addURI("com.mycontentproviderdemo.provider","0", UPDATESUCCESS);
    }


    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        //判断当前使用的uri是否匹配，返回值就是传入的int常量
        if (QUERYSUCCESS == uriMatcher.match(uri)){
            return db.query("phoneNumber", projection, selection, selectionArgs,
                    null, null, sortOrder);
        }else {
            return null;
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        //判断当前使用的uri是否匹配，返回值就是传入的int常量
        if (INSERTSUCCESS == uriMatcher.match(uri)) {
            db.insert("phoneNumber", null, values);
            // 发送通知，通知uri有改变
            getContext().getContentResolver().notifyChange(uri,null);
        }else{
            Log.e("MyContentProvider","插入数据失败");
        }
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        int deleteNumber = 0;
        if (DELETESUCCESS == uriMatcher.match(uri)){
            deleteNumber = db.delete("phoneNumber", selection, selectionArgs);
            // 发送通知，通知uri有改变
            getContext().getContentResolver().notifyChange(uri,null);
        }

        //返回删除的行数
        return deleteNumber;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {

        int updateNumber = 0;
        if (UPDATESUCCESS == uriMatcher.match(uri)){
            updateNumber = db.update("phoneNumber", values, selection, selectionArgs);
            // 发送通知，通知uri有改变
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return updateNumber;
    }
}
