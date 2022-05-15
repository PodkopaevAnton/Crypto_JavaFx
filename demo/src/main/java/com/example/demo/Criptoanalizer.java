package com.example.demo;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static java.lang.Character.toUpperCase;


public class Criptoanalizer {
    private static final String Alfabeth = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя.,\":-!? ";
    //public static List<String> static_result = new ArrayList<>();

        public static int fileDeshifr(String shifrFile,String finalFile, int method) throws IOException {
        if (method == 1) {//метод брутфорс
            System.out.println("Введите путь к файлу с зашифрованным текстом:");
            Path pathIn = Path.of(shifrFile);//считываю из консоли путь
            Path pathOut = Path.of(finalFile);//считываю из консоли путь
            List<String> shifrBruteText = Files.readAllLines(pathIn);//считываю из файла текст
            int brute_key = bruteforce(shifrBruteText);
            System.out.println("Ключ к тексту " + brute_key);
            System.out.println("Дешифруем текст с этим ключом");
            List<String> bruteText = deshifr(shifrBruteText, brute_key);

            Iterable<String> iterable = bruteText;//зашифрованные строки  в итератор для записи в файл
            System.out.println("Записываю в файл");
            Files.write(pathOut, iterable);
            System.out.println("файл создан");

            System.out.println("Выводим в консоль:");
            for (String str : bruteText) {
                System.out.println(str);//вывожу в консоль
            }
            return brute_key;
        } else if (method == 2) {//метод статистического анализа
            System.out.println("Введите путь к файлу с зашифрованным текстом:");
            Path pathIn2 = Path.of(shifrFile);//считываю из консоли путь
            Path pathOut2 = Path.of(finalFile);//считываю из консоли путь
            List<String> shifrStatText = Files.readAllLines(pathIn2);//считываю из файла текст
            int statist_key = statAnalise(shifrStatText);
            System.out.println("ключ = " + statist_key);
            System.out.println("Дешифруем текст с этим ключом");
            List<String> bruteText = deshifr(shifrStatText, statist_key);

            Iterable<String> iterable = bruteText;//зашифрованные строки  в итератор для записи в файл
            System.out.println("Записываю в файл");
            Files.write(pathOut2, iterable);
            System.out.println("файл создан");

            System.out.println("Выводим в консоль:");
            for (String str : bruteText) {
                System.out.println(str);//вывожу в консоль
            }
            return statist_key;
        }
        return 0;
    }

        public static void fileShifr(String beginFile, int key, String finalFile) throws IOException {

            Path path = Path.of(beginFile);//считываю из консоли путь

            //System.out.println("Введите криптографический ключ:");
            int offset = key;//считываю задержку

            //System.out.println("Введите путь к выходному файлу с зашифрованным текстом:");
            Path pathOut = Path.of(finalFile);//считываю из консоли путь

            List<String> list = Files.readAllLines(path);//считываю из файла текст
            // шифрование
            //System.out.println("зашифровываю текст шифром Цезаря");
            List<String> shifrText = shifr(list, offset);//зашифрованный текст
            //создание файла с шифром
            Iterable<String> iterable = shifrText;//зашифрованные строки  в итератор для записи в файл
            System.out.println("Записываю в файл");
            Files.write(pathOut, iterable);
            System.out.println("файл создан");

            System.out.println("Вывожу в консоль:");
            for (String str : shifrText) {
                System.out.println(str);//вывожу в консоль
            }
        }

        //шифрование
        public static List<String> shifr (List < String > list,int offset){
            List<String> shifr = new ArrayList<>();
            int offset_final = 0;
            //ограничение диапазона ключа
            if(Math.abs(offset) >= Alfabeth.length()){
                offset_final = Math.abs(offset) - (Alfabeth.length()) * (offset / (Alfabeth.length()));
            }else{
                if(offset < 0){
                    offset_final = offset + Alfabeth.length();
                }
                else{
                    offset_final = offset;
                }
            }
            //
            for (String text : list) {  //шифр по каждой строке
                StringBuilder builder = new StringBuilder();
                int summ = 0;
                for (int i = 0; i < text.length(); i++) { //по длине строки
                    char text_ch = text.charAt(i); // беру символ из строки
                    for (int j = 0; j < Alfabeth.length(); j++) { //по длине алфавита
                        char k = Alfabeth.charAt(j); // беру символ из алфавита
                        if (text_ch == k) { // если равен, то беру символ из алфавита + оффсет
                            summ = j + offset_final;
                            if (summ > (Alfabeth.length() - 1)) { //зацикливаю по алфавиту
                                summ = summ - Alfabeth.length();
                            }
                            char new_k = Alfabeth.charAt(summ);//беру зашифрованный символ
                            builder.append(new_k);//добавляю
                            continue;
                        }
                        if (text_ch == toUpperCase(k)) { //проверяю на заглавную букву
                            summ = j + offset_final;
                            if (summ > (Alfabeth.length() - 1)) {
                                summ = summ - Alfabeth.length();
                            }
                            char new_k = toUpperCase(Alfabeth.charAt(summ));
                            builder.append(new_k);
                        }
                    }
                }
                String shifr1 = builder.toString();
                //System.out.println(shifr1);
                shifr.add(shifr1);
            }
            return shifr;
        }

