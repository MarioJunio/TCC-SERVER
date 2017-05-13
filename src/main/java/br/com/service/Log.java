package br.com.service;

import java.sql.Timestamp;

/**
 * Created by MarioJ on 21/03/16.
 */
public class Log {

    public static void d(String tag, String message) {
        System.out.printf("[%s]\t%s: %s\n", tag, String.valueOf(new Timestamp(System.currentTimeMillis())), message);
    }

    public static void nl() {
        System.out.println();
    }
}
