package com.acs.urbannavigator.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite")
data class FavoriteItem(
    @PrimaryKey
    val uuid: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "imageUuid")
    val imageUuid: String,
    @ColumnInfo(name = "contentProviderUuid")
    val contentProviderUuid: String,
    @ColumnInfo(name = "type")
    val type: String
){
    constructor() : this("", "", "", "", "")
}