package dev.dumble.heavenly.framework.core.command;

import dev.dumble.heavenly.framework.core.annotation.Command;

public abstract class HeavenlySubCommand extends HeavenlyCommand {

    protected HeavenlySubCommand(final Command command) {
        super(command);
    }
}
