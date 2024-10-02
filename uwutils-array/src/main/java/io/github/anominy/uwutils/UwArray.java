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

/**
 * An array utility class.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwArray {

    /**
     * Get an element from an array by its index
     * or return a default value if the index is out of range
     * or resulting element is null.
     *
     * <p>Wraps {@link #getNoCheck(Object[], int)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided array is null.</li>
     *     <li>Provided index is null.</li>
     *     <li>Resulting element is null.</li>
     * </ul>
     *
     * @param array         array to get element from, may be null
     * @param index         index of element to get, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  element corresponding to its index or the default value
     *
     * @param <T>   element type
     */
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

    /**
     * Get an element from an array by its index
     * or return a default value if the index is out of range
     * or resulting element is null.
     *
     * <p>Wraps {@link #getOrNull(Object[], Integer)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided array is null.</li>
     *     <li>Provided index is null.</li>
     *     <li>Resulting element is null.</li>
     * </ul>
     *
     * @param array                 array to get element from, may be null
     * @param index                 index of element to get, may be null
     * @param defaultValueSupplier  supplier to get the default value from on failure, may be null
     *
     * @return  element corresponding to its index or the default value
     *
     * @param <T>   element type
     */
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

    /**
     * Get an element from an array by its index
     * or return a default value if the index is out of range
     * or resulting element is null.
     *
     * <p>Wraps {@link #getOrElse(Object[], Integer, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided array is null.</li>
     *     <li>Provided index is null.</li>
     *     <li>Resulting element is null.</li>
     * </ul>
     *
     * @param array                 array to get element from, may be null
     * @param index                 index of element to get, may be null
     * @param defaultValueSupplier  supplier to get the default value from on failure, may be null
     *
     * @return  element corresponding to its index or the default value
     *
     * @param <T>   element type
     */
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

    /**
     * Get an element from an array by its index
     * or return {@code null} if the index is out of range.
     *
     * <p>Wraps {@link #getOrElse(Object[], Integer, Object)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided array is null.</li>
     *     <li>Provided index is null.</li>
     *     <li>Resulting element is null.</li>
     * </ul>
     *
     * @param array     array to get element from, may be null
     * @param index     index of element to get, may be null
     *
     * @return  element corresponding to its index or {@code null}
     *
     * @param <T>   element type
     */
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
    /**
     * Get an element from an array by its index
     * or return {@code null} if the index is out of range.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided array is null.</li>
     * </ul>
     *
     * @param array array to get element from, mustn't be null
     * @param index index of element to get, mustn't be null
     *
     * @return  element corresponding to its index or {@code null}
     *
     * @throws NullPointerException if provided array is {@code null}
     *
     * @param <T>   element type
     */
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

    /**
     * Propagate an item to first null element of an array.
     *
     * <p>Wraps {@link #propagateNoCheck(Object[], Object)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided array is null.</li>
     * </ul>
     *
     * @param array     array to propagate the item in, may be null
     * @param item      item to propagate in the array, may be null
     *
     * @return  {@code true} if successfully propagated an item,
     *          {@code false} if failed to propagate an item
     *
     * @param <T>   element type
     */
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

    /**
     * Propagate an item to first null element of an array.
     *
     * @param array     array to propagate the item in, mustn't be null
     * @param item      item to propagate in the array, may be null
     *
     * @return  {@code true} if successfully propagated an item,
     *          {@code false} if failed to propagate an item
     *
     * @throws NullPointerException if provided array is {@code null}
     *
     * @param <T>   element type
     */
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

    /**
     * Consume first non-null elements of an array by passing them to a consumer.
     *
     * <p>Wraps {@link #consumeNoCheck(Object[], Consumer)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided array is null.</li>
     *     <li>Provided consumer is null.</li>
     * </ul>
     *
     * @param array     array of elements to consume, may be null
     * @param consumer  consumer to pass elements to, may be null
     *
     * @return  {@code true} if successfully consumed at least one item,
     *          {@code false} if failed to consume at least one item
     *
     * @param <T>   element type
     */
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

    /**
     * Consume first non-null elements of an array by passing them to a consumer.
     *
     * @param array     array of elements to consume, mustn't be null
     * @param consumer  consumer to pass elements to, mustn't be null
     *
     * @return  {@code true} if successfully consumed at least one item,
     *          {@code false} if failed to consume at least one item
     *
     * @throws NullPointerException if provided array or consumer is {@code null}
     *
     * @param <T>   element type
     */
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

    /**
     * Create an iterable instance for an array.
     *
     * @param array     array to create an iterable instance for, may be null
     *
     * @return  new iterable instance
     *
     * @param <T>   element type
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> java.lang.Iterable<@UnknownNullability T> iterable(
            @Nullable
            final T @Nullable [] array
    ) {
        return new Iterable<>(array);
    }

    /**
     * Create an iterator instance for an array.
     *
     * @param array     array to create an iterator instance for, may be null
     *
     * @return  new iterator instance
     *
     * @param <T>   element type
     */
    @NotNull
    @Contract(value = "_ -> new", pure = true)
    public static <T> java.util.Iterator<@UnknownNullability T> iterator(
            @Nullable
            final T @Nullable [] array
    ) {
        return new Iterator<>(array);
    }

    /**
     * Implementation of {@link java.lang.Iterable} interface for arrays.
     *
     * @param <T>   element type
     */
    public static final class Iterable<T> implements java.lang.Iterable<@UnknownNullability T> {

        /**
         * An array.
         */
        @Nullable
        private final T @Nullable [] array;

        /**
         * Initialize an iterable instance.
         *
         * @param array     array of elements, may be null
         */
        @Contract(pure = true)
        public Iterable(
                @Nullable
                final T @Nullable [] array
        ) {
            this.array = array;
        }

        /**
         * Initialize an iterable instance.
         *
         * <p>Wraps {@link #Iterable(Object[])}
         * w/ {@code null} as the array.
         */
        @Contract(pure = true)
        public Iterable() {
            this(null);
        }

        /**
         * {@inheritDoc}
         */
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

        /**
         * {@inheritDoc}
         */
        @Override
        @NotNull
        @Contract(value = "-> new", pure = true)
        public java.util.Iterator<@UnknownNullability T> iterator() {
            return new UwArray.Iterator<>(this.array);
        }
    }

    /**
     * Implementation of {@link java.util.Iterator} interface for arrays.
     *
     * @param <T>   element type
     */
    public static final class Iterator<T> implements java.util.Iterator<@UnknownNullability T> {

        /**
         * An array.
         */
        @Nullable
        private final T @Nullable [] array;

        /**
         * An index.
         */
        private int index;

        /**
         * Initialize an iterator instance.
         *
         * @param array     array of elements, may be null
         */
        @Contract(pure = true)
        public Iterator(
                @Nullable
                final T @Nullable [] array
        ) {
            this.array = array;
            this.index = -1;
        }

        /**
         * Initialize an iterator instance.
         *
         * <p>Wraps {@link #Iterator(Object[])}
         * w/ {@code null} as the array.
         */
        @Contract(pure = true)
        public Iterator() {
            this(null);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Contract(pure = true)
        public boolean hasNext() {
            return this.array != null
                    && this.index < this.array.length - 1;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        @Nullable
        @Contract(pure = true)
        public T next() {
            return UwArray.getOrNull(this.array, ++this.index);
        }

        /**
         * {@inheritDoc}
         */
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

        /**
         * {@inheritDoc}
         */
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
