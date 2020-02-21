package com.jetbrains.handson.mpp.todoapplication

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewToDoItemFragment.NewToDoDialogListener {

    private var todoListItems = ArrayList<String>()
    private var listView: ListView? = null
    private var listAdapter: ArrayAdapter<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {

            createTask()

            listView = findViewById(R.id.ToDoList)

            populateListView()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun createTask() {

        val newFragment = NewToDoItemFragment.newInstance(R.string.new_todo_item)

        newFragment.show(supportFragmentManager, "new 2Do item")

    }

    override fun onAddClick(dialog: DialogFragment, item:String) {

        todoListItems.add(item)

        listAdapter?.notifyDataSetChanged()

        Snackbar.make(fab, "Task Added Successfully!", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    override fun onCancelClick(dialog: DialogFragment) {

//        Do nothing
    }

    private fun populateListView() {

        listAdapter = ArrayAdapter(this, R.layout.custom_layout, R.id.ToDoItem, todoListItems)
        listView?.adapter = listAdapter
    }
}
