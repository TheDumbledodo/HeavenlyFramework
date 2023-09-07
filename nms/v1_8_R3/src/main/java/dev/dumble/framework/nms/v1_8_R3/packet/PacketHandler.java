package dev.dumble.framework.nms.v1_8_R3.packet;

import dev.dumble.common.packet.PacketEvent;
import dev.dumble.common.util.Logger;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class PacketHandler extends ChannelDuplexHandler {

    public static final String HANDLER_NAME = "heavenly_framework_listener";

    private final Player player;

    @Override
    public void write(ChannelHandlerContext context, Object packet, ChannelPromise promise) {
        final PacketEvent event = new PacketEvent(packet, player);

        try {
            super.write(context, packet, promise);

        } catch (Throwable throwable) {
            Logger.severe("Unexpected error while inspecting outgoing packet.", throwable);
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext context, Object packet) {
        final PacketEvent event = new PacketEvent(packet, player);

        try {
            super.channelRead(context, packet);

        } catch (Throwable throwable) {
            Logger.severe("Unexpected error while inspecting inbound packet.", throwable);
        }
    }
}