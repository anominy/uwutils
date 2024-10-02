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
import java.util.List;

/**
 * A list utility class.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwList {

    /**
     * An empty list instance.
     */
    @NotNull
    @SuppressWarnings("rawtypes")
    public static final List EMPTY = Collections.EMPTY_LIST;

    /**
     * Check if provided list is unmodifiable.
     *
     * <p>Wraps {@link #isUnmodifiableNoCheck(List)}.
     *
     * @param list  list to check for, may be null
     *
     * @return  {@code true} if is unmodifiable
     *          and {@code false} if not
     */
    @Contract(value = "null -> false", pure = false)
    public static boolean isUnmodifiable(
            @Nullable
            final List<?> list
    ) {
        if (list == null) {
            return false;
        }

        return isUnmodifiableNoCheck(list);
    }

    /**
     * Check if provided list is unmodifiable.
     *
     * <p>Wraps {@link UwCollection#isUnmodifiableNoCheck(Collection)}.
     *
     * @param list  list to check for, mustn't be null
     *
     * @return  {@code true} if is unmodifiable
     *          and {@code false} if not
     *
     * @throws NullPointerException if provided list is {@code null}
     */
    @Contract(value = "null -> fail", pure = false)
    public static boolean isUnmodifiableNoCheck(
            @UnknownNullability
            final List<?> list
    ) {
        return UwCollection.isUnmodifiableNoCheck(list);
    }

    /**
     * Create unmodifiable view of provided list.
     *
     * <p>Wraps {@link #toUnmodifiableNoCheck(List)}.
     *
     * @param list  list to create unmodifiable view for, may be null
     *
     * @return  new unmodifiable {@link List} instance or {@code null}
     *
     * @param <T>   element type
     */
    @UnknownNullability
    @UnmodifiableView
    @Contract(value = "null -> null; !null -> !null", pure = false)
    public static <T> List<@UnknownNullability T> toUnmodifiable(
            @Nullable
            final List<@UnknownNullability T> list
    ) {
        if (list == null) {
            return null;
        }

        return toUnmodifiableNoCheck(list);
    }

    /**
     * Create unmodifiable view of provided list.
     *
     * <p>Wraps {@link Collections#unmodifiableList(List)}.
     *
     * @param list  list to create unmodifiable view for, mustn't be null
     *
     * @return  new unmodifiable {@link List} instance
     *
     * @throws NullPointerException if provided list is {@code null}
     *
     * @param <T>   element type
     */
    @NotNull
    @UnmodifiableView
    @Contract(value = "null -> fail", pure = false)
    public static <T> List<@UnknownNullability T> toUnmodifiableNoCheck(
            @UnknownNullability
            final List<@UnknownNullability T> list
    ) {
        if (isUnmodifiableNoCheck(list)) {
            return list;
        }

        return Collections.unmodifiableList(list);
    }

    @Contract(value = "-> fail", pure = false)
    private UwList() {
        throw new UnsupportedOperationException();
    }
}
