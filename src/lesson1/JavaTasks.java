package lesson1;

import kotlin.NotImplementedError;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class JavaTasks {
    /**
     * Сортировка времён
     *
     * Простая
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС AM/PM,
     * каждый на отдельной строке. См. статью википедии "12-часовой формат времени".
     *
     * Пример:
     *
     * 01:15:19 PM
     * 07:26:57 AM
     * 10:00:03 AM
     * 07:56:14 PM
     * 01:15:19 PM
     * 12:40:31 AM
     *
     * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
     * сохраняя формат ЧЧ:ММ:СС AM/PM. Одинаковые моменты времени выводить друг за другом. Пример:
     *
     * 12:40:31 AM
     * 07:26:57 AM
     * 10:00:03 AM
     * 01:15:19 PM
     * 01:15:19 PM
     * 07:56:14 PM
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortTimes(String inputName, String outputName) {
        Pattern reg = Pattern.compile("\\d{2}:\\d{2}:\\d{2}\\sAM|\\d{2}:\\d{2}:\\d{2}\\sPM");
        ArrayList<String> listAM = new ArrayList<>();                                       // Создаем 2 листа чтобы разделить сразу АМ и РМ
        ArrayList<String> listPM = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputName));
            String s;
            while ((s = reader.readLine()) != null) {
                if (!s.matches(String.valueOf(reg))) throw new Exception();
                if (s.contains("AM")) {
                    listAM.add(s);
                } else {                                            // если строка содержит АМ добавляем в лист АМ
                    listPM.add(s);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String[] resAM = new String[listAM.size()];                 // Создаем массив чтобы отсортировать для АМ и РМ и отправляем на сортировку
        for (int i = 0; i < resAM.length; i++) {
            resAM[i] = listAM.get(i);
        }
        mySortTime(resAM);

        String[] resPM = new String[listPM.size()];
        for (int i = 0; i < resPM.length; i++) {
            resPM[i] = listPM.get(i);
        }
        mySortTime(resPM);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
            for (String s : resAM) {
                writer.write(s + "\n");
            }                                   // Записываем сначала отсортированный АМ потом РМ
            for (String s : resPM) {
                writer.write(s + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static public void mySortTime(String[] arr) { // Сортировка
        boolean end = false;
        while (!end) {     // Исаользуем пузырьковую сортировку сравниваем отдельно часы потом минуты и секунды
            end = true;
            for (int i = 0; i < arr.length - 1; i++) {
                if ((Integer.parseInt(arr[i].substring(0, 2)) != 12) && (Integer.parseInt(arr[i + 1].substring(0, 2)) != 12)) { // если часы не равны 12 то
                    if ((Integer.parseInt(arr[i].substring(0, 2)) > (Integer.parseInt(arr[i + 1].substring(0, 2))))) {      // сравниваем часы минуты секунды
                        String sub = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = sub;
                        end = false;
                    } else if (Integer.parseInt(arr[i].substring(0, 2)) == (Integer.parseInt(arr[i + 1].substring(0, 2)))) {
                        if (Integer.parseInt(arr[i].substring(3, 5)) > (Integer.parseInt(arr[i + 1].substring(3, 5)))) {
                            String sub = arr[i];
                            arr[i] = arr[i + 1];
                            arr[i + 1] = sub;
                            end = false;
                        } else if (Integer.parseInt(arr[i].substring(3, 5)) == (Integer.parseInt(arr[i + 1].substring(3, 5)))) {
                            if (Integer.parseInt(arr[i].substring(6, 8)) > (Integer.parseInt(arr[i + 1].substring(6, 8)))) {
                                String sub = arr[i];
                                arr[i] = arr[i + 1];
                                arr[i + 1] = sub;
                                end = false;
                            }
                        }
                    }
                } else {
                    if (Integer.parseInt(arr[i].substring(0, 2)) == (Integer.parseInt(arr[i + 1].substring(0, 2)))) { // если обе 12 то сравниваем минуты секунды
                        if (Integer.parseInt(arr[i].substring(3, 5)) > (Integer.parseInt(arr[i + 1].substring(3, 5)))) {
                            String sub = arr[i];
                            arr[i] = arr[i + 1];
                            arr[i + 1] = sub;
                            end = false;
                        } else if (Integer.parseInt(arr[i].substring(3, 5)) == (Integer.parseInt(arr[i + 1].substring(3, 5)))) {
                            if (Integer.parseInt(arr[i].substring(6, 8)) > (Integer.parseInt(arr[i + 1].substring(6, 8)))) {
                                String sub = arr[i];
                                arr[i] = arr[i + 1];
                                arr[i + 1] = sub;
                                end = false;
                            }
                        }
                    } else if (Integer.parseInt(arr[i + 1].substring(0, 2)) == 12) { // если только одна 12 то ее пишем вперед
                        String sub = arr[i];
                        arr[i] = arr[i + 1];
                        arr[i + 1] = sub;
                        end = false;
                    }
                }
            }
        }
    }

    /**
     * Сортировка адресов
     *
     * Средняя
     *
     * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
     * где они прописаны. Пример:
     *
     * Петров Иван - Железнодорожная 3
     * Сидоров Петр - Садовая 5
     * Иванов Алексей - Железнодорожная 7
     * Сидорова Мария - Садовая 5
     * Иванов Михаил - Железнодорожная 7
     *
     * Людей в городе может быть до миллиона.
     *
     * Вывести записи в выходной файл outputName,
     * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
     * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
     *
     * Железнодорожная 3 - Петров Иван
     * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
     * Садовая 5 - Сидоров Петр, Сидорова Мария
     *
     * В случае обнаружения неверного формата файла бросить любое исключение.
     */
    static public void sortAddresses(String inputName, String outputName) {
        Pattern reg = Pattern.compile("[а-яА-Я]*\\s[а-яА-Я]*\\s-\\s.*\\s\\d*");// Для обнаружения неверного формата
        SortedMap<String, String> mapForWrite = new TreeMap<>(); // Создаем Мар сортирующийся по ключу

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputName));
            String s;
            while ((s = reader.readLine()) != null) {
                if (!s.matches(String.valueOf(reg))) throw new IOException(); // Если не соответствует формату выбрасываем любое исключение
                String[] arrNamesAddress = s.split(" - ");
                if (!mapForWrite.containsKey(arrNamesAddress[1])) { // Если полученный ключ не содержится в мапе, то запишем значение как есть
                    mapForWrite.put(arrNamesAddress[1], arrNamesAddress[0]);
                } else {                                            // Иначе добавим уже существующее значение через запятую
                    StringBuilder name = new StringBuilder();
                    String[] sortLine = mapForWrite.get(arrNamesAddress[1]).split(", "); // Сначала разделим все значения по ключу
                    SortedSet<String> sortVal = new TreeSet<>(Arrays.asList(sortLine)); // Чтобы отсортировать по оалфавиту
                    sortVal.add(arrNamesAddress[0]);                                 // И добавим новое значение
                    for (String str : sortVal) {
                        name.append(str).append(", ");                  // Добавим запятую между значениями и запишем в одну строку
                    }
                    mapForWrite.put(arrNamesAddress[1], name.substring(0, name.length() - 2));// Запишем отсортированную
                                                                                            // строку в итоговую мапу отрежем ненужную запятую
                }
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
            for (Map.Entry<String, String> entry : mapForWrite.entrySet()) {
                writer.write(entry.getKey() + " - " + entry.getValue() + "\n"); // Запишем в файл ключ и значение через тире
            }
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Сортировка температур
     *
     * Средняя
     * (Модифицированная задача с сайта acmp.ru)
     *
     * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
     * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
     * Например:
     *
     * 24.7
     * -12.6
     * 121.3
     * -98.4
     * 99.5
     * -12.6
     * 11.0
     *
     * Количество строк в файле может достигать ста миллионов.
     * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
     * Повторяющиеся строки сохранить. Например:
     *
     * -98.4
     * -12.6
     * -12.6
     * 11.0
     * 24.7
     * 99.5
     * 121.3
     */
    static public void sortTemperatures(String inputName, String outputName) {
        ArrayList<Double> listTemp = new ArrayList<>(); // Создаем лист для добавления значений
        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputName));
            String s;
            while ((s = reader.readLine()) != null) {
                Double d = Double.parseDouble(s);
                listTemp.add(d);                    // Преобразуем каждую строку в Double и добавляем в лист
            }
            reader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        double[] wrTemp = new double[listTemp.size()];
        for (int i = 0; i < listTemp.size(); i++) {
            wrTemp[i] = listTemp.get(i);            // Записываем все значения в массив чтобы отсортировать
        }
        Arrays.sort(wrTemp);                        // Сортируем

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputName));
            for (double d : wrTemp) {
                writer.write(d + "\n");         // Записываем в файл
            }
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Сортировка последовательности
     *
     * Средняя
     * (Задача взята с сайта acmp.ru)
     *
     * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
     *
     * 1
     * 2
     * 3
     * 2
     * 3
     * 1
     * 2
     *
     * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
     * а если таких чисел несколько, то найти минимальное из них,
     * и после этого переместить все такие числа в конец заданной последовательности.
     * Порядок расположения остальных чисел должен остаться без изменения.
     *
     * 1
     * 3
     * 3
     * 1
     * 2
     * 2
     * 2
     */
    static public void sortSequence(String inputName, String outputName) {
        throw new NotImplementedError();
    }

    /**
     * Соединить два отсортированных массива в один
     *
     * Простая
     *
     * Задан отсортированный массив first и второй массив second,
     * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
     * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
     *
     * first = [4 9 15 20 28]
     * second = [null null null null null 1 3 9 13 18 23]
     *
     * Результат: second = [1 3 4 9 9 13 15 20 23 28]
     */
    static <T extends Comparable<T>> void mergeArrays(T[] first, T[] second) {
        int ind = 0;        // Переменная для контроля записи значений в итоговый массив
        for (T t : first) {                             // Цикл для прохода по первому массиву
            for (int j = ind; j < second.length; j++) {  // Цикл для прохода по второму массиву
                if (second[j] != null) {                // Проверяем что значение из второго массива не null
                    int res = t.compareTo(second[j]);   // Если результат сравнения 1 то значение из первого массива больше
                    if (res > 0) {                      // т.к. массивы отсортированны значение второго массива будет
                        second[ind] = second[j];        // самым минимумом из обоих запишем его в начало и перезапишем на null
                        second[j] = null;
                        ind++;
                    } else {
                        second[ind] = t;                // Если результат сравнения 0 или -1 то мы просто записываем значение
                        ind++;                          // первого массива вместо null
                        break;
                    }
                }
            }
        }
    }
}
