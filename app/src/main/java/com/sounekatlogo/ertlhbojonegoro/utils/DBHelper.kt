package com.sounekatlogo.ertlhbojonegoro.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // below is the method for creating a database by a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // below is a sqlite query, where column names
        // along with their data types is given
        val survey = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY, " +
                uid + " TEXT," +
                nama + " TEXT," +
                nik + " TEXT," +
                noKK + " TEXT," +
                alamat + " TEXT," +
                desa + " TEXT," +
                kecamatan + " TEXT," +
                jumlahKK + " TEXT," +
                jumlahPenghuni + " TEXT," +
                penghasilanKK + " TEXT," +
                luasRumah + " TEXT," +
                fondasi + " TEXT," +
                sloof + " TEXT," +
                kolom + " TEXT," +
                ringBalok + " TEXT," +
                kudaKuda + " TEXT," +
                dinding + " TEXT," +
                lantai + " TEXT," +
                penutupAtap + " TEXT," +
                statusPenguasaanLahan + " TEXT," +
                koordinat + " TEXT," +
                ktp + " TEXT," +
                samping + " TEXT," +
                dalamRumah + " TEXT," +
                status + " TEXT," +
                date + " TEXT" + ")")

        // we are calling sqlite
        // method for executing our query
        db.execSQL(survey)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        // this method is to check if table already exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    // This method is for adding data in our database
    fun addSurvey(
        uid1: String,
        nama1: String,
        nik1: String,
        noKK1: String,
        alamat1: String,
        desa1: String,
        kecamatan1: String,
        jumlahKK1: String,
        jumlahPenghuni1: String,
        penghasilan1: String,
        luasRumah1: String,
        pondasi1: String,
        sloof1: String,
        kolom1: String,
        ringBalok1: String,
        kudaKuda1: String,
        dinding1: String,
        lantai1: String,
        penutupAtap1: String,
        statusPenguasaanLahan1: String,
        koordinat1: String,
        ktp1: String,
        samping1: String,
        dalamRumah1: String,
        status1: String,
        date1: String,
    ) {

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(uid, uid1)
        values.put(nama, nama1)
        values.put(nik, nik1)
        values.put(noKK, noKK1)
        values.put(alamat, alamat1)
        values.put(desa, desa1)
        values.put(kecamatan, kecamatan1)
        values.put(jumlahKK, jumlahKK1)
        values.put(jumlahPenghuni, jumlahPenghuni1)
        values.put(penghasilanKK, penghasilan1)
        values.put(luasRumah, luasRumah1)
        values.put(fondasi, pondasi1)
        values.put(sloof, sloof1)
        values.put(kolom, kolom1)
        values.put(ringBalok, ringBalok1)
        values.put(kudaKuda, kudaKuda1)
        values.put(dinding, dinding1)
        values.put(lantai, lantai1)
        values.put(penutupAtap, penutupAtap1)
        values.put(statusPenguasaanLahan, statusPenguasaanLahan1)
        values.put(koordinat, koordinat1)
        values.put(ktp, ktp1)
        values.put(samping, samping1)
        values.put(dalamRumah, dalamRumah1)
        values.put(status, status1)
        values.put(date, date1)

        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        // all values are inserted into database
        db.insert(TABLE_NAME, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getAllSurvey(uids: String): Cursor? {

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        return db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $uid = '$uids'", null)

    }

    fun editSurvey(
        id1: Int,
        uid1: String,
        nama1: String,
        nik1: String,
        noKK1: String,
        alamat1: String,
        desa1: String,
        kecamatan1: String,
        jumlahKK1: String,
        jumlahPenghuni1: String,
        penghasilan1: String,
        luasRumah1: String,
        pondasi1: String,
        sloof1: String,
        kolom1: String,
        ringBalok1: String,
        kudaKuda1: String,
        dinding1: String,
        lantai1: String,
        penutupAtap1: String,
        statusPenguasaanLahan1: String,
        koordinat1: String,
        ktp1: String,
        samping1: String,
        dalamRumah1: String,
        status1: String,
        date1: String,
    ) {
        // calling a method to get writable database.
        val db = this.writableDatabase

        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put(uid, uid1)
        values.put(nama, nama1)
        values.put(nik, nik1)
        values.put(noKK, noKK1)
        values.put(alamat, alamat1)
        values.put(desa, desa1)
        values.put(kecamatan, kecamatan1)
        values.put(jumlahKK, jumlahKK1)
        values.put(jumlahPenghuni, jumlahPenghuni1)
        values.put(penghasilanKK, penghasilan1)
        values.put(luasRumah, luasRumah1)
        values.put(fondasi, pondasi1)
        values.put(sloof, sloof1)
        values.put(kolom, kolom1)
        values.put(ringBalok, ringBalok1)
        values.put(kudaKuda, kudaKuda1)
        values.put(dinding, dinding1)
        values.put(lantai, lantai1)
        values.put(penutupAtap, penutupAtap1)
        values.put(statusPenguasaanLahan, statusPenguasaanLahan1)
        values.put(koordinat, koordinat1)
        values.put(ktp, ktp1)
        values.put(samping, samping1)
        values.put(dalamRumah, dalamRumah1)
        values.put(status, status1)
        values.put(date, date1)

        db.update(TABLE_NAME, values, "id=?", arrayOf(id1.toString()))
        db.close()
    }

    fun delete(id1: Int?) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "id=?", arrayOf(id1.toString()))
        db.close()
    }

    fun editStatusSurvey(id1: Int) {
        val db = this.writableDatabase

        // below we are creating
        // a content values variable
        val values = ContentValues()
        values.put(status, "Sudah Diupload")

        db.update(TABLE_NAME, values, "id=?", arrayOf(id1.toString()))
        db.close()
    }

    companion object {
        // here we have defined variables for our database

        // below is variable for database name
        private val DATABASE_NAME = "E_RTLH_BONJONEGORO"

        // below is the variable for database version
        private val DATABASE_VERSION = 1

        // below is the variable for table name
        val TABLE_NAME = "survey"

        // below is the variable for id column
        val ID_COL = "id"

        val uid = "uid"
        val nama = "nama"
        val nik = "nik"
        val noKK = "noKK"
        val alamat = "alamat"
        val desa = "desa"
        val kecamatan = "kecamatan"
        val jumlahKK = "jumlahKK"
        val jumlahPenghuni = "jumlahPenghuni"
        val penghasilanKK = "penghasilanKK"
        val luasRumah = "luasRumah"
        val fondasi = "fondasi"
        val sloof = "sloof"
        val kolom = "kolom"
        val ringBalok = "ringBalok"
        val kudaKuda = "kudaKuda"
        val dinding = "dinding"
        val lantai = "lantai"
        val penutupAtap = "penutupAtap"
        val statusPenguasaanLahan = "statusPenguasaanLahan"
        val koordinat = "koordinat"
        val ktp = "ktp"
        val samping = "samping"
        val dalamRumah = "dalamRumah"
        val status = "status"
        val date = "date"
    }
}