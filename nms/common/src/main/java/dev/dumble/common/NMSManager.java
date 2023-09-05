package dev.dumble.common;

import org.bukkit.entity.Player;

public interface NMSManager {

    void injectPacketListener(Player player, PacketListener packetListener);

    void uninjectPacketListener(Player player);
}