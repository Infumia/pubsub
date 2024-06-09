/*
 * Copyright 2017 Patrick Favre-Bulle
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package net.infumia.pubsub;

import java.util.Objects;

/**
 * <a href="https://github.com/patrickfav/bytes-java/blob/main/src/main/java/at/favre/lib/bytes/BinaryToTextEncoding.java">From</a>
 */
final class Hex {
    private static final char[] LOOKUP_TABLE_LOWER = new char[]{0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39, 0x61, 0x62, 0x63, 0x64, 0x65, 0x66};

    static String encode(final byte[] byteArray) {
        final char[] buffer = new char[byteArray.length * 2];
        final char[] lookup = Hex.LOOKUP_TABLE_LOWER;

        for (int i = 0; i < byteArray.length; i++) {
            buffer[i << 1] = lookup[(byteArray[i] >> 4) & 0xF];
            buffer[(i << 1) + 1] = lookup[(byteArray[i] & 0xF)];
        }
        return new String(buffer);
    }

    static byte[] decode(final CharSequence hexString) {
        int start;
        if (Objects.requireNonNull(hexString).length() > 2 &&
            hexString.charAt(0) == '0' && hexString.charAt(1) == 'x') {
            start = 2;
        } else {
            start = 0;
        }

        final int len = hexString.length();
        final boolean isOddLength = len % 2 != 0;
        if (isOddLength) {
            start--;
        }

        final byte[] data = new byte[(len - start) / 2];
        int first4Bits;
        int second4Bits;
        for (int i = start; i < len; i += 2) {
            if (i == start && isOddLength) {
                first4Bits = 0;
            } else {
                first4Bits = Character.digit(hexString.charAt(i), 16);
            }
            second4Bits = Character.digit(hexString.charAt(i + 1), 16);

            if (first4Bits == -1 || second4Bits == -1) {
                if (i == start && isOddLength) {
                    throw new IllegalArgumentException("'" + hexString.charAt(i + 1) + "' at index " + (i + 1) + " is not hex formatted");
                } else {
                    throw new IllegalArgumentException("'" + hexString.charAt(i) + hexString.charAt(i + 1) + "' at index " + i + " is not hex formatted");
                }
            }

            data[(i - start) / 2] = (byte) ((first4Bits << 4) + second4Bits);
        }
        return data;
    }

    private Hex() {
        throw new IllegalStateException("Utility class");
    }
}
