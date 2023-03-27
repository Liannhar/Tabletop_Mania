package com.example.tabletopapplication.presentationlayer.models.DIce

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.tabletopapplication.presentationlayer.models.Note.Note

@Dao
interface DiceDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(dice: Dice)

    @Delete
    fun delete(dice: Dice)

    @Update
    fun update(dice: Dice)

    @Query("Select * from diceTable order by id ASC")
    fun getAllDice(): LiveData<List<Dice>>

    @Query("Select * from diceTable Where id=:id")
    fun getOneDice(id:Int): LiveData<Dice>
}