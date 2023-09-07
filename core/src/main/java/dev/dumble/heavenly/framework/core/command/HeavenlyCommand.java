package dev.dumble.heavenly.framework.core.command;

import dev.dumble.heavenly.framework.core.annotation.Cooldown;
import dev.dumble.heavenly.framework.core.annotation.Permission;
import dev.dumble.heavenly.framework.core.expiring.ExpiringSet;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@ToString
@Getter
@Setter(value = AccessLevel.PRIVATE)
public abstract class HeavenlyCommand implements CommandExecutor, TabCompleter {

    public final static List<String> NO_COMPLETE = Collections.emptyList();

    /**
     * Command
     */
    private final String name;
    private final List<String> aliases;
    private final String permission;
    private final String permissionMessage;

    /**
     * Cooldown
     */
    private final ExpiringSet<UUID> expiringCooldown;
    private final String cooldownMessage;
    private final String cooldownBypassPermission;
    private final boolean cooldownOnCommand;
    private final boolean clearCooldownOnQuit;

    /**
     * Handler fields
     */
    private CommandSender sender;
    private String currentLabel;
    private String[] args;

    /*
     * TODO: handle other things from Command annotation
     * TODO: colorize messages
     */
    protected HeavenlyCommand(dev.dumble.heavenly.framework.core.annotation.Command command) {
        super();

        this.name = command.command();
        this.aliases = Arrays.asList(command.aliases());

        final Permission permission = command.permission();
        this.permission = permission.permission().isEmpty() ? null : permission.permission();
        this.permissionMessage = permission.denyMessage().isEmpty() ? null : permission.denyMessage();

        final Cooldown cooldown = command.cooldown();
        this.expiringCooldown = cooldown.duration() == -1 ? null : new ExpiringSet<>(cooldown.duration(), cooldown.timeUnit());
        this.cooldownMessage = cooldown.duration() == -1 || cooldown.denyMessage().isEmpty() ? null : cooldown.denyMessage();
        this.cooldownBypassPermission = cooldown.duration() == -1 || cooldown.bypassPermission().isEmpty() ? null : cooldown.bypassPermission();
        this.cooldownOnCommand = cooldown.onCommand();
        this.clearCooldownOnQuit = cooldown.clearOnQuit();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        this.setFields(sender, label, args);

        if (this.expiringCooldown != null && sender instanceof Player) {
            final Player player = ((Player) sender);
            final UUID uniqueId = player.getUniqueId();

            if (!player.hasPermission(this.cooldownBypassPermission) && this.expiringCooldown.contains(uniqueId)) {
                player.sendMessage(this.cooldownMessage);
                return false;
            }
        }

        this.onCommand();

        if (this.expiringCooldown != null && sender instanceof Player && this.cooldownOnCommand) {
            final Player player = ((Player) sender);
            final UUID uniqueId = player.getUniqueId();

            this.expiringCooldown.add(uniqueId);
        }

        return false;
    }

    @Override @Nullable
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
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
