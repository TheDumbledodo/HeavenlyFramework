package dev.dumble.framework.nms.v1_8_R3.packet;

import dev.dumble.common.PacketListener;
import dev.dumble.api.container.Container;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class InboundPacketHandler extends ChannelInboundHandlerAdapter {

    public static final String HANDLER_NAME = "heavenly_framework_listener";

    private final Player player;
    private final PacketListener packetListener;

    @Override
    public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
        try {
            if (packet instanceof PacketPlayInWindowClick) {

                final PacketPlayInWindowClick windowClick = (PacketPlayInWindowClick) packet;
                final Container.DataHolder containerData = Container.DataHolder.builder()
                        .setWindowId(windowClick.a())
                        .setSlot(windowClick.b())
                        .setButton(windowClick.c())
                        .setActionNumber(windowClick.d())
                        .setItem(CraftItemStack.asBukkitCopy(windowClick.e()))
                        .build();

                final Container container = Container.getContainer(containerData.getWindowId());
                container.callContainerInteraction(player, containerData);

                if (packetListener.onMenuInteract(player, containerData)) return;
            }
        } catch (Throwable throwable) {
            // todo: add loggers
        }
        super.channelRead(context, packet);
    }
}