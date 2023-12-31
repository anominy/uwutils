/*
 * Copyright 2023 anominy
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

package io.github.anominy.uwutils.interfaces;

import java.util.concurrent.Callable;

/**
 * A void callable functional interface.
 *
 * <p>A compatible interface w/ {@link Callable}.
 */
@FunctionalInterface
public interface VoidCallable<T> extends Callable<T> {

	/**
	 * Dummy redirect to {@link Callable#call()}.
	 *
	 * @throws Exception	dummy throw
	 */
	@SuppressWarnings("RedundantThrows")
	void call0() throws Exception;

	/**
	 * {@inheritDoc}
	 */
	@Override
	default T call() throws Exception {
		call0(); return null;
	}
}
