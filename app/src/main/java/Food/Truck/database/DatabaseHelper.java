package Food.Truck.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.sql.SQLDataException;

// clase diseñada para crear la base de datos
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String db_name = "food_truck_intern.db";
    private static final int version = 6;

    // el constructor recibe el contexto y el nombre de la base de datos y la version
    public DatabaseHelper(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, db_name, factory, version); // factory es para crear cursores personalizados
                                                    // un cursor es un objeto que permite recorrer los registros de una tabla
    }

    // metodo se ejecuta cuando se crea la base de datos
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS food_truck " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT, " +
                "image TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS user " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "sec_name TEXT, " +
                "email TEXT, " +
                "password TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS user_prop " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "sec_name TEXT, " +
                "email TEXT, " +
                "password TEXT, " +
                "food_truck_id TEXT)");

        db.execSQL("CREATE TABLE IF NOT EXISTS food_truck_menu " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "description TEXT, " +
                "price INTEGER, " +
                "food_truck_id INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS food_truck_location " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "latitude REAL, " +
                "longitude REAL, " +
                "food_truck_id INTEGER)");

    }

    // metodo se ejecuta cuando se actualiza la base de datos
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS food_truck");
        db.execSQL("DROP TABLE IF EXISTS user");
        db.execSQL("DROP TABLE IF EXISTS user_prop");
        db.execSQL("DROP TABLE IF EXISTS food_truck_menu");
        db.execSQL("DROP TABLE IF EXISTS food_truck_location");
        onCreate(db);
    }
}
