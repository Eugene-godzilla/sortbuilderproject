package com.project.io;

import com.project.model.Car;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class OutputManager {

    /**
     * Сохраняет список автомобилей в файл.
     * Каждая строка файла содержит данные об одном автомобиле в формате:
     * Модель,Мощность,Год
     *
     * @param cars список объектов Car для сохранения
     * @param path путь к файлу для сохранения данных
     */
    public static void saveToFile(List<Car> cars, String path) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path))) {
            for (Car car : cars) {
                writer.printf("%s,%d,%d\n",
                        car.getModel(),
                        car.getPower(),
                        car.getYear());
            }
        } catch (IOException e) {
            System.out.println("Ошибка сохранения файла");
        }
    }
}
