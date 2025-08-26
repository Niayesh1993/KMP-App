package com.zozi.shared

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*

actual fun platformClientEngine(): HttpClientEngineFactory<*> = Darwin
