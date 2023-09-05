package dev.dumble.common;

import org.bukkit.entity.Player;

public interface PacketListener {

    boolean onMenuInteract(Player player, Object object);
}