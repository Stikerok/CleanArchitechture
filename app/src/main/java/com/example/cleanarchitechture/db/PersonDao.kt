package com.example.cleanarchitechture.db

import androidx.room.*
import com.example.cleanarchitechture.entity.Person
import io.reactivex.Observable
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(person: Person)

    @Delete
    fun delete(person: Person)

    @Query("SELECT * FROM Person")
    fun selectAll(): Flow<List<Person>>

    @Query("SELECT * FROM Person")
    fun selectAllRx(): Observable<List<Person>>
}