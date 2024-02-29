package com.example.sendproduct
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "products.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "products"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_SELLER = "seller"
        const val COLUMN_PRICE = "price"
        const val COLUMN_PICTURE_URI = "picture_uri"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COLUMN_NAME TEXT NOT NULL," +
                "$COLUMN_DESCRIPTION TEXT NOT NULL," +
                "$COLUMN_SELLER TEXT NOT NULL," +
                "$COLUMN_PRICE REAL NOT NULL," +
                "$COLUMN_PICTURE_URI TEXT NOT NULL" +
                ");"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertProduct(product: Product): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, product.name)
            put(COLUMN_DESCRIPTION, product.description)
            put(COLUMN_SELLER, product.seller)
            put(COLUMN_PRICE, product.price)
            put(COLUMN_PICTURE_URI, product.pictureUri)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getAllProducts(): List<Product> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val products = mutableListOf<Product>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
            val seller = cursor.getString(cursor.getColumnIndex(COLUMN_SELLER))
            val price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))
            val pictureUri = cursor.getString(cursor.getColumnIndex(COLUMN_PICTURE_URI))
            products.add(Product(id, name, description, seller, price, pictureUri))
        }
        cursor.close()
        return products
    }
}