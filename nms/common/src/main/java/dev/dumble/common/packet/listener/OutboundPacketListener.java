package dev.dumble.common.packet.listener;

import org.bukkit.entity.Player;

public interface OutboundPacketListener {

    boolean onPacketSend(Player player, Object packet);
}