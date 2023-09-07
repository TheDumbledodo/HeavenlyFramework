package dev.dumble.common.packet;

import org.bukkit.entity.Player;

public interface NMSManager {

    void injectPacketListener(Player player);

    void uninjectPacketListener(Player player);
}