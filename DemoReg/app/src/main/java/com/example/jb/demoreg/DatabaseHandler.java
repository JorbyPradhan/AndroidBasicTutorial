package com.example.jb.demoreg;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION=1;
    private Context context;
    private static final String DATABASE_NAME=" register_db";

    private static final String TABLE_NAME=" reg";

    private static final String KEY_NAME="Name";
    private static final String KEY_EMAIL="Email";
    private static final String KEY_PHONE="Phone";
    private static final String KEY_GENDER="Gender";
    private static final String KEY_DATE="DateofBith";
    private static final String KEY_AGE="Age";
    private static final String KEY_NRC="Nrc";
    private static final String KEY_FNAME="FName";
    private static final String KEY_img = "img";

    public DatabaseHandler (Context context){

        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context = context;

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE= " CREATE TABLE " + TABLE_NAME + " ( "
                + KEY_NAME + " TEXT, " + KEY_NRC + " TEXT, " + KEY_AGE + " TEXT, " + KEY_DATE + " DATE, " + KEY_GENDER + " TEXT, " + KEY_FNAME + " TEXT, "
                + KEY_EMAIL + " TEXT, " + KEY_PHONE + " TEXT, " + KEY_img + " blob " + " ) ";
        db.execSQL(CREATE_CONTACTS_TABLE);
        Log.i("Hello", "Create Table dsfkjdfsklajagkfldlsjgksljsdlkf");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    void addData(User contact){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues values=new ContentValues();
        values.put(KEY_NAME,contact.getName());
        values.put(KEY_NRC,contact.getNrc());
        values.put(KEY_AGE,contact.getAge());
        values.put(KEY_DATE,contact.getDob());
        values.put(KEY_GENDER,contact.getGender());
        values.put(KEY_FNAME,contact.getFname());
        values.put(KEY_EMAIL,contact.getEmail());
        values.put(KEY_PHONE,contact.getPhone());
        values.put(KEY_img,contact.getImg());

        db.insert(TABLE_NAME,null, values);
        Log.i("DAta LAP", "Helo world");
        db.close();
    }
    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = " Select name,img from " + TABLE_NAME;
        Cursor data = db.rawQuery(query,null);
        return data;
    }

    public User getAllContact(String name1){
        Log.i("NAME", name1);
        User contact = new User();
        String selectAll= " SELECT * FROM "+ TABLE_NAME + " WHERE " + KEY_NAME +  " = '" + name1 +"' ";
        SQLiteDatabase db= this.getWritableDatabase();
        Cursor cursor= db.rawQuery(selectAll,null);

        if(cursor.moveToFirst()){
            do{
              //  User contact=new User();
                contact.setName(cursor.getString(0));
                contact.setNrc(cursor.getString(1));
                contact.setAge(cursor.getString( 2));
                contact.setDob(cursor.getString(3));
                contact.setGender(cursor.getString(4));
                contact.setFname(cursor.getString(5));
                contact.setEmail(cursor.getString(6));
                contact.setPhone(cursor.getString(7));
                contact.setImg(cursor.getBlob(8));

              //  list.add(contact);
            }while (cursor.moveToNext());
        }
        db.close();
        return contact;
    }
    public void deleteData(String s)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "name = '" + s + "'", null);
    }
    public boolean updateData(String s,String ph,String s1)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL( " UPDATE " + TABLE_NAME + " SET " + KEY_NAME + " = '" + s + "' , " + KEY_PHONE + " = '" + ph + "' " + " WHERE " + KEY_NRC + " = '" + s1 + "'");
        return true;
    }
}
