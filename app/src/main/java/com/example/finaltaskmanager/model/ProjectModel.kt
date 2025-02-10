package com.example.finaltaskmanager.model

import android.os.Parcel
import android.os.Parcelable

class ProjectModel(
    var projectId: String="",
    val projectTitle: String="",
    val projectLevel: String="",
    val projectDate: String="",
    val projectDescription: String="",
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:""
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(projectId)
        parcel.writeString(projectTitle)
        parcel.writeString(projectLevel)
        parcel.writeString(projectDate)
        parcel.writeString(projectDescription)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ProjectModel> {
        override fun createFromParcel(parcel: Parcel): ProjectModel {
            return ProjectModel(parcel)
        }

        override fun newArray(size: Int): Array<ProjectModel?> {
            return arrayOfNulls(size)
        }
    }
}