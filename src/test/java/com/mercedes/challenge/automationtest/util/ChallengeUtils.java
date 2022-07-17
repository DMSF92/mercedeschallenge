package com.mercedes.challenge.automationtest.util;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ChallengeUtils {
    public static void saveTxt(String lowest, String highest, String browser) {
        String text = new StringBuilder("Highest Price: ").append(highest).append("\n").append("Lowest Price: ").append(lowest).toString();
        String path = System.getProperty("user.dir") + "/results";
        String fileName = path + "/result_"+browser+".txt";

        try {

            if (!Files.exists(Path.of(path))){
                Files.createDirectory(Path.of(path));
            }

            Files.writeString(Path.of(fileName), text);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void takeScreenshot(WebElement screenshotElement, String browser) {
        File source = screenshotElement.getScreenshotAs(OutputType.FILE);
        File folder = new File(System.getProperty("user.dir") + "/screenshots");
        String fileName = folder + "/results_"+browser+".png";

        folder.mkdir();
        File dest = new File(fileName);

        try {
            FileHandler.copy(source, dest);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
