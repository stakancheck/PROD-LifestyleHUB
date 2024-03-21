/*
 * Copyright (c) 2024. Artem Sukhanov (Stakancheck)
 * All rights reserved.
 *
 * For inquiries, please contact:
 * Personal Email: stakancheck@gmail.com
 */

package ru.stakancheck.lifestylehub.utils

import ru.stakancheck.api.utils.LoggerBridge
import ru.stakancheck.common.Logger

class LoggerBridgeImpl(private val logger: Logger) : LoggerBridge {
    override fun log(tag: String, message: String) {
        logger.d(tag = tag, message = message)
    }
}
