/*
 * Copyright 2024 anominy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.anominy.uwutils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.Locale;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwString {
    public static final String EMPTY = "";

    @UnknownNullability
    @Contract(value = "null, _, _ -> param3; !null, null, _ -> param1; !null, _, _ -> !null", pure = true)
    public static String trimOrElse(
            @Nullable
            final String str,

            @Nullable
            final Integer diff,

            @Nullable
            final String defaultValue
    ) {
        if (str == null) {
            return defaultValue;
        }

        if (diff == null) {
            return str;
        }

        return trimNoCheck(str, diff);
    }

    @UnknownNullability
    @Contract(value = "!null, null, _ -> param1; !null, _, _ -> !null", pure = false)
    public static String trimOrElse(
            @Nullable
            final String str,

            @Nullable
            final Integer diff,

            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(trimOrNull(str, diff), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _, _ -> null; !null, null, _ -> param1; !null, _, _ -> !null", pure = false)
    public static String trimOrElse(
            @Nullable
            final String str,

            @Nullable
            final Integer diff,

            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return trimOrElse(str, diff, (Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(value = "!null, null -> param1", pure = true)
    public static String trimOrEmpty(
            @Nullable
            final String str,

            @Nullable
            final Integer diff
    ) {
        return trimOrElse(str, diff, EMPTY);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param1; !null, null -> param1; !null, _ -> !null", pure = true)
    public static String trimOrSelf(
            @Nullable
            final String str,

            @Nullable
            final Integer diff
    ) {
        return trimOrElse(str, diff, str);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null; !null, null -> param1; !null, _ -> !null", pure = true)
    public static String trimOrNull(
            @Nullable
            final String str,

            @Nullable
            final Integer diff
    ) {
        return trimOrElse(str, diff, (@Nullable String) null);
    }

    @NotNull
    @Contract(pure = true)
    public static String trimNoCheck(
            @NotNull
            final String str,

            final int diff
    ) {
        if (diff == 0) {
            return str;
        }

        final int count = Math.abs(diff);
        final int signum = Integer.signum(diff);
        final int length = str.length();
        final int lendiv = length >> 1;
        final int lenmod = length & 1;

        try {
            if (diff < 0) {
                return str.substring(0, lendiv - count + lenmod)
                        + str.substring(lendiv + count, length);
            }

            return str.substring(count, length - count);
        } catch (final IndexOutOfBoundsException ignored) {
        }

        return EMPTY;
    }

    @UnknownNullability
    @Contract(value = "null, _, _, _ -> param4; _, null, _, _ -> param4; _, _, null, _ -> param4; _, _, _, !null -> !null", pure = true)
    public static String toBaseOrElse(
            @Nullable
            final String str,

            @Nullable
            final String base0,

            @Nullable
            final String base1,

            @Nullable
            final String defaultValue
    ) {
        if (str == null
                || base0 == null
                || base1 == null) {
            return defaultValue;
        }

        return toBaseNoCheck(str, base0, base1);
    }

    @UnknownNullability
    @Contract(value = "!null, !null, !null, _ -> !null", pure = false)
    public static String toBaseOrElse(
            @Nullable
            final String str,

            @Nullable
            final String base0,

            @Nullable
            final String base1,

            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(toBaseOrNull(str, base0, base1), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _, _, _ -> null; _, null, _, _ -> null; _, _, null, _ -> null; !null, !null, !null, _ -> !null", pure = false)
    public static String toBaseOrElse(
            @Nullable
            final String str,

            @Nullable
            final String base0,

            @Nullable
            final String base1,

            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return toBaseOrElse(str, base0, base1, (Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public static String toBaseOrEmpty(
            @Nullable
            final String str,

            @Nullable
            final String base0,

            @Nullable
            final String base1
    ) {
        return toBaseOrElse(str, base0, base1, EMPTY);
    }

    @UnknownNullability
    @Contract(value = "null, _, _ -> param1; _, null, _ -> param1; _, _, null -> param1; !null, _, _ -> !null", pure = true)
    public static String toBaseOrSelf(
            @Nullable
            final String str,

            @Nullable
            final String base0,

            @Nullable
            final String base1
    ) {
        return toBaseOrElse(str, base0, base1, str);
    }

    @UnknownNullability
    @Contract(value = "null, _, _ -> null; _, null, _ -> null; _, _, null -> null; !null, !null, !null -> !null", pure = true)
    public static String toBaseOrNull(
            @Nullable
            final String str,

            @Nullable
            final String base0,

            @Nullable
            final String base1
    ) {
        return toBaseOrElse(str, base0, base1, (@Nullable String) null);
    }

    @NotNull
    @Contract(value = "null, _, _ -> fail; !null, null, _ -> fail; !null, !null, null -> fail", pure = true)
    public static String toBaseNoCheck(
            @UnknownNullability
            final String str,

            @UnknownNullability
            final String base0,

            @UnknownNullability
            final String base1
    ) {
        final int length = str.length();
        if (length == 0) {
            return str;
        }

        final int baseLength0 = base0.length();
        final int baseLength1 = base1.length();
        if (baseLength0 == 0 || baseLength1 == 0) {
            return EMPTY;
        }

        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            final int idx = base0.indexOf(str.charAt(i));
            if (idx == -1) {
                continue;
            }

            sb.append(base1.charAt(idx % baseLength1));
        }


        return sb.toString();
    }

    @UnknownNullability
    @Contract(value = "null, _ -> param2; !null, _ -> !null", pure = true)
    public static String capitalizeOrElse(
            @Nullable
            final String str,

            @Nullable
            final String defaultValue
    ) {
        if (str == null) {
            return defaultValue;
        }

        return capitalizeNoCheck(str);
    }

    @UnknownNullability
    @Contract(value = "!null, _ -> !null", pure = false)
    public static String capitalizeOrElse(
            @Nullable
            final String str,

            @Nullable
            final Supplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return UwObject.ifNull(capitalizeOrNull(str), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null; !null, _ -> !null", pure = false)
    public static String capitalizeOrElse(
            @Nullable
            final String str,

            @Nullable
            final VoidSupplier<@UnknownNullability String> defaultValueSupplier
    ) {
        return capitalizeOrElse(str, (Supplier<@UnknownNullability String>) defaultValueSupplier);
    }

    @NotNull
    @Contract(pure = true)
    public static String capitalizeOrEmpty(
            @Nullable
            final String str
    ) {
        return capitalizeOrElse(str, EMPTY);
    }

    @UnknownNullability
    @Contract(value = "null -> param1; !null -> !null", pure = true)
    public static String capitalizeOrSelf(
            @Nullable
            final String str
    ) {
        return capitalizeOrElse(str, str);
    }

    @UnknownNullability
    @Contract(value = "null -> null; !null -> !null", pure = true)
    public static String capitalizeOrNull(
            @Nullable
            final String str
    ) {
        return capitalizeOrElse(str, (@Nullable String) null);
    }

    @NotNull
    @Contract(value = "null -> fail", pure = true)
    public static String capitalizeNoCheck(
            @UnknownNullability
            final String str
    ) {
        final int length = str.length();
        if (length == 0) {
            return str;
        }

        final String firstLetter = str.substring(0, 1)
                .toUpperCase(Locale.ROOT);

        if (length == 1) {
            return firstLetter;
        }

        return firstLetter + str.substring(1);
    }

    @Contract(value = "-> fail", pure = false)
    private UwString() {
        throw new UnsupportedOperationException();
    }
}
