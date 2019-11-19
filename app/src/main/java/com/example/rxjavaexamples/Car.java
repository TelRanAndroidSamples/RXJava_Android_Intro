package com.example.rxjavaexamples;

import java.util.Objects;

public class Car {
    private String model;
    private String year;

    public Car(String model, String year) {
        this.model = model;
        this.year = year;
    }

    public Car() {
    }

    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return model.equals(car.model) &&
                year.equals(car.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, year);
    }
}
