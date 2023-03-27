package com.example.tabletopapplication.presentationlayer.models.Timer

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopapplication.presentationlayer.models.Note.Note

@Dao
interface TimerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(timer:Timer)

    @Delete
    fun delete(timer:Timer)

    @Update
    fun update(timer:Timer)

    @Query("Select * from timerTable order by id ASC")
    fun getAllTimer(): LiveData<List<Timer>>

    @Query("Select * from timerTable Where id=:id")
    fun getOneTimer(id:Int): LiveData<Timer>
}