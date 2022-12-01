package com.example.androidcourseproject.dataClasses

import android.os.Parcel
import android.os.Parcelable


data class SequenceStorage(
    private var id: Int,
    private var naturalSequenceValue: Int,
    private var fibonacciSupportSequenceValue: Int,
    private var fibonacciSequenceValue: Int,
    private var collatzSequenceValue: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    constructor(id: Int) : this(
        id,
        0,
        0,
        1,
        7
    )

    fun getNatural(): String {
        return this.naturalSequenceValue.toString()
    }

    fun getFibonacci(): String {
        return this.fibonacciSequenceValue.toString()
    }

    fun getCollatz(): String {
        return this.collatzSequenceValue.toString()
    }

    fun getId() : Int{
        return this.id
    }


    override fun toString(): String {
        return "Element $id, states: " +
                "$naturalSequenceValue, " +
                "$fibonacciSequenceValue, " +
                "$collatzSequenceValue"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(naturalSequenceValue)
        parcel.writeInt(fibonacciSupportSequenceValue)
        parcel.writeInt(fibonacciSequenceValue)
        parcel.writeInt(collatzSequenceValue)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SequenceStorage> {
        override fun createFromParcel(parcel: Parcel): SequenceStorage {
            return SequenceStorage(parcel)
        }

        override fun newArray(size: Int): Array<SequenceStorage?> {
            return arrayOfNulls(size)
        }
    }

    fun nextNatural() {
        this.naturalSequenceValue++
    }

    fun nextFibonacci() {
        val temp = this.fibonacciSupportSequenceValue + this.fibonacciSequenceValue
        this.fibonacciSupportSequenceValue = this.fibonacciSequenceValue
        this.fibonacciSequenceValue = temp
    }

    fun nextCollatz() {
        if (this.collatzSequenceValue % 2 == 0) {
            this.collatzSequenceValue = this.collatzSequenceValue / 2
        } else {
            this.collatzSequenceValue = this.collatzSequenceValue * 3 + 1
        }
    }

}
