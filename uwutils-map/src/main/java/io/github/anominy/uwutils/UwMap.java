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

/**
 * A map utility class.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam", "CallToPrintStackTrace"})
public final class UwMap {

    /**
     * An empty map instance.
     */
    @NotNull
    @SuppressWarnings("rawtypes")
    public static final Map EMPTY = Collections.EMPTY_MAP;

    /**
     * Check if provided map is unmodifiable.
     *
     * <p>Wraps {@link #isUnmodifiableNoCheck(Map)}.
     *
     * @param map  map to check for, may be null
     *
     * @return  {@code true} if is unmodifiable
     *          and {@code false} if not
     */
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

    /**
     * Check if provided map is unmodifiable.
     *
     * @param map  map to check for, mustn't be null
     *
     * @return  {@code true} if is unmodifiable
     *          and {@code false} if not
     *
     * @throws NullPointerException if provided map is {@code null}
     */
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

    /**
     * Create unmodifiable view of provided map.
     *
     * <p>Wraps {@link #toUnmodifiableNoCheck(Map)}.
     *
     * @param map   map to create unmodifiable view for, may be null
     *
     * @return  new unmodifiable {@link Map} instance or {@code null}
     *
     * @param <K>   key type
     * @param <V>   value type
     */
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

    /**
     * Create unmodifiable view of provided map.
     *
     * <p>Wraps {@link Collections#unmodifiableMap(Map)}.
     *
     * @param map   map to create unmodifiable view for, mustn't be null
     *
     * @return  new unmodifiable {@link Map} instance
     *
     * @throws NullPointerException if provided map is {@code null}
     *
     * @param <K>   key type
     * @param <V>   value type
     */
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

    /**
     * Get a value from provided map by its key
     * or return a default one on failure.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided map is null.</li>
     *     <li>Provided key is null.</li>
     *     <li>Resulting value is null.</li>
     * </ul>
     *
     * @param map           map to get the value from, may be null
     * @param key           key that corresponds to the value, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  value that corresponds to its key or the default one
     *
     * @param <K>   key type
     * @param <T>   value type
     */
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

    /**
     * Get a value from provided map by its key
     * or return a default one on failure.
     *
     * <p>Wraps {@link #getOrNull(Map, Object)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided map is null.</li>
     *     <li>Provided key is null.</li>
     *     <li>Resulting value is null.</li>
     * </ul>
     *
     * @param map                   map to get the value from, may be null
     * @param key                   key that corresponds to the value, may be null
     * @param defaultValueSupplier  supplier to get the default value from on failure, may be null
     *
     * @return  value that corresponds to its key or the default one
     *
     * @param <K>   key type
     * @param <T>   value type
     */
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

    /**
     * Get a value from provided map by its key
     * or return a default one on failure.
     *
     * <p>Wraps {@link #getOrElse(Map, Object, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided map is null.</li>
     *     <li>Provided key is null.</li>
     *     <li>Resulting value is null.</li>
     * </ul>
     *
     * @param map                   map to get the value from, may be null
     * @param key                   key that corresponds to the value, may be null
     * @param defaultValueSupplier  supplier to get the default value from on failure, may be null
     *
     * @return  value that corresponds to its key or the default one
     *
     * @param <K>   key type
     * @param <T>   value type
     */
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

    /**
     * Get a value from provided map by its key
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #getOrElse(Map, Object, Object)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided map is null.</li>
     *     <li>Provided key is null.</li>
     * </ul>
     *
     * @param map   map to get the value from, may be null
     * @param key   key that corresponds to the value, may be null
     *
     * @return  value that corresponds to its key or {@code null}
     *
     * @param <K>   key type
     * @param <T>   value type
     */
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
