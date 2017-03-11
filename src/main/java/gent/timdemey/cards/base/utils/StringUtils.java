package gent.timdemey.cards.base.utils;

import java.util.Random;

public class StringUtils {
    public static String getRandomString(int length) {
        String CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder builder = new StringBuilder();
        Random rnd = new Random();
        while (builder.length() < length) {
            builder.append(CHARS.charAt(rnd.nextInt(CHARS.length())));
        }
        String result = builder.toString();
        return result;
    }
}
