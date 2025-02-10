package com.example.finaltaskmanager.model

import android.os.Parcel
import android.os.Parcelable

data class TaskModel(
    var taskId : String="",
    var taskTitle : String="",
    var tasklevel : String="",
    var taskDate : String="",
    var taskDescription : String="",


): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(taskId)
        parcel.writeString(taskTitle)
        parcel.writeString(tasklevel)
        parcel.writeString(taskDate)
        parcel.writeString(taskDescription)

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TaskModel> {
        override fun createFromParcel(parcel: Parcel): TaskModel {
            return TaskModel(parcel)
        }

        override fun newArray(size: Int): Array<TaskModel?> {
            return arrayOfNulls(size)
        }
    }
}