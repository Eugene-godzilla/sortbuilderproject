package com.project.Strategy;

import com.project.model.Car;

public class YearComparator implements InsertionSortCarComparatorStrategy {

    @Override
    public int compare(Car car1, Car car2) {

        return Integer.compare(car1.getYear(), car2.getYear());
    }
}
