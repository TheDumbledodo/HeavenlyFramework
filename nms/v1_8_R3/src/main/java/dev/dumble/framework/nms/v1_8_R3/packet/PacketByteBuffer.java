package dev.dumble.framework.nms.v1_8_R3.packet;

import io.netty.buffer.Unpooled;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketDataSerializer;

import java.io.IOException;

public class PacketByteBuffer {

	private static final PacketByteBuffer INSTANCE = new PacketByteBuffer();
	private final PacketDataSerializer serializer;

	public static PacketByteBuffer get() {
		INSTANCE.clear();

		return INSTANCE;
	}

	private PacketByteBuffer() {
		this.serializer = new PacketDataSerializer(Unpooled.buffer());
	}

	public int readableBytes() {
		return serializer.readableBytes();
	}

	public void readBytes(byte[] bytes) {
		serializer.readBytes(bytes);
	}

	public void writeBoolean(boolean flag) {
		serializer.writeBoolean(flag);
	}

	public void writeByte(byte b) {
		serializer.writeByte(b);
	}

	public void writeByte(int i) {
		serializer.writeByte(i);
	}

	public void writeShort(int i) {
		serializer.writeShort(i);
	}

	public void writeInt(int i) {
		serializer.writeInt(i);
	}

	public void writeVarInt(int i) {
		serializer.b(i);
	}

	public void writeString(String s) {
		serializer.a(s);
	}

	public void writeItemStack(ItemStack itemStack) {
		serializer.a(itemStack);
	}

	public void clear() {
		serializer.clear();
	}

	public <T extends Packet<?>> T writeDataTo(T packet) {
		try {
			packet.a(serializer);
			return packet;

		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}
}