package com.zozi.shared

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*

actual fun platformClientEngine(): HttpClientEngineFactory<*> = OkHttp
