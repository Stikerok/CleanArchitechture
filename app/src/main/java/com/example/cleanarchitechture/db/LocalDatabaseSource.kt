package com.example.cleanarchitechture.db

import android.content.Context
import androidx.room.Room
import com.example.cleanarchitechture.domain.PersonRepository
import com.example.cleanarchitechture.entity.Person
import kotlinx.coroutines.flow.Flow

class LocalDatabaseSource (context: Context) : PersonRepository {
    private val db = Room.databaseBuilder(
        context, PersonDb::class.java, "Persons"
    ).build()
    override fun getPersons(): Flow<List<Person>> {
       return db.getPersonDao().selectAll()
    }
}