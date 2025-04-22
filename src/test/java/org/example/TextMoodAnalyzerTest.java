package org.example;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тестовый класс для TextMoodAnalyzer
 *
 * @author Karabanov-ae 22.04.2025
 */
public class TextMoodAnalyzerTest {

    @Test
    void testCalculateMoodScoreVeryPositive() throws IOException {
        TextMoodAnalyzer analyzer = new TextMoodAnalyzer();
        Path filePath = Paths.get("src/test/resources/org.example/positive.txt");
        String content = Files.readString(filePath, StandardCharsets.UTF_8);
        int score = analyzer.calculateMoodScore(content);
        assertTrue(score > 5, "Score should be strongly positive");
    }

    @Test
    void testCalculateMoodScoreNeutral() throws IOException {
        TextMoodAnalyzer analyzer = new TextMoodAnalyzer();
        Path filePath = Paths.get("src/test/resources/org.example/neutral.txt");
        String content = Files.readString(filePath, StandardCharsets.UTF_8);
        int score = analyzer.calculateMoodScore(content);
        assertTrue(Math.abs(score) <= 3, "Score should be neutral");
    }

    @Test
    void testCalculateMoodScoreVeryNegative() throws IOException {
        TextMoodAnalyzer analyzer = new TextMoodAnalyzer();
        Path filePath = Paths.get("src/test/resources/org.example/negative.txt");
        String content = Files.readString(filePath, StandardCharsets.UTF_8);
        int score = analyzer.calculateMoodScore(content);
        System.out.println(score);
        assertTrue(score < -5, "Score should be strongly negative");
    }

    @Test
    void testInterpretMood() {
        TextMoodAnalyzer analyzer = new TextMoodAnalyzer();
        assertEquals("Очень позитивное", analyzer.interpretMood(15));
        assertEquals("Позитивное", analyzer.interpretMood(5));
        assertEquals("Нейтральное", analyzer.interpretMood(0));
        assertEquals("Негативное", analyzer.interpretMood(-5));
        assertEquals("Очень негативное", analyzer.interpretMood(-15));
    }
}
