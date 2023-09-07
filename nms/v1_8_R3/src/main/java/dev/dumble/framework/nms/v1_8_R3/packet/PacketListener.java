package dev.dumble.framework.nms.v1_8_R3.packet;

import dev.dumble.api.packet.PacketEvent;
import dev.dumble.api.packet.PacketType;
import dev.dumble.common.util.BukkitHelper;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PacketListener extends ChannelDuplexHandler {

    public static final String HANDLER_NAME = "heavenly_framework_listener";

    private final Player player;

    @Override
    @SneakyThrows
    public void write(ChannelHandlerContext context, Object packet, ChannelPromise promise) {
        final PacketEvent event = new PacketEvent(packet, player, PacketType.WRITE);
        BukkitHelper.callEvent(event);

        if (event.isCancelled()) return;
        super.write(context, packet, promise);
    }

    @Override
    @SneakyThrows
    public void channelRead(ChannelHandlerContext context, Object packet) {
        final PacketEvent event = new PacketEvent(packet, player, PacketType.READ);
        BukkitHelper.callEvent(event);

        if (event.isCancelled()) return;
        super.channelRead(context, packet);
    }
}