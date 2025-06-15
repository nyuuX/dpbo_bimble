package dpbo.bimble;

import java.io.Console;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MissionUtil {

    private static Scanner scanner = new Scanner(System.in);

    public static String getString(String prompt) {
        System.out.print(prompt + ": ");
        return scanner.nextLine();
    }

    public static int getInt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                int value = scanner.nextInt();
                scanner.nextLine();
                return value;
            } catch (InputMismatchException e) {
                System.out.println("Input tidak valid. Harap masukkan angka bulat.");
                scanner.nextLine();
            }
        }
    }

    public static String getPassword(String prompt) {
        Console console = System.console();
        if (console != null) {
            char[] passwordArray = console.readPassword(prompt + ": ");
            return new String(passwordArray);
        } else {
            System.out.println("Peringatan: Console tidak tersedia. Password akan terlihat saat diketik.");
            return getString(prompt + " (terlihat)");
        }
    }

    public static void closeScanner() {
        if (scanner != null) {
            scanner.close();
        }
    }
}