        //дешифрование
        public static List<String> deshifr (List < String > list,int offset){
            List<String> deshifr = new ArrayList<>();
            for (String text : list) {
                int summ1 = 0;
                StringBuilder builder1 = new StringBuilder();
                for (int i = 0; i < text.length(); i++) {
                    char untext_ch = text.charAt(i);
                    for (int j = 0; j < Alfabeth.length(); j++) {
                        char k = Alfabeth.charAt(j);
                        if (untext_ch == k) {
                            summ1 = j - offset;
                            if (summ1 < 0) {
                                summ1 = summ1 + Alfabeth.length();
                            }
                            char new_k = Alfabeth.charAt(summ1);
                            builder1.append(new_k);
                            continue;
                        }
                        if (untext_ch == toUpperCase(k)) {
                            summ1 = j - offset;
                            if (summ1 < 0) {
                                summ1 = summ1 + Alfabeth.length();
                            }
                            char new_k = toUpperCase(Alfabeth.charAt(summ1));
                            builder1.append(new_k);
                        }
                    }
                }
                String rasshifr = builder1.toString();
                //System.out.println(rasshifr);
                deshifr.add(rasshifr);
            }
            return deshifr;
        }

        //brute force
        public static int bruteforce (List < String > shifrText) {
            int equal = 0;
            int length_text = 0;
            int[] key_offset = new int[Alfabeth.length()];
            int offset_final = 0; // финальный ключ
            int equal_final = 0; // максимальное количество признаков текста соотв этому ключу
            for (int offset = 0; offset < Alfabeth.length(); offset++) {
                List<String> deshifrText = deshifr(shifrText, offset);
                for (String text : deshifrText) {
                    length_text = length_text + text.length();
                    char[] array = text.toCharArray();
                    for (int i = 0; i < (array.length - 1); i = i + 1) {
                        if (((array[i] == ',') && (array[i + 1] == ' ')) || ((array[i] == '.') && (array[i + 1] == ' '))) {
                            equal = equal + 1;
                        }
                    }
                }
                key_offset[offset] = equal;
//            if(equal > 0){
//                System.out.println("кол-во символов в тексте " + length_text);
//                System.out.println("количество признаков = " + equal + " для ключа = " + offset);
//            }
                equal = 0;
                length_text = 0;
            }
            for (int i = 0; i < key_offset.length; i++) {
                if (key_offset[i] > equal_final) {
                    equal_final = key_offset[i];
                    offset_final = i;
                }
            }
            return offset_final;
        }

