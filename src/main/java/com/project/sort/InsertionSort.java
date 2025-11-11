package com.project.sort;
import com.project.model.Car;
import java.util.Comparator;
import java.util.List;

public class InsertionSort {
    /**
     * Сортирует список используя алгоритм Insertion Sort
     * @param list список для сортировки
     * @param comparator компаратор для определения порядка сортировки
     * @param <T> тип элементов в списке
     */
    public <T> void sort(List<T> list, Comparator<T> comparator) {
        if (list == null || comparator == null) {
            throw new IllegalArgumentException("List and comparator cannot be null");
        }

        int n = list.size();
        for (int i = 1; i < n; i++) {
            T key = list.get(i);
            int j = i - 1;

            // Перемещаем элементы list[0..i-1], которые больше key,
            // на одну позицию вперед
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j = j - 1;
            }
            list.set(j + 1, key);
        }
    }
}
