package dev.dumble.heavenly.framework.core.command;

import dev.dumble.heavenly.framework.core.annotation.Permission;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ToString
public abstract class HeavenlyCommand extends Command {

    public final static List<String> NO_COMPLETE = Collections.emptyList();

    private CommandSender sender;
    private String currentLabel;
    private String[] args;

    // TODO: handle other things from Command annotation
    protected HeavenlyCommand(dev.dumble.heavenly.framework.core.annotation.Command command) {
        super(command.command());

        this.setAliases(Arrays.asList(command.aliases()));
        this.setUsage(/* TODO: colorize for messages */ command.usage());
        this.setDescription(/* TODO: colorize for messages */ command.description());

        final Permission permission = command.permission();
        this.setPermission(permission.permission().isEmpty() ? null : permission.permission());
        this.setPermissionMessage(/* TODO: colorize for messages */ permission.denyMessage());
    }

    @Override
    public final boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        this.setFields(sender, commandLabel, args);
        return false;
    }

    @NotNull
    @Override
    public final List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args, @Nullable Location location) {
        return this.tabComplete(sender, alias, args);
    }

    @NotNull
    @Override
    public final List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) {
        this.setFields(sender, alias, args);
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