        //метод статического анализа
        public static int statAnalise (List < String > shifrStatText) {
            int equal_space = 0;//количество совпадений по пробелам
            int equal_o = 0;//количество совпадений по o
            int equal_e = 0;//количество совпадений по e
            int equal_a = 0;//количество совпадений по a
            int equal_i = 0;//количество совпадений по i
            int equal_n = 0;//количество совпадений по n
            int equal_t = 0;//количество совпадений по t
            int equal_s = 0;//количество совпадений по s
            int length_text = 0;//количество символов в тексте
            double[] vord_space = new double[Alfabeth.length()]; //массив вероятностей выпадения пробела для каждого ключа
            double[] vord_o = new double[Alfabeth.length()]; //буквы о
            double[] vord_e = new double[Alfabeth.length()]; //буквы е
            double[] vord_a = new double[Alfabeth.length()]; //буквы а
            double[] vord_i = new double[Alfabeth.length()]; //буквы и
            double[] vord_n = new double[Alfabeth.length()]; //буквы н
            double[] vord_t = new double[Alfabeth.length()]; //буквы т
            double[] vord_s = new double[Alfabeth.length()]; //буквы с
            int[] key_offset = new int[Alfabeth.length()];
            int offset_final = 0; // финальный ключ
            int equal_final = 0; // максимальное количество признаков текста соотв этому ключу
            for (int offset = 0; offset < Alfabeth.length(); offset++) {
                List<String> deshifrText = deshifr(shifrStatText, offset);
                for (String text : deshifrText) {
                    length_text = length_text + text.length();
                    char[] array = text.toCharArray();
                    for (int i = 0; i < (array.length); i = i + 1) {
                        if (array[i] == ' ') {
                            equal_space = equal_space + 1;
                        }
                        if (Character.toLowerCase(array[i]) == 'о') {
                            equal_o = equal_o + 1;
                        }
                        if (Character.toLowerCase(array[i]) == 'е') {
                            equal_e = equal_e + 1;
                        }
                        if (Character.toLowerCase(array[i]) == 'а') {
                            equal_a = equal_a + 1;
                        }
                        if (Character.toLowerCase(array[i]) == 'и') {
                            equal_i = equal_i + 1;
                        }
                        if (Character.toLowerCase(array[i]) == 'н') {
                            equal_n = equal_n + 1;
                        }
                        if (Character.toLowerCase(array[i]) == 'т') {
                            equal_t = equal_t + 1;
                        }
                        if (Character.toLowerCase(array[i]) == 'с') {
                            equal_s = equal_s + 1;
                        }
                    }
                }
                vord_space[offset] = (equal_space * 100) / length_text;
                vord_o[offset] = (equal_o * 100) / length_text;
                vord_e[offset] = (equal_e * 100) / length_text;
                vord_a[offset] = (equal_a * 100) / length_text;
                vord_i[offset] = (equal_i * 100) / length_text;
                vord_n[offset] = (equal_n * 100) / length_text;
                vord_t[offset] = (equal_t * 100) / length_text;
                vord_s[offset] = (equal_s * 100) / length_text;
//            key_offset[offset] = equal;
//            if(equal > 0){
//                System.out.println("кол-во символов в тексте " + length_text);
//                System.out.println("количество признаков = " + equal + " для ключа = " + offset);
//            }
                equal_space = 0;
                equal_o = 0;
                equal_e = 0;
                equal_a = 0;
                equal_i = 0;
                equal_n = 0;
                equal_t = 0;
                equal_s = 0;
                length_text = 0;
            }
//        System.out.println(Arrays.toString(vord_space));
//        System.out.println(Arrays.toString(vord_o));
//        System.out.println(Arrays.toString(vord_e));
//        System.out.println(Arrays.toString(vord_a));
//        System.out.println(Arrays.toString(vord_i));
//        System.out.println(Arrays.toString(vord_n));
//        System.out.println(Arrays.toString(vord_t));
//        System.out.println(Arrays.toString(vord_s));

            for (int i = 0; i < key_offset.length; i++) {
                double e = 1.5;
                int prizn = 0;
                if (Math.abs(vord_space[i] - 17.5) <= e) {
//                System.out.println("вероятность пробела " + vord_space[i]);
                    prizn = prizn + 1;
                }
                if (Math.abs(vord_o[i] - 9) <= e) {
//                System.out.println("вероятность буквы о " + vord_o[i]);
                    prizn = prizn + 1;
                }
                if ((Math.abs(vord_e[i] - 7.2) <= e)) {
//                System.out.println("вероятность буквы е " + vord_e[i]);
                    prizn = prizn + 1;
                }
                if ((Math.abs(vord_a[i] - 6.2) <= e)) {
//                System.out.println("вероятность буквы а " + vord_a[i]);
                    prizn = prizn + 1;
                }
                if ((Math.abs(vord_i[i] - 6.2) <= e)) {
//                System.out.println("вероятность буквы и " + vord_i[i]);
                    prizn = prizn + 1;
                }
                if ((Math.abs(vord_n[i] - 5.3) <= e)) {
//                System.out.println("вероятность буквы н " + vord_n[i]);
                    prizn = prizn + 1;
                }
                if ((Math.abs(vord_t[i] - 5.3) <= e)) {
//                System.out.println("вероятность буквы т " + vord_t[i]);
                    prizn = prizn + 1;
                }
                if ((Math.abs(vord_s[i] - 4.5) <= e)) {
//                System.out.println("вероятность буквы с " + vord_s[i]);
                    prizn = prizn + 1;
                }
//            System.out.println("количество признаков = " + prizn + " для " + i + " ключа");
//            System.out.println();
                if (prizn >= 4) {
//                    System.out.println("ключ " + i);
                    System.out.println("вероятность пробела " + vord_space[i]);//static_result.add("вероятность пробела " + vord_space[i]);
                    System.out.println("вероятность буквы о " + vord_o[i]);//static_result.add("вероятность буквы о " + vord_o[i]);
                    System.out.println("вероятность буквы е " + vord_e[i]);//static_result.add("вероятность буквы е " + vord_e[i]);
                    System.out.println("вероятность буквы а " + vord_a[i]);//static_result.add("вероятность буквы а " + vord_a[i]);
                    System.out.println("вероятность буквы и " + vord_i[i]);//static_result.add("вероятность буквы и " + vord_i[i]);
                    System.out.println("вероятность буквы н " + vord_n[i]);//static_result.add("вероятность буквы н " + vord_n[i]);
                    System.out.println("вероятность буквы т " + vord_t[i]);//static_result.add("вероятность буквы т " + vord_t[i]);
                    System.out.println("вероятность буквы с " + vord_s[i]);//static_result.add("вероятность буквы с " + vord_s[i]);
                    return i;
                }
            }
            return 0;
        }
}