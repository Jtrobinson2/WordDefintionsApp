/*
 * Copyright (C) 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.wordsapp

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.ActivityMainBinding

/**
 * Main Activity and entry point for the app. Displays a RecyclerView of letters.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    //boolean used for menu item button to toggle between layout managers
    private var isLinearLayoutManagerActive = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.recyclerView
        chooseLayout()

    }


    //method to determine which layout manager to use based on the users preference
    private fun chooseLayout() {
        if (isLinearLayoutManagerActive) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 4)
        }

        recyclerView.adapter = LetterAdapter()
    }


    //responsible for changing the icon based on which layout manger the user toggle in the menu item
    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null) return

        menuItem.icon = when (isLinearLayoutManagerActive) {
            true -> ContextCompat.getDrawable(this, R.drawable.ic_linear_layout)
            false -> ContextCompat.getDrawable(this, R.drawable.ic_grid_layout)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        //inflate your custom menu
        menuInflater.inflate(R.menu.layout_menu, menu)

        //find the layout button
        val layoutMenuButton = menu?.findItem(R.id.action_switch_layout)

        //set the icon of the button
        setIcon(layoutMenuButton)

        //returning true means you want the options menu to be created
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                //switch the linear layout to grid layout manager
                isLinearLayoutManagerActive = !isLinearLayoutManagerActive
                chooseLayout()
                setIcon(item)
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }
}
