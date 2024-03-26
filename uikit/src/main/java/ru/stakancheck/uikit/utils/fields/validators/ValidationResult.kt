/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.uikit.utils.fields.validators

sealed class ValidationResult<out V : Any?> {

    open fun <T : Any?> ValidationResult<T>.nextValidation(
        block: (value: T) -> ValidationResult<T>
    ): ValidationResult<T> {
        return if (this is Success) {
            block(this.value)
        } else {
            this
        }
    }

    class Success<out V : Any?> internal constructor(val value: V) : ValidationResult<V>()
    class Failure internal constructor(val failureText: Int) : ValidationResult<Nothing>()

    companion object {
        fun <V : Any?> success(value: V) = Success(value)

        fun failure(failureText: Int) = Failure(failureText)

        fun <V : Any?> of(value: V): ValidationResult<V> {
            return success(value)
        }

        fun <V : Any?> of(
            value: V,
            block: ValidationResult<V>.() -> ValidationResult<V>
        ): Int? {
            return block(ValidationResultDslContext(value)).validate()
        }
    }
}

fun <V : Any?> ValidationResult<V>.validate(): Int? {
    return when (this) {
        is ValidationResult.Failure -> {
            this.failureText
        }

        is ValidationResultDslContext -> {
            this.errorResult?.failureText
        }

        else -> {
            null
        }
    }
}

internal class ValidationResultDslContext<out V : Any?>(
    val validatedValue: V
) : ValidationResult<V>() {
    internal var errorResult: Failure? = null

    override fun <T> ValidationResult<T>.nextValidation(
        block: (value: T) -> ValidationResult<T>
    ): ValidationResult<T> {
        val currentErrorResult = errorResult
        if (currentErrorResult != null) {
            return currentErrorResult
        }

        this as ValidationResultDslContext
        val result = block(validatedValue)

        if (result is Failure) {
            errorResult = result
        }
        return this
    }
}