package com.project.io;

import com.project.model.Car;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class InputManager {

    // Константы для генерации массива данных
    private static final int MIN_POWER = 0;
    private static final int MAX_POWER = 300;
    private static final int MIN_YEAR = 1886;
    private static final int MAX_YEAR = 2025;
    private static final String[] MODELS = {"Audi", "BMV", "Ford", "Toyota", "Mercedes", "Honda",  "Volvo", "Scania", "Nissan"};

    /**
     * Создает массив автомобилей вручную через ввод данных с консоли.
     * @param length размер массива
     * @return заполненный массив объектов Car
     */
    public static Car[] createManualArray(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Размер массива должен быть положительным числом: " + length);
        }

        Car[] cars = new Car[length];
        Scanner sc = new Scanner(System.in);

        for (int i = 0; i < length; i++) {
            System.out.printf("%n--- Введите данные для машины %d ---%n", (i + 1));

            try {
                System.out.print("Модель (не может быть пустым): ");
                String model = sc.nextLine().trim();

                System.out.printf("Мощность (%d-%d): ", MIN_POWER, MAX_POWER);
                int power = Integer.parseInt(sc.nextLine().trim());

                System.out.printf("Год выпуска (%d-%d): ", MIN_YEAR, MAX_YEAR);
                int year = Integer.parseInt(sc.nextLine().trim());

                cars[i] = new Car.Builder().setModel(model).setPower(power).setYear(year).build();

            } catch (IllegalArgumentException  e) {
                System.out.printf("Ошибка валидации: %s. Повторите ввод.%n", e.getMessage());
                i--;
            }
        }

        return cars;
    }

    /**
     * Генерирует массив автомобилей со случайными характеристиками.
     * @param length размер массива
     * @return заполненный массив объектов Car
     */
    public static Car[] createRandomArray(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Размер массива должен быть положительным числом: " + length);
        }

        Car[] cars = new Car[length];
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            try {
                String model = MODELS[random.nextInt(MODELS.length)];
                int power = random.nextInt(MIN_POWER, MAX_POWER);
                int year = random.nextInt(MIN_YEAR, MAX_YEAR);

                cars[i] = new Car.Builder().setModel(model).setPower(power).setYear(year).build();

            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка генерации: " + e.getMessage());
            }
        }

        return cars;
    }

    /**
     * Создает массив автомобилей, читая данные из файла.
     * Формат файла: Модель, Мощность, Год (каждая строка - один автомобиль)
     * @param filePath путь к файлу для чтения данных
     * @return заполненный массив объектов Car
     */
    public static Car[] createArrayFromFile(String filePath) {
        List<Car> cars = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new File(filePath))) {
            int lineNumber = 0;

            while (fileScanner.hasNextLine()) {
                lineNumber++;
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                if (parts.length == 3) {
                    try {
                        Car car = new Car.Builder()
                                .setModel(parts[0].trim())
                                .setPower(Integer.parseInt(parts[1].trim()))
                                .setYear(Integer.parseInt(parts[2].trim()))
                                .build();
                        cars.add(car);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Ошибка в строке " + lineNumber + ": " + e.getMessage());
                    }
                } else {
                    System.out.println("Некорректный формат в строке " + lineNumber);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден: " + filePath);
        }

        return cars.toArray(new Car[0]);
    }

}
