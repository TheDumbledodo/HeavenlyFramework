package dev.dumble.framework.nms.v1_8_R3.packet.impl;

import dev.dumble.common.EntityIdProvider;
import dev.dumble.api.container.Container;
import dev.dumble.framework.nms.v1_8_R3.menu.WrapperContainerType;
import dev.dumble.framework.nms.v1_8_R3.packet.PacketByteBuffer;
import dev.dumble.framework.nms.v1_8_R3.packet.VersionNMSPacket;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutOpenWindow;

@Getter
public class MenuCreationNMSPacket extends VersionNMSPacket {

	private final Packet<?> rawPacket;

	public MenuCreationNMSPacket(Container container) {
		final PacketByteBuffer packetByteBuffer = PacketByteBuffer.get();
		final WrapperContainerType containerType = WrapperContainerType.convert(container.getContainerType());

		container.setWindowId(EntityIdProvider.generate());
		packetByteBuffer.writeByte(container.getWindowId());

		packetByteBuffer.writeString(containerType.name());
		packetByteBuffer.writeString(container.getTitle());

		packetByteBuffer.writeByte(container.getSize());

		this.rawPacket = packetByteBuffer.writeDataTo(new PacketPlayOutOpenWindow());
	}
}
