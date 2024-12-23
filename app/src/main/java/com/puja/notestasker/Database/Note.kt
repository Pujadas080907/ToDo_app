package com.puja.notestasker.Database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity
data class Note(

    @PrimaryKey(autoGenerate = true)
    var id : Int=0,
    var title : String,
    var body : String,
    var createdAt : Date = Date(),
    var updateAt: Date? = null
)
