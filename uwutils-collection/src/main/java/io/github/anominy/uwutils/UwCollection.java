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

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwCollection {

    @NotNull
    @SuppressWarnings("rawtypes")
    public static final Collection EMPTY = Collections.EMPTY_LIST;

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
