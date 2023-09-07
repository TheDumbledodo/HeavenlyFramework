package dev.dumble.heavenly.framework.core.command;

import dev.dumble.heavenly.framework.core.annotation.Command;
import dev.dumble.heavenly.framework.core.annotation.HeavenlyRegistry;
import dev.dumble.heavenly.framework.core.util.ReflectionUtils;
import dev.dumble.heavenly.framework.core.util.ResourceUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class CommandManager {

    private final List<Class<?>> classes = new ArrayList<>();

    public void setupFiles() {
        final List<String> strings = new ArrayList<>();

        for (Class<?> clazz : getHeavenlyClasses(Command.class)) {
            final Command annotation = clazz.getAnnotation(Command.class);

            strings.add(String.format("  %s:", annotation.command()));

            final String description = annotation.description();
            if (!description.isEmpty())
                strings.add(String.format("    description: %s", description));

            final String[] aliases = annotation.aliases();
            if (aliases.length != 0) {
                final String join = String.join(", ", aliases);

                strings.add(String.format("    aliases: [ %s ]", join));
            }
        }

        strings.add(" ");

        ResourceUtils.appendResourcesTextFile("plugin.yml", strings);
    }

    @SneakyThrows
    public void bootstrap(JavaPlugin plugin) {
        for (Class<?> clazz : getHeavenlyClasses(Command.class)) {
            final Command annotation = clazz.getAnnotation(Command.class);

            final Object instance = clazz.getConstructor(Command.class).newInstance(annotation);
            final HeavenlyCommand command = (HeavenlyCommand) instance;

            plugin.getCommand(command.getName());
        }
    }

    @NotNull
    public List<Class<?>> getHeavenlyClasses(Class<? extends Annotation> annotation) {
        if (classes.isEmpty())
            ReflectionUtils.getRuntimeClasses()
                .stream()
                .filter(HeavenlyCommand.class::isAssignableFrom)
                .filter(clazz -> clazz.isAnnotationPresent(HeavenlyRegistry.class))
                .forEach(classes::add);

        return classes.stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }
}
