package ru.bmstu.lab1.task_19;

// Задача 19. Объединить два неубывающих массива за O(n + m).

import java.util.Objects;
import java.util.Scanner;
import ru.bmstu.lab1.common.ConsoleInput;

/** Слияние двух отсортированных массивов без изменения исходных данных. */
public final class SortedMerge {
    private SortedMerge() {
    }

    /**
     * Сливает два массива, каждый из которых уже отсортирован по неубыванию.
     *
     * @param first первый отсортированный массив
     * @param second второй отсортированный массив
     * @return новый отсортированный массив с сохранением повторов
     */
    public static int[] merge(int[] first, int[] second) {
        Objects.requireNonNull(first, "Первый массив не должен быть null");
        Objects.requireNonNull(second, "Второй массив не должен быть null");
        int[] result = new int[first.length + second.length];
        int firstIndex = 0;
        int secondIndex = 0;
        int resultIndex = 0;
        while (firstIndex < first.length && secondIndex < second.length) {
            if (first[firstIndex] <= second[secondIndex]) {
                result[resultIndex++] = first[firstIndex++];
            } else {
                result[resultIndex++] = second[secondIndex++];
            }
        }
        while (firstIndex < first.length) {
            result[resultIndex++] = first[firstIndex++];
        }
        while (secondIndex < second.length) {
            result[resultIndex++] = second[secondIndex++];
        }
        return result;
    }

    /**
     * Читает два блока «длина, элементы» и выводит результат слияния.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] first = ConsoleInput.readArray(scanner);
        int[] second = ConsoleInput.readArray(scanner);
        ConsoleInput.printArray(merge(first, second));
    }
}
