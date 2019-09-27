package com.affluencesystems.testthis.viewmodels

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.affluencesystems.testthis.room.dao.UserDao
import com.affluencesystems.testthis.room.db.UserDb
import com.affluencesystems.testthis.room.models.User
import com.affluencesystems.testthis.room.repo.UserRepo

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var userRepo: UserRepo? = null
    private var userDao: UserDao

    init {
        userDao = UserDb.getDb(application.applicationContext).getUserDao()
        userRepo = UserRepo(userDao)
    }

    fun setdataToModel(userdata: User) {
        userRepo?.let { AsyncInsert(it, userdata) }
    }

    fun deleteDataFromModel(userdata: User) = userRepo!!.deleteUser(userdata)

    fun updateUser(userdata: User) = userRepo!!.updateUser(userdata)

    fun getAllUsers(): LiveData<List<User>> = userRepo!!.getUsers()

    class AsyncInsert(userRepo1: UserRepo, user: User) : AsyncTask<User, Unit, Unit>() {
        private var userRepo: UserRepo = userRepo1
        private var user1: User = user
        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to [.execute]
         * by the caller of this task.
         *
         * This method can call [.publishProgress] to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         *
         * @return A result, defined by the subclass of this task.
         *
         * @see .onPreExecute
         * @see .onPostExecute
         *
         * @see .publishProgress
         */
        override fun doInBackground(vararg params: User?): Unit {
            userRepo.insertUser(user1)
        }

    }
}