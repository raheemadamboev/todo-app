package xyz.teamgravity.todo.presentation.component.misc

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Composable
fun <T> ObserveEvent(
    flow: Flow<T>,
    onEvent: suspend (event: T) -> Unit
) {
    val owner = LocalLifecycleOwner.current
    LaunchedEffect(
        key1 = flow,
        key2 = owner.lifecycle,
        block = {
            owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                withContext(Dispatchers.Main.immediate) {
                    flow.collect(onEvent)
                }
            }
        }
    )
}