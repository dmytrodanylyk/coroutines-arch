package com.kotlin.arch.example

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView
import com.kotlin.arch.LiveDataChannel
import com.kotlin.arch.launchConsumeEach
import com.kotlin.arch.observeChannel
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.channels.consumeEach
import kotlinx.coroutines.experimental.launch

class NameActivity : AppCompatActivity() {

    private lateinit var viewModel: NameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(NameViewModel::class.java)

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val button = findViewById<Button>(R.id.button)

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        viewModel.currentName.observe(this, Observer<String> { text ->
            nameTextView.text = text
        })
        // Version with [LiveDataChannel]
        val channel: LiveDataChannel<String> = viewModel.currentName.observeChannel(lifecycleOwner = this)
        launch(UI) {
            channel.consumeEach { text ->
                nameTextView.text = text
            }
        }

        // Alternative version with [LiveDataChannel] and [ChannelsExt]
        viewModel.currentName.observeChannel(lifecycleOwner = this).launchConsumeEach(UI) { text ->
            nameTextView.text = text
        }

        // Update LiveData objects
        button.setOnClickListener { viewModel.currentName.value = "John Doe" }

    }
}
