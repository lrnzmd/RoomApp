package com.example.roomapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.roomapp.db.Word
import com.example.roomapp.db.WordRepository
import com.example.roomapp.db.WordRoomDatabase
import kotlinx.coroutines.launch

// Class extends AndroidViewModel and requires application as a parameter.
class WordViewModel(application: Application) : AndroidViewModel(application) {

    // The ViewModel maintains a reference to the repository to get data.
    private val repository: WordRepository
    // LiveData gives us updated words when they change.
    val allWords: MutableLiveData<List<Word>> = MutableLiveData()

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository. 
        val wordsDao = WordRoomDatabase.getDatabase(application).wordDao()
        repository = WordRepository(wordsDao)
        updateWord()
    }
    
    private fun updateWord()= viewModelScope.launch { 
        allWords.postValue(repository.getWords())
    }

   
    fun insert(word: Word) =
        viewModelScope.launch {
      repository.insert(word)
            updateWord()
    }
    
    
}