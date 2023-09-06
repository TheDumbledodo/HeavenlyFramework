package dev.dumble.heavenly.framework.core.utils;

import dev.dumble.heavenly.framework.core.HeavenlyPlugin;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Pattern;

@UtilityClass
public class ReflectionUtils {

    private final Pattern CLASS_PATTERN = Pattern.compile("\\w+\\$[0-9]$");

    @SneakyThrows
    public List<Class<?>> getRuntimeClasses() {
        final List<Class<?>> classes = new ArrayList<>();

        final Method getFileMethod = JavaPlugin.class.getDeclaredMethod("getFile");
        getFileMethod.setAccessible(true);

        final File pluginFile = (File) getFileMethod.invoke(HeavenlyPlugin.getInstance());

        try (JarFile file = new JarFile(pluginFile)) {
            for (Enumeration<JarEntry> entry = file.entries(); entry.hasMoreElements(); ) {
                try {
                    final JarEntry jar = entry.nextElement();
                    final String name = jar.getName().replace("/", ".");

                    if (!name.endsWith(".class")) continue;

                    final String className = name.substring(0, name.length() - 6);

                    final Class<?> clazz = HeavenlyPlugin.class.getClassLoader().loadClass(className);

                    if (Modifier.isAbstract(clazz.getModifiers()) || CLASS_PATTERN.matcher(className).find()) continue;

                    classes.add(clazz);
                } catch (Throwable ignored) {
                }
            }
        }

        return classes;
    }
}
