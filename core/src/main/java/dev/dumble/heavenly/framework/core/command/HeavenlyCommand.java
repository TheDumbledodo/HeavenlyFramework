package dev.dumble.heavenly.framework.core.command;

import dev.dumble.heavenly.framework.core.annotation.Permission;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ToString
@Getter @Setter(value = AccessLevel.PRIVATE)
public abstract class HeavenlyCommand implements CommandExecutor, TabCompleter {

    public final static List<String> NO_COMPLETE = Collections.emptyList();

    private final String name;
    private final List<String> aliases;
    private final String permission;
    private final String permissionMessage;

    private CommandSender sender;
    private String currentLabel;
    private String[] args;

    // TODO: handle other things from Command annotation
    protected HeavenlyCommand(dev.dumble.heavenly.framework.core.annotation.Command command) {
        super();

        this.name = command.command();
        this.aliases = Arrays.asList(command.aliases());

        final Permission permission = command.permission();
        this.permission = permission.permission().isEmpty() ? null : permission.permission();
        this.permissionMessage = permission.denyMessage().isEmpty() ? null : permission.denyMessage();
    }

    @Override
    public boolean onCommand(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        this.setFields(sender, label, args);
        this.onCommand();
        return false;
    }

    @Override @Nullable
    public List<String> onTabComplete(@NotNull final CommandSender sender, @NotNull final Command command, @NotNull final String label, @NotNull final String[] args) {
        this.setFields(sender, label, args);
        return this.tabComplete();
    }

    private void setFields(CommandSender sender, String label, String[] args) {
        this.sender = sender;
        this.currentLabel = label;
        this.args = args;
    }

    abstract public void onCommand();

    public List<String> tabComplete() {
        return NO_COMPLETE;
    }
}
