package dev.dumble.framework.nms.v1_8_R3.packet;

import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public abstract class VersionNMSPacket {

    public void sendTo(Player player) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(this.getRawPacket());
    }

    protected abstract Packet<?> getRawPacket();
}