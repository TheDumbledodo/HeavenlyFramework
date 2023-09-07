package dev.dumble.heavenly.framework.core;

import dev.dumble.api.packet.NMSManager;
import dev.dumble.heavenly.framework.core.exception.OutdatedVersionException;
import dev.dumble.heavenly.framework.core.exception.UnknownVersionException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public enum NMSVersion {

    /* 1.8.4 - 1.8.9   */ v1_8_R3(() -> new dev.dumble.framework.nms.v1_8_R3.packet.VersionNMSManager()),

    /* 1.8.3           */ v1_8_R2(NMSManagerFactory.outdatedVersion("1.8.4")),
    /* 1.8 - 1.8.2     */ v1_8_R1(NMSManagerFactory.outdatedVersion("1.8.4")),

    /* Other versions  */ UNKNOWN(NMSManagerFactory.unknownVersion());

    private static final NMSVersion CURRENT_VERSION = NMSVersion.detectCurrentVersion();

    private static final Pattern VERSION_PATTERN = Pattern.compile("v\\d+_\\d+_R\\d+");

    private final NMSManagerFactory nmsManagerFactory;

    public NMSManager createNMSManager() throws OutdatedVersionException, UnknownVersionException {
        return nmsManagerFactory.check();
    }

    public static NMSVersion getCurrent() {
        return CURRENT_VERSION;
    }

    private static NMSVersion detectCurrentVersion() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();

        if (VERSION_PATTERN == null) return UNKNOWN;

        final Matcher matcher = VERSION_PATTERN.matcher(name);
        if (!matcher.find()) return UNKNOWN;

        try {
            final String nmsVersionName = matcher.group();

            return NMSVersion.valueOf(nmsVersionName);

        } catch (IllegalArgumentException exception) {
            return UNKNOWN;
        }
    }

    @FunctionalInterface
    private interface NMSManagerFactory {

        NMSManager check() throws UnknownVersionException, OutdatedVersionException;

        @SneakyThrows
        static NMSManagerFactory unknownVersion() {
            throw new UnknownVersionException();
        }

        @SneakyThrows
        static NMSManagerFactory outdatedVersion(String minimumSupportedVersion) {
            throw new OutdatedVersionException(minimumSupportedVersion);
        }
    }
}
