package natasha.restaurantreview;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.w3c.dom.Comment;


public class RestaurantDataSource {

    // Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_ADDRESS, MySQLiteHelper.COLUMN_PHONE, MySQLiteHelper.COLUMN_DESCRIPTION,
            MySQLiteHelper.COLUMN_TAGS, MySQLiteHelper.COLUMN_RATING  };

    public RestaurantDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Restaurant createRestaurant(String name, String address, String phone, String desc, String tag, String rating) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_ADDRESS, address);
        values.put(MySQLiteHelper.COLUMN_PHONE, phone);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, desc);
        values.put(MySQLiteHelper.COLUMN_TAGS, tag);
        values.put(MySQLiteHelper.COLUMN_RATING, rating);
        for(Restaurant res : getAllRestaurants()){
            if(res.getName().contains(name)){
                long updateId = database.update(MySQLiteHelper.TABLE_RESTAURANT, values, MySQLiteHelper.COLUMN_NAME + " = '" + res.getName()+"'", null);
                Cursor cursor = database.query(MySQLiteHelper.TABLE_RESTAURANT,
                        allColumns, MySQLiteHelper.COLUMN_ID + " = " + updateId, null,
                        null, null, null);
                cursor.moveToFirst();
                Restaurant newComment = cursorToComment(cursor);
                cursor.close();
                return newComment;
            }
        }
            long insertId = database.insert(MySQLiteHelper.TABLE_RESTAURANT, null,
                    values);
            Cursor cursor = database.query(MySQLiteHelper.TABLE_RESTAURANT,
                    allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Restaurant newComment = cursorToComment(cursor);
            cursor.close();
            return newComment;
    }


    public Restaurant updateRestaurant(Long id, String name, String address, String phone, String desc, String tag, String rating) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_ADDRESS, address);
        values.put(MySQLiteHelper.COLUMN_PHONE, phone);
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION, desc);
        values.put(MySQLiteHelper.COLUMN_TAGS, tag);
        values.put(MySQLiteHelper.COLUMN_RATING, rating);
        for(Restaurant res : getAllRestaurants()){
            if(res.getId() == id){
                long updateId = database.update(MySQLiteHelper.TABLE_RESTAURANT, values, MySQLiteHelper.COLUMN_NAME + " = '" + res.getName()+"'", null);
                Cursor cursor = database.query(MySQLiteHelper.TABLE_RESTAURANT,
                        allColumns, MySQLiteHelper.COLUMN_ID + " = " + updateId, null,
                        null, null, null);
                cursor.moveToFirst();
                Restaurant newComment = cursorToComment(cursor);
                cursor.close();
                return newComment;
            }
        }
        long insertId = database.insert(MySQLiteHelper.TABLE_RESTAURANT, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESTAURANT,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Restaurant newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }
    public void deleteComment(Restaurant comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_RESTAURANT, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Restaurant> getAllRestaurants() {
        List<Restaurant> comments = new ArrayList<Restaurant>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_RESTAURANT,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Restaurant comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Restaurant cursorToComment(Cursor cursor) {
        Restaurant comment = new Restaurant();
        if( cursor != null ) {
            comment.setId(Long.parseLong(cursor.getString(0)));
            comment.setName(cursor.getString(1));
            comment.setAddress(cursor.getString(2));
            comment.setPhone(cursor.getString(3));
            comment.setDescription(cursor.getString(4));
            comment.setTags(cursor.getString(5));
            comment.setRating(cursor.getString(6));
        }
        return comment;
    }
}