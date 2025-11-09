package com.project.strategy;

import com.project.model.Car;
import com.project.sort.InsertionSort;

import java.util.List;

public class ContextSort {

    private CarComparatorStrategy carComparatorStrategy;
    private InsertionSort insertionSort = new InsertionSort();

    public void setCarComparatorStrategy(CarComparatorStrategy carComparatorStrategy) {

        this.carComparatorStrategy = carComparatorStrategy;
    }

    public void Sort(List<Car> cars) {

        if (carComparatorStrategy == null) {
            throw new IllegalArgumentException(
                    "Стратегия сортировки не задана для " + this.getClass()
                            + " воспользуйтесь методом setCarComparatorStrategy");
        }

        insertionSort.sort(cars, carComparatorStrategy);
    }
}
