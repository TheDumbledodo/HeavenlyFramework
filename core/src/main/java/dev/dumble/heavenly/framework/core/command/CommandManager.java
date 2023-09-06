package dev.dumble.heavenly.framework.core.command;

import dev.dumble.heavenly.framework.core.annotation.Command;
import dev.dumble.heavenly.framework.core.annotation.HeavenlyRegistry;
import dev.dumble.heavenly.framework.core.util.ReflectionUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class CommandManager {

    @SneakyThrows
    public void bootstrap(JavaPlugin plugin) {
        // TODO: register commands into plugin yaml file

        for (Class<?> clazz : getCommandClasses()) {
            final Command annotation = clazz.getAnnotation(Command.class);

            final Object instance = clazz.getConstructor(Command.class).newInstance(annotation);
            final HeavenlyCommand command = (HeavenlyCommand) instance;

            plugin.getCommand(command.getName());
        }
    }

    @NotNull
    private List<Class<?>> getCommandClasses() {
        final List<Class<?>> classes = new ArrayList<>();

        for (Class<?> clazz : ReflectionUtils.getRuntimeClasses()) {
            if (!HeavenlyCommand.class.isAssignableFrom(clazz)) continue;

            if (!clazz.isAnnotationPresent(HeavenlyRegistry.class)) continue;
            if (!clazz.isAnnotationPresent(Command.class)) continue;

            classes.add(clazz);
        }

        return classes;
    }
}
