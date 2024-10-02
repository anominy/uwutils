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
import java.util.Set;

/**
 * A set utility class.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwSet {

    /**
     * An empty set instance.
     */
    @NotNull
    @SuppressWarnings("rawtypes")
    public static final Set EMPTY = Collections.EMPTY_SET;

    /**
     * Check if provided set is unmodifiable.
     *
     * <p>Wraps {@link #isUnmodifiableNoCheck(Set)}.
     *
     * @param set   set to check for, may be null
     *
     * @return  {@code true} if is unmodifiable
     *          and {@code false} if not
     */
    @Contract(value = "null -> false", pure = false)
    public static boolean isUnmodifiable(
            @Nullable
            final Set<?> set
    ) {
        if (set == null) {
            return false;
        }

        return isUnmodifiableNoCheck(set);
    }

    /**
     * Check if provided set is unmodifiable.
     *
     * <p>Wraps {@link UwCollection#isUnmodifiableNoCheck(Collection)}.
     *
     * @param set  set to check for, mustn't be null
     *
     * @return  {@code true} if is unmodifiable
     *          and {@code false} if not
     *
     * @throws NullPointerException if provided set is {@code null}
     */
    @Contract(value = "null -> fail", pure = false)
    public static boolean isUnmodifiableNoCheck(
            @UnknownNullability
            final Set<?> set
    ) {
        return UwCollection.isUnmodifiableNoCheck(set);
    }

    /**
     * Create unmodifiable view of provided set.
     *
     * <p>Wraps {@link #toUnmodifiableNoCheck(Set)}.
     *
     * @param set  set to create unmodifiable view for, may be null
     *
     * @return  new unmodifiable {@link Set} instance or {@code null}
     *
     * @param <T>   element type
     */
    @UnknownNullability
    @UnmodifiableView
    @Contract(value = "null -> null; !null -> !null", pure = false)
    public static <T> Set<@UnknownNullability T> toUnmodifiable(
            @Nullable
            final Set<@UnknownNullability T> set
    ) {
        if (set == null) {
            return null;
        }

        return toUnmodifiableNoCheck(set);
    }

    /**
     * Create unmodifiable view of provided set.
     *
     * <p>Wraps {@link Collections#unmodifiableSet(Set)}.
     *
     * @param set  set to create unmodifiable view for, mustn't be null
     *
     * @return  new unmodifiable {@link Set} instance
     *
     * @throws NullPointerException if provided set is {@code null}
     *
     * @param <T>   element type
     */
    @NotNull
    @UnmodifiableView
    @Contract(value = "null -> fail", pure = false)
    public static <T> Set<@UnknownNullability T> toUnmodifiableNoCheck(
            @UnknownNullability
            final Set<@UnknownNullability T> set
    ) {
        if (isUnmodifiableNoCheck(set)) {
            return set;
        }

        return Collections.unmodifiableSet(set);
    }

    @Contract(value = "-> fail", pure = false)
    private UwSet() {
        throw new UnsupportedOperationException();
    }
}
