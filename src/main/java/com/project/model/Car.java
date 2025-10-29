package com.project.model;


import java.util.Objects;

public class Car {

    private final String model;
    private final int power;
    private final int year;

    private Car(Builder builder) {
        this.model = builder.model;
        this.power = builder.power;
        this.year = builder.year;
    }


    public String getModel() {
        return model;
    }

    public int getPower() {
        return power;
    }

    public int getYear() {
        return year;
    }


    public static class Builder {
        private String model;
        private int power;
        private int year;

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setPower(int power) {
            this.power = power;
            return this;
        }

        public Builder setYear(int year) {
            this.year = year;
            return this;
        }

        public Car build() {
            validate();
            return new Car(this);
        }

        private void validate() {
            if (model == null || model.isBlank())
                throw new IllegalArgumentException("Model must not be empty");
            if (power <= 0)
                throw new IllegalArgumentException("Power must be positive");
            if (year < 1886 || year > 2100)
                throw new IllegalArgumentException("Year must be realistic");
        }
    }


    @Override
    public String toString() {
        return String.format("Car{model='%s', power=%d, year=%d}", model, power, year);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Car car)) return false;
        return power == car.power && year == car.year && Objects.equals(model, car.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, power, year);
    }
}
