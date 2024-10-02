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

import org.jetbrains.annotations.*;

import java.util.Collections;
import java.util.Map;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "DefaultAnnotationParam", "CallToPrintStackTrace"})
public final class UwMap {

    @NotNull
    @SuppressWarnings("rawtypes")
    public static final Map EMPTY = Collections.EMPTY_MAP;

    @Contract(value = "null -> false", pure = false)
    public static boolean isUnmodifiable(
            @Nullable
            final Map<?, ?> map
    ) {
        if (map == null) {
            return false;
        }

        return isUnmodifiableNoCheck(map);
    }

    @Contract(value = "null -> fail", pure = false)
    @SuppressWarnings("unchecked")
    public static boolean isUnmodifiableNoCheck(
            @UnknownNullability
            final Map<?, ?> map
    ) {
        try {
            map.putAll(EMPTY);

            return false;
        } catch (final UnsupportedOperationException ignored) {
        }

        return true;
    }

    @UnknownNullability
    @UnmodifiableView
    @Contract(value = "null -> null; !null -> !null", pure = false)
    public static <K, V> Map<@UnknownNullability K, @UnknownNullability V> toUnmodifiable(
            @Nullable
            final Map<@UnknownNullability K, @UnknownNullability V> map
    ) {
        if (map == null) {
            return null;
        }

        return toUnmodifiableNoCheck(map);
    }

    @NotNull
    @UnmodifiableView
    @Contract(value = "null -> fail", pure = false)
    public static <K, V> Map<@UnknownNullability K, @UnknownNullability V> toUnmodifiableNoCheck(
            @UnknownNullability
            final Map<@UnknownNullability K, @UnknownNullability V> map
    ) {
        if (isUnmodifiableNoCheck(map)) {
            return map;
        }

        return Collections.unmodifiableMap(map);
    }

    @UnknownNullability
    @Contract(value = "null, _, _ -> param3; _, _, !null -> !null", pure = false)
    public static <K, T> T getOrElse(
            @Nullable
            final Map<@UnknownNullability K, @UnknownNullability T> map,

            @Nullable
            final K key,

            @Nullable
            final T defaultValue
    ) {
        if (map == null) {
            return defaultValue;
        }

        try {
            return map.getOrDefault(key, defaultValue);
        } catch (final ClassCastException
                | NullPointerException
                | UnsupportedOperationException e) {
            e.printStackTrace();
        }

        return defaultValue;
    }

    @UnknownNullability
    @Contract(pure = false)
    public <K, T> T getOrElse(
            @Nullable
            final Map<@UnknownNullability K, @UnknownNullability T> map,

            @Nullable
            final K key,

            @Nullable
            final Supplier<@UnknownNullability T> defaultValueSupplier
    ) {
        return UwObject.ifNull(getOrNull(map, key), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _, _ -> null", pure = false)
    public <K, T> T getOrElse(
            @Nullable
            final Map<@UnknownNullability K, @UnknownNullability T> map,

            @Nullable
            final K key,

            @Nullable
            final VoidSupplier<@UnknownNullability T> defaultValueSupplier
    ) {
        return getOrElse(map, key, (Supplier<@UnknownNullability T>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null", pure = false)
    public <K, T> T getOrNull(
            @Nullable
            final Map<@UnknownNullability K, @UnknownNullability T> map,

            @Nullable
            final K key
    ) {
        return getOrElse(map, key, (T) null);
    }

    @Contract(value = "-> fail", pure = false)
    private UwMap() {
        throw new UnsupportedOperationException();
    }
}
