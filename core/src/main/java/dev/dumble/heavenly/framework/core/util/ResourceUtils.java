package dev.dumble.heavenly.framework.core.util;

import dev.dumble.heavenly.framework.core.HeavenlyPlugin;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@UtilityClass
public class ResourceUtils {

    @SneakyThrows
    public static void addPluginString(String resourcePath, List<String> strings) {
        final InputStream inputStream = HeavenlyPlugin.class.getResourceAsStream(resourcePath);
        if (inputStream == null) return;

        final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder content = new StringBuilder();

        String line;
        while ((line = reader.readLine()) != null)
            content.append(line).append("\n");

        reader.close();

        final String string = "\n" + String.join("\n", strings);
        final String modifiedContent = content.append(string).toString();

        final OutputStream outputStream = Files.newOutputStream(Paths.get(resourcePath));
        outputStream.write(modifiedContent.getBytes());
        outputStream.close();
    }
}
