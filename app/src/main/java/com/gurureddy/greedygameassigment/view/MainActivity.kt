package com.gurureddy.greedygameassigment.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.gurureddy.greedygameassigment.model.Tag
import com.google.android.material.chip.Chip
import com.gurureddy.greedygameassigment.R
import com.gurureddy.greedygameassigment.databinding.ActivityMainBinding
import com.gurureddy.greedygameassigment.extension.showToast
import com.gurureddy.greedygameassigment.utils.Constants.TAG
import com.gurureddy.greedygameassigment.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainActivityViewModel::class.java]

        initObserver()
        viewModel.fetchData()
    }

    private fun initObserver() {
        viewModel.apply {
            isLoading.observe(this@MainActivity, Observer { isLoading ->
                binding.pbLoader.visibility = if (isLoading) View.VISIBLE else View.GONE
            })

            errorMessage.observe(this@MainActivity) { errorMessage ->
                showToast(errorMessage)
            }

            tagList.observe(this@MainActivity) { tagList ->
                refreshTheList(tagList)
            }
        }
    }

    private fun refreshTheList(tagList: List<Tag>) {
        binding.apply {
            if (viewModel.isListExpanded) {
                toggleIcon.setImageResource(R.drawable.up)
            } else {
                toggleIcon.setImageResource(R.drawable.down)
            }
            chipGroup.removeAllViews()
        }
        for (i in tagList.indices) {
            if (i == 10 && !viewModel.isListExpanded) {
                break
            }
            val tag = tagList[i]
            val chip = Chip(this)
            chip.text = tag.name

            chip.setChipBackgroundColorResource(R.color.purple_200)

            chip.setOnClickListener {
                val intent = Intent(this, GenreDetailActivity::class.java)
                intent.putExtra(TAG, tag.name)
                startActivity(intent)
            }
            binding.chipGroup.addView(chip)
        }
    }

    fun buttonPressed(view: View) {
        viewModel.toggleList()
        viewModel.fetchData()
    }
}
