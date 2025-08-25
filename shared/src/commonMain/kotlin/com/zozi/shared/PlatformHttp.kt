package com.zozi.shared

import io.ktor.client.engine.*

expect fun platformClientEngine(): HttpClientEngineFactory<*>
