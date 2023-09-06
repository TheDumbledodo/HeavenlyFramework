/*
 * Copyright (C) filoghost and contributors
 *
 * SPDX-License-Identifier: GPL-3.0-or-later
 */
package dev.dumble.heavenly.framework.core;

import dev.dumble.common.NMSManager;
import dev.dumble.heavenly.framework.core.common.ExceptionCollector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public enum NMSVersion {

    /* 1.8 - 1.8.2     */ v1_8_R1(NMSManagerFactory.outdatedVersion("1.8.4")),
    /* 1.8.3           */ v1_8_R2(NMSManagerFactory.outdatedVersion("1.8.4")),

    /* 1.8.4 - 1.8.9   */ v1_8_R3(collector -> new dev.dumble.framework.nms.v1_8_R3.packet.VersionNMSManager()),

    /* Other versions  */ UNKNOWN(null);

    private static final NMSVersion CURRENT_VERSION = NMSVersion.detectCurrentVersion();

    private final NMSManagerFactory nmsManagerFactory;

    public NMSManager createNMSManager(ExceptionCollector exceptionCollector) throws OutdatedVersionException, UnknownVersionException {
        return nmsManagerFactory.create(exceptionCollector);
    }

    public static NMSVersion getCurrent() {
        return CURRENT_VERSION;
    }

    private static NMSVersion detectCurrentVersion() {
        final String name = Bukkit.getServer().getClass().getPackage().getName();

        final Matcher matcher = Pattern.compile("v\\d+_\\d+_R\\d+").matcher(name);
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

        NMSManager create(ExceptionCollector exceptionCollector) throws UnknownVersionException, OutdatedVersionException;

        static NMSManagerFactory unknownVersion() {
            return exceptionCollector -> {
                throw new UnknownVersionException();
            };
        }

        static NMSManagerFactory outdatedVersion(String minimumSupportedVersion) {
            return exceptionCollector -> {
                throw new OutdatedVersionException(minimumSupportedVersion);
            };
        }
    }

    @Getter
    @AllArgsConstructor
    public static class OutdatedVersionException extends Exception {

        private final String minimumSupportedVersion;
    }

    public static class UnknownVersionException extends Exception {}
}
