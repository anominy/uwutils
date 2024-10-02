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

import java.util.function.Consumer;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwArray {

    @UnknownNullability
    @Contract(value = "null, _, _ -> param3; _, null, _ -> param3; _, _, !null -> !null", pure = true)
    public static <T> T getOrElse(
            @Nullable
            final T @Nullable [] array,

            @Nullable
            final Integer index,

            @Nullable
            final T defaultValue
    ) {
        if (array == null || index == null) {
            return defaultValue;
        }

        return UwObject.ifNull(getNoCheck(array, index), defaultValue);
    }

    @UnknownNullability
    @Contract(pure = false)
    public static <T> T getOrElse(
            @Nullable
            final T @Nullable [] array,

            @Nullable
            final Integer index,

            @Nullable
            final Supplier<@UnknownNullability T> defaultValueSupplier
    ) {
        return UwObject.ifNull(getOrNull(array, index), defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _, _ -> null; _, null, _ -> null", pure = false)
    public static <T> T getOrElse(
            @Nullable
            final T @Nullable [] array,

            @Nullable
            final Integer index,

            @Nullable
            final VoidSupplier<@UnknownNullability T> defaultValueSupplier
    ) {
        return getOrElse(array, index, (Supplier<@UnknownNullability T>) defaultValueSupplier);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> null; _, null -> null", pure = true)
    public static <T> T getOrNull(
            @Nullable
            final T @Nullable [] array,

            @Nullable
            final Integer index
    ) {
        return getOrElse(array, index, (@Nullable T) null);
    }

    @UnknownNullability
    @Contract(value = "null, _ -> fail", pure = true)
    public static <T> T getNoCheck(
            @Nullable
            final T @UnknownNullability [] array,

            final int index
    ) {
        // `index >= array.length` must be first
        //   to not violate the contract clause.
        if (index >= array.length || index < 0) {
            return null;
        }

        return array[index];
    }

    @Contract(value = "null, _ -> false; _, null -> false", pure = false)
    public static <T> boolean propagate(
            @Nullable
            final T @Nullable [] array,

            @Nullable
            final T item
    ) {
        if (array == null) {
            return false;
        }

        return propagateNoCheck(array, item);
    }

    @Contract(value = "null, _ -> fail", pure = false)
    public static <T> boolean propagateNoCheck(
            @Nullable
            final T @UnknownNullability [] array,

            @Nullable
            final T item
    ) {
        for (int i = 0; i < array.length; ++i) {
            if (array[i] != null) {
                continue;
            }

            array[i] = item;
            return true;
        }

        return false;
    }

    @Contract(value = "null, _ -> false; _, null -> false", pure = false)
    public static <T> boolean consume(
            @Nullable
            final T @Nullable [] array,

            @Nullable
            final Consumer<@UnknownNullability T> consumer
    ) {
        if (array == null || consumer == null) {
            return false;
        }

        return consumeNoCheck(array, consumer);
    }

    @Contract(value = "null, _ -> fail", pure = false)
    public static <T> boolean consumeNoCheck(
            @Nullable
            final T @UnknownNullability [] array,

            @NotNull
            final Consumer<@UnknownNullability T> consumer
    ) {
        boolean result = false;

        for (final T item : array) {
            if (item == null) {
                break;
            }

            consumer.accept(item);
            result = true;
        }

        return result;
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> java.lang.Iterable<@UnknownNullability T> iterable(
            @Nullable
            final T @Nullable [] array
    ) {
        return new Iterable<>(array);
    }

    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> java.util.Iterator<@UnknownNullability T> iterator(
            @Nullable
            final T @Nullable [] array
    ) {
        return new Iterator<>(array);
    }

    public static final class Iterable<T> implements java.lang.Iterable<@UnknownNullability T> {

        @Nullable
        private final T @Nullable [] array;

        @Contract(pure = true)
        public Iterable(
                @Nullable
                final T @Nullable [] array
        ) {
            this.array = array;
        }

        @Contract(pure = true)
        public Iterable() {
            this(null);
        }

        @Override
        @Contract(pure = false)
        public void forEach(
                @Nullable
                final Consumer<? super @UnknownNullability T> action
        ) {
            if (action == null) {
                return;
            }

            java.lang.Iterable.super.forEach(action);
        }

        @Override
        @NotNull
        @Contract(value = "-> new", pure = true)
        public java.util.Iterator<@UnknownNullability T> iterator() {
            return new UwArray.Iterator<>(this.array);
        }
    }

    public static final class Iterator<T> implements java.util.Iterator<@UnknownNullability T> {

        @Nullable
        private final T @Nullable [] array;

        private int index;

        @Contract(pure = true)
        public Iterator(
                @Nullable
                final T @Nullable [] array
        ) {
            this.array = array;
            this.index = -1;
        }

        @Contract(pure = true)
        public Iterator() {
            this(null);
        }

        @Override
        @Contract(pure = true)
        public boolean hasNext() {
            return this.array != null
                    && this.index < this.array.length - 1;
        }

        @Override
        @Nullable
        @Contract(pure = true)
        public T next() {
            return UwArray.getOrNull(this.array, ++this.index);
        }

        @Override
        @Contract(pure = false)
        public void remove() {
            if (this.array == null
                    || this.index < 0
                    || this.index >= this.array.length) {
                return;
            }

            this.array[this.index] = null;
        }

        @Override
        @Contract(pure = false)
        public void forEachRemaining(
                @Nullable
                final Consumer<? super @UnknownNullability T> action
        ) {
            if (action == null) {
                return;
            }

            java.util.Iterator.super.forEachRemaining(action);
        }
    }

    @Contract(value = "-> fail", pure = false)
    private UwArray() {
        throw new UnsupportedOperationException();
    }
}
