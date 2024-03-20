/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data

/**
 * A sealed class that represents a resource that can either be successful or contain an error.
 *
 * @param E The type of the data.
 * @param data The data of the resource. Can be null if the resource represents an error.
 */
sealed class Resource<out E : Any>(open val data: E? = null) {
    /**
     * Represents a successful resource.
     *
     * @param E The type of the data.
     * @param data The data of the resource.
     */
    class Success<E : Any>(override val data: E) : Resource<E>(data)

    /**
     * Represents a resource that contains an error.
     *
     * @param E The type of the data.
     * @param data The data of the resource. Can be null if the resource represents an error.
     * @param error The error of the resource. Can be null if the resource represents a success.
     */
    class Error<E : Any>(data: E? = null, val error: Throwable? = null) : Resource<E>(data)
}
