package com.affluencesystems.testthis.room.repo

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import com.affluencesystems.testthis.room.dao.UserDao
import com.affluencesystems.testthis.room.models.User

class UserRepo(var userDao: UserDao) {
    val mSectionLive = MediatorLiveData<List<User>>()

    fun getUsers(): LiveData<List<User>> {
        val listLive: LiveData<List<User>> = this.userDao.getAllUsers()
        mSectionLive.addSource(listLive, object : Observer<List<User>> {
            /**
             * Called when the data is changed.
             * @param t  The new data
             */
            override fun onChanged(t: List<User>?) {
                if (t!!.size == 0) {
                    val nums = 1..5
                    for (i in nums) {
                        val user = User("name$i", "age$i")
                        AsyncInsert(userDao, user)
                    }
                } else {
                    mSectionLive.removeSource(listLive)
                    mSectionLive.value = t
                }
            }
        })
        return mSectionLive
    }

    fun insertUser(user: User) {
        userDao.insertUser(user)
    }

    fun deleteUser(user: User) = this.userDao.deleteUser(user)

    fun updateUser(user: User) = this.userDao.updateUser(user)

    class AsyncInsert(private var userDao: UserDao, user: User) : AsyncTask<Unit, Unit, Unit>() {
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
        override fun doInBackground(vararg params: Unit?): Unit {
            userDao.insertUser(user1)
        }

    }
}