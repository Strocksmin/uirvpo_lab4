package org.example;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс оценки настроения текста
 *
 * @author Karabanov-AE 22.04.2025
 */
public class TextMoodAnalyzer {
    private static final Set<String> POSITIVE_WORDS = new HashSet<>(Arrays.asList(
            "хорош", "отличн", "прекрасн", "радост", "счаст", "любов", "успех", "весел",
            "позитив", "удовольстви", "наслаждени", "мечт", "восторг", "гармони", "побед",
            "замечательн", "приятн", "восхитительн", "благодар", "одобрени", "удача", "повез", "супер"
    ));

    private static final Set<String> NEGATIVE_WORDS = new HashSet<>(Arrays.asList(
            "плох", "ужасн", "страшн", "груст", "несчаст", "ненавист", "провал", "зл",
            "негатив", "страдани", "бол", "кошмар", "бед", "проблем", "поражени",
            "отвратительн", "разочарован", "печал", "тоск", "уныни", "тревог", "тупой", "грубый"
    ));

    private static final Pattern WORD_PATTERN = Pattern.compile("[а-яА-ЯёЁa-zA-Z]{3,}");

    public static void main(String[] args) {
        try {
            Properties props = new Properties();
            try (InputStream input = TextMoodAnalyzer.class.getClassLoader().getResourceAsStream("application.properties")) {
                if (input == null) {
                    System.out.println("Can't find application.properties");
                    return;
                }
                props.load(input);
            }

            String filePath = props.getProperty("text.file.path");
            if (filePath == null || filePath.isEmpty()) {
                System.out.println("Please specify 'text.file.path' in application.properties");
                return;
            }

            String text = readTextFromFile(filePath);
            int moodScore = calculateMoodScore(text);
            System.out.println("Оценка настроения: " + moodScore);
            System.out.println("Настроение: " + interpretMood(moodScore));
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String readTextFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath))).toLowerCase();
    }

    static int calculateMoodScore(String text) {
        int positiveCount = 0;
        int negativeCount = 0;

        Matcher matcher = WORD_PATTERN.matcher(text);
        while (matcher.find()) {
            String word = matcher.group();
            if (isPositive(word)) {
                positiveCount++;
            } else if (isNegative(word)) {
                negativeCount++;
            }
        }

        return positiveCount - negativeCount;
    }

    private static boolean isPositive(String word) {
        for (String positive : POSITIVE_WORDS) {
            if (word.startsWith(positive)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isNegative(String word) {
        for (String negative : NEGATIVE_WORDS) {
            if (word.startsWith(negative)) {
                return true;
            }
        }
        return false;
    }

    static String interpretMood(int moodScore) {
        if (moodScore > 10) {
            return "Очень позитивное";
        } else if (moodScore > 3) {
            return "Позитивное";
        } else if (moodScore >= -3) {
            return "Нейтральное";
        } else if (moodScore >= -10) {
            return "Негативное";
        } else {
            return "Очень негативное";
        }
    }
}