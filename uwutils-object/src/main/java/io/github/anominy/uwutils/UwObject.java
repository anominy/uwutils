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
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * An object utility class.
 */
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
public final class UwObject {

    /**
     * Apply provided object to a function if object and function aren't null.
     * And return a default value if resulting one is null.
     *
     * @param object        object to apply, may be null
     * @param function      function to invoke, may be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  result of function apply or the default value
     *
     * @param <T>   object type
     * @param <R>   return type
     */
    @UnknownNullability
    @Contract(value = "null, _, _ -> param3; _, null, _ -> param3; _, _, !null -> !null", pure = false)
    public static <T, R> R ifNotNull(
            @Nullable
            final T object,

            @Nullable
            final Function<@UnknownNullability T, @UnknownNullability R> function,

            @Nullable
            final R defaultValue
    ) {
        if (object == null || function == null) {
            return defaultValue;
        }

        return ifNull(function.apply(object), defaultValue);
    }

    /**
     * Apply provided object to a function if object and function aren't null.
     * And return a default value if resulting one is null.
     *
     * @param object                object to apply, may be null
     * @param function              function to invoke, may be null
     * @param defaultValueSupplier  supplier to get the default value from on failure, may be null
     *
     * @return  result of function apply or the default value
     *
     * @param <T>   object type
     * @param <R>   return type
     */
    @UnknownNullability
    @Contract(value = "null, _, null -> null; _, null, null -> null", pure = false)
    public static <T, R> R ifNotNull(
            @Nullable
            final T object,

            @Nullable
            final Function<@UnknownNullability T, @UnknownNullability R> function,

            @Nullable
            final Supplier<@UnknownNullability R> defaultValueSupplier
    ) {
        if (object == null || function == null) {
            return ifNotNullNoCheck(defaultValueSupplier, Supplier::get);
        }

        return ifNull(function.apply(object), defaultValueSupplier);
    }

    /**
     * Apply provided object to a function if object and function aren't null.
     * And return a default value if resulting one is null.
     *
     * <p>Wraps {@link #ifNotNull(Object, Function, Supplier)}.
     *
     * @param object                object to apply, may be null
     * @param function              function to invoke, may be null
     * @param defaultValueSupplier  supplier to get the default value from on failure, may be null
     *
     * @return  result of function apply or the default value
     *
     * @param <T>   object type
     * @param <R>   return type
     */
    @UnknownNullability
    @Contract(value = "null, _, _ -> null; _, null, _ -> null", pure = false)
    public static <T, R> R ifNotNull(
            @Nullable
            final T object,

            @Nullable
            final Function<@UnknownNullability T, @UnknownNullability R> function,

            @Nullable
            final VoidSupplier<@UnknownNullability R> defaultValueSupplier
    ) {
        return ifNotNull(object, function, (Supplier<@UnknownNullability R>) defaultValueSupplier);
    }

    /**
     * Apply provided object to a function if object and function aren't null.
     *
     * <p>Wraps {@link #ifNotNull(Object, Function, Object)}
     * w/ {@code null} as the default value.
     *
     * @param object    object to apply, may be null
     * @param function  function to invoke, may be null
     *
     * @return  result of function apply or {@code null}
     *
     * @param <T>   object type
     * @param <R>   return type
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null; _, null -> null", pure = false)
    public static <T, R> R ifNotNull(
            @Nullable
            final T object,

            @Nullable
            final Function<@UnknownNullability T, @UnknownNullability R> function
    ) {
        return ifNotNull(object, function, (@Nullable R) null);
    }

    /**
     * Apply provided object to a function if object isn't null
     * or return a default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided object is null.</li>
     * </ul>
     *
     * @param object        object to apply, may be null
     * @param function      function to invoke, mustn't be null
     * @param defaultValue  default value to return on failure, may be null
     *
     * @return  result of function apply or the default value
     *
     * @throws NullPointerException if provided function is {@code null} and object isn't
     *
     * @param <T>   object type
     * @param <R>   return type
     */
    @UnknownNullability
    @Contract(value = "null, _, _ -> param3; !null, null, _ -> fail", pure = false)
    public static <T, R> R ifNotNullNoCheck(
            @Nullable
            final T object,

            @UnknownNullability
            final Function<@UnknownNullability T, @UnknownNullability R> function,

            @Nullable
            final R defaultValue
    ) {
        if (object == null) {
            return defaultValue;
        }

        return function.apply(object);
    }

    /**
     * Apply provided object to a function if object isn't null
     * or return a default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided object is null.</li>
     * </ul>
     *
     * @param object                object to apply, may be null
     * @param function              function to invoke, mustn't be null
     * @param defaultValueSupplier  supplier to get the default value from on failure, mustn't be null
     *
     * @return  result of function apply or the default value
     *
     * @throws NullPointerException if provided object and default value supplier are {@code null},
     *                              if provided function is {@code null} and object isn't
     *
     * @param <T>   object type
     * @param <R>   return type
     */
    @UnknownNullability
    @Contract(value = "null, _, null -> fail; !null, null, _ -> fail", pure = false)
    public static <T, R> R ifNotNullNoCheck(
            @Nullable
            final T object,

            @UnknownNullability
            final Function<@UnknownNullability T, @UnknownNullability R> function,

            @UnknownNullability
            final Supplier<@UnknownNullability R> defaultValueSupplier
    ) {
        if (object == null) {
            return defaultValueSupplier.get();
        }

        return function.apply(object);
    }

