package org.abubaker.listmaker.model

import android.content.Context
import android.preference.PreferenceManager

class ListDataManager(private val context: Context) {

    fun saveList(list: TaskList) {

        // Mode: Read/Write
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context).edit()

        // Prepare DATA to be Stored
        sharedPreferences.putStringSet(
            list.name,
            list.tasks.toHashSet()
        )

        // Store the DATA (List)
        sharedPreferences.apply()

    }

    fun readLists(): ArrayList<TaskList> {

        // Mode: Read Only
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        // Get references of all records
        val sharedPreferencesContents = sharedPreferences.all

        // Store the data in an Array
        val taskLists = ArrayList<TaskList>()

        // Loop
        for (preferenceItem in sharedPreferencesContents) {
            val itemsHashSet = preferenceItem.value as HashSet<String>
            val list = TaskList(
                preferenceItem.key,
                ArrayList(itemsHashSet)
            )
            taskLists.add(list)
        }

        return taskLists

    }

}