
package com.example.sqlitedemo


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.sqlitedemo.Constant.COLUMN_AGE
import com.example.sqlitedemo.Constant.COLUMN_ID
import com.example.sqlitedemo.Constant.COLUMN_NAME
import com.example.sqlitedemo.Constant.DATABASE_NAME
import com.example.sqlitedemo.Constant.DATABASE_VERSION
import com.example.sqlitedemo.Constant.TABLE_USER


class Databasehelper(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """CREATE TABLE $TABLE_USER(
        $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
        $COLUMN_NAME TEXT,
        $COLUMN_AGE INTEGER
        )
        """.trimMargin()
        db?.execSQL(createTable)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Leave it empty
    }
    fun insertUser(name: String,age: Int):Long{
        val values=ContentValues()
        values.put(COLUMN_NAME,name)
        values.put(COLUMN_AGE,age)
        return writableDatabase.insert(TABLE_USER,null,values)
    }

    fun fetchUser(): List<User>{
        val userList = mutableListOf<User>()
        val cursor: Cursor = readableDatabase.query(TABLE_USER,null,null,null,null,null,null)

        with(cursor){
            if(count > 0){
                while(moveToNext()){
                    val id= getLong(getColumnIndexOrThrow(COLUMN_ID))
                    val name= getString(getColumnIndexOrThrow(COLUMN_NAME))
                    val age= getInt(getColumnIndexOrThrow(COLUMN_AGE))
                    userList.add(User(id.toInt(),name,age))
                }
            }
        }
        return userList

    }
    fun updateUser(user: User): Int{
        val values = ContentValues().apply {
            put(COLUMN_NAME, user.name)
            put(COLUMN_AGE,user.age)
        }

        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(user.id.toString())

        return writableDatabase.update(
            TABLE_USER,values,selection,selectionArgs
        )
    }
    fun deleteUser(id: Int){
        val selection = "$COLUMN_ID = ?"
        val selectionArgs = arrayOf(id.toString())

        writableDatabase.delete(TABLE_USER,selection, selectionArgs)
    }

}