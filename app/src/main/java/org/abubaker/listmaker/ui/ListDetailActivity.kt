package org.abubaker.listmaker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.abubaker.listmaker.MainActivity
import org.abubaker.listmaker.databinding.ActivityListDetailBinding
import org.abubaker.listmaker.model.TaskList

class ListDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListDetailBinding

    lateinit var list: TaskList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityListDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!

        title = list.name

        // Binding RecyclerView (from content_main.xml) using its #id assigned to the @include tag in the activity_main.xml
        binding.listItemsRecyclerview.layoutManager = LinearLayoutManager(this)


    }

}