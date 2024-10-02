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
import java.util.Set;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwSet {

    @NotNull
    @SuppressWarnings("rawtypes")
    public static final Set EMPTY = Collections.EMPTY_SET;

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

    @Contract(value = "null -> fail", pure = false)
    public static boolean isUnmodifiableNoCheck(
            @UnknownNullability
            final Set<?> set
    ) {
        return UwCollection.isUnmodifiableNoCheck(set);
    }

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
