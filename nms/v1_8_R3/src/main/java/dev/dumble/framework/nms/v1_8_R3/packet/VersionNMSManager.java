package dev.dumble.framework.nms.v1_8_R3.packet;

import dev.dumble.common.NMSManager;
import dev.dumble.common.PacketListener;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelPipeline;
import net.minecraft.server.v1_8_R3.NetworkManager;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class VersionNMSManager implements NMSManager {

	@Override
	public void injectPacketListener(Player player, PacketListener packetListener) {
		final String handler = InboundPacketHandler.HANDLER_NAME;

		this.modifyPipeline(player,
				(ChannelPipeline pipeline) -> {

					final ChannelHandler currentListener = pipeline.get(handler);
					if (currentListener != null) pipeline.remove(handler);

					pipeline.addBefore("packet_handler", handler, new InboundPacketHandler(player, packetListener));
				});
	}

	@Override
	public void uninjectPacketListener(Player player) {
		final String handler = InboundPacketHandler.HANDLER_NAME;

		this.modifyPipeline(player, (ChannelPipeline pipeline) -> {
			final ChannelHandler currentListener = pipeline.get(handler);
			if (currentListener == null) return;

			pipeline.remove(handler);
		});
	}

	private void modifyPipeline(Player player, Consumer<ChannelPipeline> pipelineModifierTask) {
		final CraftPlayer craftPlayer = (CraftPlayer) player;
		final PlayerConnection connection = craftPlayer.getHandle().playerConnection;

		final NetworkManager networkManager = connection.a();
		final Channel channel = networkManager.channel;

		channel.eventLoop()
				.execute(() -> {
					if (!player.isOnline()) return;

					pipelineModifierTask.accept(channel.pipeline());
				});
	}
}