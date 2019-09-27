package com.affluencesystems.testthis.room.db

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.affluencesystems.testthis.room.dao.UserDao
import com.affluencesystems.testthis.room.models.User
import android.os.AsyncTask
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.annotation.NonNull
import android.icu.lang.UCharacter.GraphemeClusterBreak.V



@Database(entities = [User::class], version = 1)
abstract class UserDb : RoomDatabase() {

    abstract fun getUserDao(): UserDao


    /**
     * Populate the database in the background.
     * If you want to start with more words, just add them.
     */
   /* private class PopulateDbAsync internal constructor(db: UserDb) :
        AsyncTask<Void, Void, Void>() {

        private val mDao: UserDao
        internal var words = arrayOf("dolphin", "crocodile", "cobra")

        init {
            mDao = db.getUserDao()
        }

        override fun doInBackground(vararg params: Void): Void? {
//            mDao.deleteAll()
//
//            for (i in 0..words.size - 1) {
//                val word = Word(words[i])
//                mDao.insert(word)
//            }
            return null
        }
    }*/

    companion object {
        /**
         * Override the onOpen method to populate the database.
         * For this sample, we clear the database every time it is created or opened.
         */
        val sRoomDatabaseCallback = object : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
//                PopulateDbAsync(UserDb).execute()
            }
        }

        fun getDb(context: Context): UserDb = Room.databaseBuilder(context.applicationContext
        ,UserDb::class.java, "User.db")

            // Wipes and rebuilds instead of migrating if no Migration object.
            // Migration is not part of this codelab.
            .fallbackToDestructiveMigration()
            .addCallback(sRoomDatabaseCallback)
            .build()
    }

}