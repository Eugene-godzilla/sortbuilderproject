package com.project;

import com.project.model.Car;
import com.project.model.CarComparators;
import com.project.model.CarTest;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Application {

    private final Scanner scanner = new Scanner(System.in);
    private List<Car> cars = new ArrayList<>();

    public void run() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== МЕНЮ =====");
            System.out.println("1. Загрузить данные");
            System.out.println("2. Отсортировать данные");
            System.out.println("3. Вывести результаты на экран");
            System.out.println("4. Сохранить результаты в файл");
            System.out.println("0. Выход");
            System.out.print("Выберите пункт: ");

            int choice = getInt();
            switch (choice) {
                case 1 -> loadDataMenu();
                case 2 -> sortMenu();
                case 3 -> printCars();
                case 4 -> saveToFile();
                case 0 -> {
                    System.out.println("Выход из программы...");
                    running = false;
                }
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    private void loadDataMenu() {
        System.out.println("\nВыберите способ загрузки:");
        System.out.println("1. Ввести вручную");
        System.out.println("2. Автоматически (из CarTest)");
        System.out.println("3. Из файла");
        System.out.print("Ваш выбор: ");

        int method = getInt();
        switch (method) {
            case 1 -> loadManual();
            case 2 -> loadAuto();
            case 3 -> loadFromFile();
            default -> System.out.println("Неверный вариант!");
        }
    }

    private void loadManual() {
        cars.clear();
        System.out.print("Введите количество машин: ");
        int n = getInt();

        for (int i = 0; i < n; i++) {
            System.out.println("\nМашина №" + (i + 1));

            // ввод модели
            String model;
            while (true) {
                System.out.print("Модель: ");
                model = scanner.nextLine().trim();
                if (!model.isEmpty()) break;
                System.out.println("Модель не может быть безимянной!");
            }

            // ввод мощности
            int power;
            while (true) {
                System.out.print("Мощность: ");
                try {
                    power = Integer.parseInt(scanner.nextLine());
                    if (power > 0) break;
                    System.out.println("Мощность должна быть положительной!");
                } catch (NumberFormatException e) {
                    System.out.println("Введите число!");
                }
            }

            // ввод года
            int year;
            while (true) {
                System.out.print("Год выпуска: ");
                try {
                    year = Integer.parseInt(scanner.nextLine());
                    if (year >= 1886 && year <= 2100) break;
                    System.out.println("Нереалистичный год! (допустимо 1886–2100)");
                } catch (NumberFormatException e) {
                    System.out.println("Введите число!");
                }
            }

            // создание объекта
            cars.add(new Car.Builder()
                    .setModel(model)
                    .setPower(power)
                    .setYear(year)
                    .build());
        }

        System.out.println("\nДанные успешно введены! Добавлено машин: " + cars.size());
    }

    private void loadAuto() {
        cars = CarTest.getCars();
        System.out.println("Автоматически добавлены тестовые данные из CarTest.");
    }

    private void loadFromFile() {
        System.out.print("Введите имя файла (по умолчанию cars_input.txt): ");
        String fileName = scanner.nextLine().trim();
        if (fileName.isEmpty()) fileName = "cars_input.txt";

        List<Car> loadedCars = new ArrayList<>();

        try (Scanner fileScanner = new Scanner(new java.io.File(fileName))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                if (parts.length != 3) {
                    System.out.println("Неверный формат строки: " + line);
                    continue;
                }

                try {
                    String model = parts[0].trim();
                    int power = Integer.parseInt(parts[1].trim());
                    int year = Integer.parseInt(parts[2].trim());

                    loadedCars.add(new Car.Builder()
                            .setModel(model)
                            .setPower(power)
                            .setYear(year)
                            .build());
                } catch (NumberFormatException e) {
                    System.out.println("Ошибка чтения данных в строке: " + line);
                }
            }

            cars = loadedCars;
            System.out.println("Успешно Загружено " + cars.size() + " машин из файла " + fileName);

        } catch (Exception e) {
            System.out.println("Ошибка при открытии файла: " + e.getMessage());
        }
    }

    private void sortMenu() {
        if (cars.isEmpty()) {
            System.out.println("Нет данных для сортировки!");
            return;
        }

        System.out.println("\nВыберите критерий сортировки:");
        System.out.println("1. По модели");
        System.out.println("2. По мощности");
        System.out.println("3. По году выпуска");
        System.out.print("Ваш выбор: ");

        int sortChoice = getInt();

        Comparator<Car> comparator = switch (sortChoice) {
            case 1 -> CarComparators.BY_MODEL;
            case 2 -> CarComparators.BY_POWER;
            case 3 -> CarComparators.BY_YEAR;
            default -> null;
        };

        if (comparator == null) {
            System.out.println("Неверный выбор!");
            return;
        }

        insertionSort(cars, comparator);
        System.out.println("Сортировка успешно выполнена!");
    }
    private <T> void insertionSort(List<T> list, Comparator<? super T> comparator) {
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;
            while (j >= 0 && comparator.compare(list.get(j), key) > 0) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
    }

    private void printCars() {
        if (cars.isEmpty()) {
            System.out.println("Нет данных для вывода!");
            return;
        }

        System.out.println("\n=== СПИСОК МАШИН ===");
        System.out.printf("%-10s | %-6s | %-4s%n", "Модель", "Мощн.", "Год");
        System.out.println("-----------------------------");
        for (Car car : cars) {
            System.out.printf("%-10s | %-6d | %-4d%n",
                    car.getModel(), car.getPower(), car.getYear());
        }
    }

    private void saveToFile() {
        if (cars.isEmpty()) {
            System.out.println("Нет данных для сохранения!");
            return;
        }
        try (PrintWriter writer = new PrintWriter("cars_output.txt")) {
            cars.forEach(writer::println);
            System.out.println("Данные сохранены в файл cars_output.txt");
        } catch (Exception e) {
            System.out.println("Ошибка при записи в файл: " + e.getMessage());
        }
    }

    private int getInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Введите число: ");
            }
        }
    }
}
