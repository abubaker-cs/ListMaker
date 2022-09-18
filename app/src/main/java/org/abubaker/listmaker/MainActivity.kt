package org.abubaker.listmaker

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.abubaker.listmaker.adapter.ListSelectionRecyclerViewAdapter
import org.abubaker.listmaker.databinding.ActivityMainBinding
import org.abubaker.listmaker.model.ListDataManager
import org.abubaker.listmaker.model.TaskList
import org.abubaker.listmaker.ui.ListDetailActivity

class MainActivity : AppCompatActivity(),
    ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener {

    private lateinit var binding: ActivityMainBinding

    // This will be used to initialize storing data using SharedPreferences
    private val listDataManager: ListDataManager = ListDataManager(this)

    companion object {
        val INTENT_LIST_KEY = "list"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.fab.setOnClickListener { _ ->
            showCreateListDialog()
        }

        // Refreshing RecyclerView on page load
        val lists = listDataManager.readLists()

        // Binding RecyclerView (from content_main.xml) using its #id assigned to the @include tag in the activity_main.xml
        binding.contentMain.listsRecyclerview.layoutManager = LinearLayoutManager(this)

        // We are passing "lists" containing reference to the records of existing data
        binding.contentMain.listsRecyclerview.adapter =
            ListSelectionRecyclerViewAdapter(lists, this)

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

    // + Dialog
    private fun showCreateListDialog() {

        val dialogTitle = getString(R.string.name_of_list)
        val positiveButtonTitle = getString(R.string.create_list)

        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(dialogTitle)
        builder.setView(listTitleEditText)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->

            // We are creating an Empty list, TITLED from the EditText
            val list = TaskList(listTitleEditText.text.toString())

            // Now we are storing the new list
            listDataManager.saveList(list)

            // Refresh RecyclerView
            val recyclerAdapter =
                binding.contentMain.listsRecyclerview.adapter as ListSelectionRecyclerViewAdapter

            //
            recyclerAdapter.addList(list)

            dialog.dismiss()

            // Pass the "list" to the ListDetailActivity
            showListDetail(list)
        }

        builder.create().show()

    }

    private fun showListDetail(list: TaskList) {

        val listDetailIntent = Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        startActivity(listDetailIntent)

    }

    override fun listItemClicked(list: TaskList) {
        showListDetail(list)
    }


}