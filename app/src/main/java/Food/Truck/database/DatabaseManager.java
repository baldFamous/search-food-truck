package Food.Truck.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

// clase diseñada para gestionar la base de datos
public class DatabaseManager extends DatabaseHelper{

    private SQLiteDatabase database;

    // el constructor recibe el contexto y el nombre de la base de datos
    public DatabaseManager(@Nullable Context context, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, factory);
    }

    // metodo para abrir la base de datos y poder escribir en ella
    public void open(){
        database = getWritableDatabase();
    }

    // metodo para cerrar la base de datos
    @Override
    public void close() {
        super.close();
    }

    // metodo para insertar datos en alguna tabla de la base de datos
    // modificar metodo dependiendo de a que tabla se insertarán datos
    public long insert_us_cons(String nom, String ap, String corr, String contr) {
        ContentValues values = new ContentValues();
        values.put("name", nom);
        values.put("sec_name", ap);
        values.put("email", corr);
        values.put("password", contr);
        return database.insert("user", null, values);
    }

    public long insert_us_prop(String nom, String ap, String corr, String contr, String pat) {
        ContentValues values = new ContentValues();
        values.put("name", nom);
        values.put("sec_name", ap);
        values.put("email", corr);
        values.put("password", contr);
        values.put("food_truck_id", pat);
        return database.insert("user_prop", null, values);
    }

    // metodo para actualizar datos en alguna tabla de la base de datos
    public Cursor fetchData() { // modificar metodo dependiendo de a que tabla se actualizarán datos
        String[] columns = {"_id", "campo1", "campo2"};
        return database.query("mi_tabla", columns, null, null, null, null, null);
    }

}