    /**
     * Apply provided object to a function if object isn't null
     * or return a default value.
     *
     * <p>Wraps {@link #ifNotNullNoCheck(Object, Function, Supplier)}.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided object is null.</li>
     * </ul>
     *
     * @param object                object to apply, may be null
     * @param function              function to invoke, mustn't be null
     * @param defaultValueSupplier  supplier to get the default value from on failure, mustn't be null
     *
     * @return  result of function apply or the default value
     *
     * @throws NullPointerException if provided object and default value supplier are {@code null},
     *                              if provided function is {@code null} and object isn't
     *
     * @param <T>   object type
     * @param <R>   return type
     */
    @UnknownNullability
    @Contract(value = "null, _, !null -> null; !null, null, _ -> fail", pure = false)
    public static <T, R> R ifNotNullNoCheck(
            @Nullable
            final T object,

            @UnknownNullability
            final Function<@UnknownNullability T, @UnknownNullability R> function,

            @UnknownNullability
            final VoidSupplier<@UnknownNullability R> defaultValueSupplier
    ) {
        return ifNotNullNoCheck(object, function, (Supplier<@UnknownNullability R>) defaultValueSupplier);
    }

    /**
     * Apply provided object to a function if object isn't null
     * or return {@code null} on failure.
     *
     * <p>Wraps {@link #ifNotNullNoCheck(Object, Function, Object)}
     * w/ {@code null} as the default value.
     *
     * <p>Possible failure cases:
     * <ul>
     *     <li>Provided object is null.</li>
     * </ul>
     *
     * @param object    object to apply, may be null
     * @param function  function to invoke, mustn't be null
     *
     * @return  result of function apply or {@code null}
     *
     * @throws NullPointerException if provided function is {@code null} and object isn't
     *
     * @param <T>   object type
     * @param <R>   return type
     */
    @UnknownNullability
    @Contract(value = "null, _ -> null; !null, null -> fail", pure = false)
    public static <T, R> R ifNotNullNoCheck(
            @Nullable
            final T object,

            @UnknownNullability
            final Function<@UnknownNullability T, @UnknownNullability R> function
    ) {
        return ifNotNullNoCheck(object, function, (@Nullable R) null);
    }

    /**
     * Return provided object if it isn't null
     * or return a default value.
     *
     * @param object    object to check, may be null
     * @param value     default value to return, may be null
     *
     * @return  provided object or if it's null the default value
     *
     * @param <T>   object type
     */
    @UnknownNullability
    @Contract(value = "null, _ -> param2; !null, _ -> param1", pure = true)
    public static <T> T ifNull(
            @Nullable
            final T object,

            @Nullable
            final T value
    ) {
        if (object == null) {
            return value;
        }

        return object;
    }

    /**
     * Return provided object if it isn't null
     * or return a default value.
     *
     * @param object    object to check, may be null
     * @param supplier  supplier to get the default value, may be null
     *
     * @return  provided object or if it's null the default value
     *
     * @param <T>   object type
     */
    @UnknownNullability
    @Contract(value = "null, null -> null; !null, _ -> param1", pure = false)
    public static <T> T ifNull(
            @Nullable
            final T object,

            @Nullable
            final Supplier<@UnknownNullability T> supplier
    ) {
        if (object == null) {
            return ifNotNullNoCheck(supplier, Supplier::get);
        }

        return object;
    }

    /**
     * Return provided object if it isn't null
     * or return a default value.
     *
     * @param object    object to check, may be null
     * @param supplier  supplier to get the default value from, mustn't be null
     *
     * @return  provided object or if it's null the default value
     *
     * @throws NullPointerException if provided object and supplier are {@code null}
     *
     * @param <T>   object type
     */
    @UnknownNullability
    @Contract(value = "!null, _ -> param1; null, null -> fail", pure = false)
    public static <T> T ifNullNoCheck(
            @Nullable
            final T object,

            @UnknownNullability
            final Supplier<@UnknownNullability T> supplier
    ) {
        if (object == null) {
            return supplier.get();
        }

        return object;
    }

    /**
     * Return provided object if it isn't null
     * or return a default value.
     *
     * <p>Wraps {@link #ifNullNoCheck(Object, Supplier)}.
     *
     * @param object    object to check, may be null
     * @param supplier  supplier to get the default value from, mustn't be null
     *
     * @return  provided object or if it's null the default value
     *
     * @throws NullPointerException if provided object and supplier are {@code null}
     *
     * @param <T>   object type
     */
    @UnknownNullability
    @Contract(value = "null, !null -> null; !null, _ -> param1; null, null -> fail", pure = false)
    public static <T> T ifNullNoCheck(
            @Nullable
            final T object,

            @UnknownNullability
            final VoidSupplier<@UnknownNullability T> supplier
    ) {
        return ifNullNoCheck(object, (Supplier<@UnknownNullability T>) supplier);
    }

    @Contract(value = "-> fail", pure = false)
    private UwObject() {
        throw new UnsupportedOperationException();
    }
}
