package com.kotlin.arch.example

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


class NameViewModel : ViewModel() {

    // Create a LiveData with a String
    var currentName: MutableLiveData<String> = MutableLiveData()

    // Rest of the ViewModel...
}