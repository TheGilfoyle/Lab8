package org.example.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для работы с файлами скриптов
 */
public class ScriptManager {
    /**
     * Путь к файлу скрипта
     */
    private final String filePath;

    /**
     * Конструктор
     *
     * @param filePath путь к файлу скрипта
     */
    public ScriptManager(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Чтение скрипта из файла
     *
     * @return список строк скрипта
     * @throws IOException если возникла ошибка чтения файла
     */
    public List<String> readScript() throws IOException {
        List<String> scriptLines = new ArrayList<>();
        File scriptFile = new File(filePath);

        if (!scriptFile.exists()) {
            throw new IOException("Файл скрипта не найден: " + filePath);
        }

        if (!scriptFile.canRead()) {
            throw new IOException("Нет прав на чтение файла: " + filePath);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(scriptFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty() && !line.trim().startsWith("#")) {
                    scriptLines.add(line.trim());
                }
            }
        }

        return scriptLines;
    }

    /**
     * Чтение скрипта из файла
     *
     * @return буферизированный поток
     * @throws IOException если возникла ошибка чтения файла
     */
    public BufferedReader getBufferedReader() throws IOException {
        File scriptFile = new File(filePath);

        if (!scriptFile.exists()) {
            throw new IOException("Файл скрипта не найден: " + filePath);
        }

        if (!scriptFile.canRead()) {
            throw new IOException("Нет прав на чтение файла: " + filePath);
        }

        return new BufferedReader(new FileReader(scriptFile));
    }

}
