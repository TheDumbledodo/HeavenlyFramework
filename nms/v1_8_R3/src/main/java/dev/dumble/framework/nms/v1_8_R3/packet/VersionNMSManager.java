package dev.dumble.framework.nms.v1_8_R3.packet;

import dev.dumble.common.packet.NMSManager;
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
	public void injectPacketListener(Player player) {
		this.modifyPipeline(player,
				(ChannelPipeline pipeline) -> {
					final String handler = PacketHandler.HANDLER_NAME;

					final ChannelHandler currentListener = pipeline.get(handler);
					if (currentListener != null) pipeline.remove(handler);

					pipeline.addBefore("packet_handler", handler, new PacketHandler(player));
				});
	}

	@Override
	public void uninjectPacketListener(Player player) {
		final String handler = PacketHandler.HANDLER_NAME;

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