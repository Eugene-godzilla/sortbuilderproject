package com.project.mainapp;

import com.project.model.Car;
import com.project.io.InputManager;
import com.project.io.OutputManager;
import com.project.Strategy.ContextSort;
import com.project.Strategy.ModelComparator;
import com.project.Strategy.PowerComparator;
import com.project.Strategy.YearComparator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Главный класс контроллера приложения.
 * Здесь происходит взаимодействие пользователя с системой через меню.
 * ВАЖНО: логика ввода, вывода, сортировки и стратегий вынесена в отдельные пакеты:
 * - пакет io — работа с вводом/выводом (Салават)
 * - пакет sort — реализация сортировки (Олег)
 * - пакет Strategy — реализация паттерна «Стратегия» для выбора компаратора (Иван)
 * - пакет model — модель данных Car и Builder (общая часть)
 * Класс Application не содержит бизнес-логики — только управляет потоком выполнения.
 */
public class Application {

    // Сканер для консольного ввода
    private final Scanner scanner = new Scanner(System.in);

    // Основной список автомобилей (актуальное состояние данных)
    private List<Car> cars = new ArrayList<>();

    // Контекст сортировки — используется паттерн «Стратегия»
    private final ContextSort contextSort = new ContextSort();

    /**
     * Главный метод запуска меню.
     * Управляет всем процессом приложения.
     */
    public void run() {
        boolean running = true;

        while (running) {
            // Главное меню приложения
            System.out.println("\n===== МЕНЮ =====");
            System.out.println("1. Ввести автомобили вручную");
            System.out.println("2. Сгенерировать случайные автомобили");
            System.out.println("3. Загрузить автомобили из файла");
            System.out.println("4. Сортировать автомобили");
            System.out.println("5. Вывести автомобили");
            System.out.println("6. Сохранить в файл");
            System.out.println("0. Выход");
            System.out.print("Выберите пункт: ");

            int choice = Integer.parseInt(scanner.nextLine());

            // Вызов подпрограмм по пунктам меню
            switch (choice) {
                case 1 -> inputManual();       // Ввод вручную (через InputManager)
                case 2 -> generateRandom();    // Генерация случайных авто
                case 3 -> loadFromFile();      // Загрузка из файла
                case 4 -> sortCars();          // Сортировка по выбранной стратегии
                case 5 -> printCars();         // Вывод на экран
                case 6 -> saveToFile();        // Сохранение в файл
                case 0 -> running = false;     // Завершение работы
                default -> System.out.println("Неверный выбор!");
            }
        }
    }

    /**
     * Ввод автомобилей вручную.
     * Использует InputManager.createManualList().
     * Данные валидируются на уровне билдера Car.
     */
    private void inputManual() {
        System.out.print("Введите количество автомобилей: ");
        int count = Integer.parseInt(scanner.nextLine());
        cars = InputManager.createManualList(count);
        System.out.println("Данные успешно введены!");
    }

    /**
     * Генерация случайного списка автомобилей.
     * Использует InputManager.createRandomList().
     */
    private void generateRandom() {
        System.out.print("Введите количество автомобилей для генерации: ");
        int count = Integer.parseInt(scanner.nextLine());
        cars = InputManager.createRandomList(count);
        System.out.println("Сгенерировано " + cars.size() + " автомобилей.");
    }

    /**
     * Загрузка автомобилей из файла.
     * Использует InputManager.createListFromFile().
     * Формат файла: Модель,Мощность,Год
     */
    private void loadFromFile() {
        System.out.print("Введите путь к файлу: ");
        String path = scanner.nextLine();
        cars = InputManager.createListFromFile(path);
        System.out.println("Загружено " + cars.size() + " автомобилей из файла.");
    }

    /**
     * Сортировка автомобилей по выбранному критерию.
     * Используется паттерн «Стратегия» — контекст ContextSort
     * и конкретные стратегии (ModelComparator, PowerComparator, YearComparator).
     */
    private void sortCars() {
        if (cars.isEmpty()) {
            System.out.println("Нет данных для сортировки!");
            return;
        }

        System.out.println("\nВыберите критерий сортировки:");
        System.out.println("1. По модели");
        System.out.println("2. По мощности");
        System.out.println("3. По году выпуска");
        System.out.print("Ваш выбор: ");
        int choice = Integer.parseInt(scanner.nextLine());

        // Установка стратегии сортировки
        switch (choice) {
            case 1 -> contextSort.setCarComparatorStrategy(new ModelComparator());
            case 2 -> contextSort.setCarComparatorStrategy(new PowerComparator());
            case 3 -> contextSort.setCarComparatorStrategy(new YearComparator());
            default -> {
                System.out.println("Неверный выбор!");
                return;
            }
        }

        // Вызов сортировки через контекст
        contextSort.Sort(cars);
        System.out.println("Сортировка выполнена успешно!");
    }

    /**
     * Вывод текущего списка автомобилей в консоль.
     * Данные берутся из текущего состояния списка cars.
     */
    private void printCars() {
        if (cars.isEmpty()) {
            System.out.println("Список автомобилей пуст.");
            return;
        }

        System.out.println("\n===== СПИСОК АВТОМОБИЛЕЙ =====");
        for (Car car : cars) {
            System.out.printf("Модель: %-10s | Мощность: %3d | Год: %d%n",
                    car.getModel(), car.getPower(), car.getYear());
        }
    }

    /**
     * Сохранение текущего списка автомобилей в файл.
     * Используется OutputManager.saveToFile().
     */
    private void saveToFile() {
        if (cars.isEmpty()) {
            System.out.println("Нет данных для сохранения!");
            return;
        }

        System.out.print("Введите путь для сохранения файла: ");
        String path = scanner.nextLine();
        OutputManager.saveToFile(cars, path);
        System.out.println("Данные успешно сохранены в файл " + path);
    }
}
