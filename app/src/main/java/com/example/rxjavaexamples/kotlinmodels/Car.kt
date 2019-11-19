package com.example.rxjavaexamples.kotlinmodels

class Car(var model: String, var year: String) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Car

        if (model != other.model) return false
        if (year != other.year) return false

        return true
    }

    override fun hashCode(): Int {
        var result = model.hashCode();
        result = 31 * result + year.hashCode();
        return result;
    }

    override fun toString():String{
        return "Car: $model - $year";
    }
}