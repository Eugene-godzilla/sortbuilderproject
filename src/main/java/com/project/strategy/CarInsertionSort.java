package com.project.strategy;
import com.project.model.Car;
import java.util.Comparator;
import java.util.List;

public class CarInsertionSort {
    /**
     * Сортирует список автомобилей используя алгоритм Insertion Sort
     * @param cars список автомобилей для сортировки
     * @param comparator компаратор для определения порядка сортировки
     */
    public static void sort(List<Car> cars, Comparator<Car> comparator) {
        if (cars == null || comparator == null) {
            throw new IllegalArgumentException("List and comparator cannot be null");
        }

        int n = cars.size();
        for (int i = 1; i < n; i++) {
            Car key = cars.get(i);
            int j = i - 1;

            // Перемещаем элементы cars[0..i-1], которые больше key,
            // на одну позицию вперед
            while (j >= 0 && comparator.compare(cars.get(j), key) > 0) {
                cars.set(j + 1, cars.get(j));
                j = j - 1;
            }
            cars.set(j + 1, key);
        }
    }

}
