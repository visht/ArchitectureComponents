package com.affluencesystems.testthis.ui

//import com.affluencesystems.testthis.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.affluencesystems.testthis.R
import com.affluencesystems.testthis.adapters.MainAdapter
import com.affluencesystems.testthis.databinding.ActivityMainBinding
import com.affluencesystems.testthis.room.models.User
import com.affluencesystems.testthis.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activtyMainBinding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private var listUsers: ArrayList<User> = ArrayList<User>()
    private lateinit var liveData: LiveData<List<User>>
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        activtyMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        activtyMainBinding.lifecycleOwner = this
        activtyMainBinding.mainviewmodel = mainViewModel
        activtyMainBinding.executePendingBindings()

        generaterandomUsers()

        liveData = mainViewModel.getAllUsers()
        liveData.observe(this, object : Observer<List<User>> {
            /**
             * Called when the data is changed.
             * @param t  The new data
             */
            override fun onChanged(t: List<User>?) {
                    mainAdapter.setData(t)
            }
        })

        activtyMainBinding.fab.setOnClickListener(object : View.OnClickListener {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            override fun onClick(v: View?) {
                listUsers.add(
                    User(
                        "Arvind " + java.util.Random().nextInt(),
                        "${java.util.Random().nextInt()}"
                    )
                )
                mainAdapter.setData(listUsers)
            }
        })
    }

    private fun generaterandomUsers() {
        var nums = 1..5
        for (i in nums) {
            val user = User("name$i", "age$i")
            listUsers.add(user)
            mainViewModel.setdataToModel(user)
        }
        mainAdapter = MainAdapter(baseContext, MainActivity@ listUsers)
        var mgr: RecyclerView.LayoutManager =
            LinearLayoutManager(baseContext, RecyclerView.VERTICAL, false)
        activtyMainBinding.recycler.layoutManager = mgr
        activtyMainBinding.recycler.adapter = mainAdapter
    }

}
