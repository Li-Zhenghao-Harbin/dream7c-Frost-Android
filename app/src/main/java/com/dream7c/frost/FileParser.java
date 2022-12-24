package com.dream7c.frost;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class FileParser {
    public static String parserTxt(File file) throws IOException {
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt;
        ProjectActivity.content = "";
        StringBuilder sb = new StringBuilder();
        while ((lineTxt = bufferedReader.readLine()) != null) {
            sb.append(lineTxt);
        }
        return sb.toString();
    }
}
