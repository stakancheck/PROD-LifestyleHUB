/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */


package ru.stakancheck.uikit.utils.fields.validators

fun ValidationResult<String>.notEmpty(errorText: Int) = nextValidation { value ->
    if (value.isNotEmpty()) {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}

fun ValidationResult<String>.notBlank(errorText: Int) = nextValidation { value ->
    if (value.isNotBlank()) {
        ValidationResult.success(value)
    } else {
        ValidationResult.failure(errorText)
    }
}

fun ValidationResult<String>.minLength(errorText: Int, minLength: Int = 0) =
    nextValidation { value ->
        if (value.length < minLength) {
            ValidationResult.failure(errorText)
        } else {
            ValidationResult.success(value)
        }
    }

fun ValidationResult<String>.maxLength(errorText: Int, maxLength: Int = 0) =
    nextValidation { value ->
        if (value.length > maxLength) {
            ValidationResult.failure(errorText)
        } else {
            ValidationResult.success(value)
        }
    }

fun ValidationResult<String>.matchRegex(errorText: Int, regex: Regex, canBeBlank: Boolean = false) =
    nextValidation { value ->
        if (canBeBlank && value.isBlank()) {
            ValidationResult.success(value)
        } else {
            if (regex.matches(value)) {
                ValidationResult.success(value)
            } else {
                ValidationResult.failure(errorText)
            }
        }
    }

fun ValidationResult<String>.containedIn(errorText: Int, validValues: List<String>) =
    nextValidation { value ->
        if (validValues.contains(value)) {
            ValidationResult.success(value)
        } else {
            ValidationResult.failure(errorText)
        }
    }

inline fun <reified D> ValidationResult<D?>.notNull(errorText: Int) =
    nextValidation { value ->
        if (value != null) {
            ValidationResult.success(value)
        } else {
            ValidationResult.failure(errorText)
        }
    }
