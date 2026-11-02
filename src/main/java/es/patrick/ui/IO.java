package es.patrick.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class IO {
    private static final BufferedReader READER = new BufferedReader(new InputStreamReader(System.in));

    private IO() {
    }

    public static void println(Object value) {
        System.out.println(value);
    }

    public static String readln() {
        try {
            return READER.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("Error leyendo la entrada del usuario", e);
        }
    }
}
