package com.example.statecodelab.start.state.examples

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.statecodelab.databinding.ActivityHelloCodelab1Binding

/*
* 简单常规VM用法
* An observable is any state object that provides a way for anyone to listen for changes to that state.
 For example, LiveData, StateFlow, Flow, and Observable are all observable.
* */

class HelloCodelabViewModel1: ViewModel() {

    // LiveData holds state which is observed by the UI
    // (state flows down from ViewModel)
    private val _name = MutableLiveData("")
    val name: LiveData<String> = _name

    // onNameChanged is an event we're defining that the UI can invoke
    // (events flow up from UI)
    fun onNameChanged(newName: String) {
        _name.value = newName
    }
}

class HelloCodeLabActivityWithViewModel1 : AppCompatActivity() {
    private val helloViewModel by viewModels<HelloCodelabViewModel1>()

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityHelloCodelab1Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        binding.textInput.doAfterTextChanged {
            helloViewModel.onNameChanged(it.toString())
        }

        helloViewModel.name.observe(this) { name ->
            binding.helloText.text = "Hello, $name"
        }
    }
}