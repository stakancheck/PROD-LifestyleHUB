/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.data

import ru.stakancheck.common.Logger

class LoggerTestImpl : Logger {
    override fun d(tag: String, message: String) {
        println("DEBUG: [$tag] $message")
    }

    override fun e(tag: String, message: String) {
        println("ERROR: [$tag] $message")
    }
}
