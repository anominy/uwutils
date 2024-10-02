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

import java.util.concurrent.Callable;

/**
 * Implementation of {@link Callable} w/ void return type.
 *
 * @param <T>   dummy type
 */
@FunctionalInterface
@SuppressWarnings({"unused", "RedundantThrows", "DefaultAnnotationParam"})
public interface VoidCallable<T> extends Callable<@UnknownNullability T> {

    /**
     * @see #call()
     */
    @Contract(pure = false)
    void call0() throws Exception;

    /**
     * {@inheritDoc}
     */
    @Override
    @Nullable
    @Contract(value = "-> null", pure = false)
    default T call() throws Exception {
        this.call0();
        return null;
    }
}
