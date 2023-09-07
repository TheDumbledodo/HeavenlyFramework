package dev.dumble.common.packet.listener;

import org.bukkit.entity.Player;

public interface InboundPacketListener {

    boolean onPacketReceive(Player player, Object packet);
}