package dev.dumble.heavenly.framework.core.command;

import dev.dumble.heavenly.framework.core.annotation.Command;
import dev.dumble.heavenly.framework.core.annotation.HeavenlyRegistry;
import dev.dumble.heavenly.framework.core.util.ReflectionUtils;
import dev.dumble.heavenly.framework.core.util.ResourceUtils;
import lombok.experimental.UtilityClass;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

@UtilityClass
public class CommandManager {

    public final List<HeavenlyCommand> HEAVENLY_COMMANDS = new ArrayList<>();
    private final List<Class<?>> CLASSES = new ArrayList<>();

    public void setupFiles() {
        final List<Class<?>> heavenlyCommands = getHeavenlyClasses(Command.class);
        if (heavenlyCommands.isEmpty()) return;

        final List<String> strings = new ArrayList<>();

        for (Class<?> clazz : heavenlyCommands) {
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

    public void bootstrap(JavaPlugin plugin) {
        for (Class<?> clazz : getHeavenlyClasses(Command.class)) {
            try {
                final Command annotation = clazz.getAnnotation(Command.class);

                final Object instance = clazz.getConstructor(Command.class).newInstance(annotation);
                final HeavenlyCommand command = (HeavenlyCommand) instance;

                final PluginCommand pluginCommand = plugin.getCommand(command.getName());
                if (pluginCommand == null) continue;

                pluginCommand.setTabCompleter(command);
                pluginCommand.setExecutor(command);

                HEAVENLY_COMMANDS.add(command);
            } catch (Throwable throwable) {
                plugin.getLogger().log(Level.SEVERE, "Unable to register " + clazz.getSimpleName() + " as a command.", throwable);
            }
        }
    }

    @NotNull
    public List<Class<?>> getHeavenlyClasses(Class<? extends Annotation> annotation) {
        if (CLASSES.isEmpty())
            ReflectionUtils.getRuntimeClasses()
                .stream()
                .filter(HeavenlyCommand.class::isAssignableFrom)
                .filter(clazz -> clazz.isAnnotationPresent(HeavenlyRegistry.class))
                .forEach(CLASSES::add);

        return CLASSES.stream()
                .filter(clazz -> clazz.isAnnotationPresent(annotation))
                .collect(Collectors.toList());
    }
}
