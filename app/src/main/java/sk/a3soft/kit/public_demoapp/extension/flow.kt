package sk.a3soft.kit.public_demoapp.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

/**
 * The timeout in ms before cancelling the block if there are no active observers.
 *
 * Docs and value copied from the constant used with _liveData_ builder.
 */
private const val DEFAULT_TIMEOUT = 5_000L

/**
 * Converts _Flow_ to _StateFlow_ with WhileSubscribed starting strategy.
 *
 * @param scope The coroutine scope in which sharing is started.
 * @param initialValue The initial value of the state flow.
 * @param stopTimeoutMillis Configures a delay (in milliseconds) between the disappearance of the last
 *   subscriber and the stopping of the sharing coroutine. It defaults to value used in _liveData_ builder.
 */
fun <T> Flow<T>.stateInWhileSubscribed(
    scope: CoroutineScope,
    initialValue: T,
    stopTimeoutMillis: Long = DEFAULT_TIMEOUT,
): StateFlow<T> = stateIn(scope, SharingStarted.WhileSubscribed(stopTimeoutMillis), initialValue)
