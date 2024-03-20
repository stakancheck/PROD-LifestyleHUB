/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.common

import java.util.concurrent.CopyOnWriteArrayList

class ErrorCollector {
    private val observers = CopyOnWriteArrayList<ErrorObserver>()

    fun subscribe(observer: ErrorObserver) {
        observers.add(observer)
    }

    fun unsubscribe(observer: ErrorObserver) {
        observers.remove(observer)
    }

    fun notifyError(error: Throwable) {
        observers.forEach { it.onError(error) }
    }
}
