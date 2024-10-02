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

import java.util.Collection;
import java.util.Collections;

/**
 * A collection utility class.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwCollection {

    /**
     * An empty collection instance.
     */
    @NotNull
    @SuppressWarnings("rawtypes")
    public static final Collection EMPTY = Collections.EMPTY_LIST;

    /**
     * Check if provided collection is unmodifiable.
     *
     * <p>Wraps {@link #isUnmodifiableNoCheck(Collection)}.
     *
     * @param collection    collection to check for, may be null
     *
     * @return  {@code true} if is unmodifiable
     *          and {@code false} if not
     */
    @Contract(value = "null -> false", pure = false)
    public static boolean isUnmodifiable(
            @Nullable
            final Collection<?> collection
    ) {
        if (collection == null) {
            return false;
        }

        return isUnmodifiableNoCheck(collection);
    }
    /**
     * Check if provided collection is unmodifiable.
     *
     * @param collection    collection to check for, mustn't be null
     *
     * @return  {@code true} if is unmodifiable
     *          and {@code false} if not
     *
     * @throws NullPointerException if provided collection is {@code null}
     */
    @Contract(value = "null -> fail", pure = false)
    @SuppressWarnings("unchecked")
    public static boolean isUnmodifiableNoCheck(
            @UnknownNullability
            final Collection<?> collection
    ) {
        try {
            collection.addAll(EMPTY);

            return false;
        } catch (UnsupportedOperationException ignored) {
        }

        return true;
    }

    /**
     * Create unmodifiable view of provided collection.
     *
     * <p>Wraps {@link #toUnmodifiableNoCheck(Collection)}.
     *
     * @param collection    collection to create unmodifiable view for, may be null
     *
     * @return  new unmodifiable {@link Collection} instance or {@code null}
     *
     * @param <T>   element type
     */
    @UnknownNullability
    @UnmodifiableView
    @Contract(value = "null -> null; !null -> !null", pure = false)
    public static <T> Collection<@UnknownNullability T> toUnmodifiable(
            @Nullable
            final Collection<@UnknownNullability T> collection
    ) {
        if (collection == null) {
            return null;
        }

        return toUnmodifiableNoCheck(collection);
    }

    /**
     * Create unmodifiable view of provided collection.
     *
     * <p>Wraps {@link Collections#unmodifiableCollection(Collection)}.
     *
     * @param collection    collection to create unmodifiable view for, mustn't be null
     *
     * @return  new unmodifiable {@link Collection} instance
     *
     * @throws NullPointerException if provided collection is {@code null}
     *
     * @param <T>   element type
     */
    @NotNull
    @UnmodifiableView
    @Contract(value = "null -> fail", pure = false)
    public static <T> Collection<@UnknownNullability T> toUnmodifiableNoCheck(
            @UnknownNullability
            final Collection<@UnknownNullability T> collection
    ) {
        if (isUnmodifiableNoCheck(collection)) {
            return collection;
        }

        return Collections.unmodifiableCollection(collection);
    }

    @Contract(value = "-> fail", pure = false)
    private UwCollection() {
        throw new UnsupportedOperationException();
    }
}
