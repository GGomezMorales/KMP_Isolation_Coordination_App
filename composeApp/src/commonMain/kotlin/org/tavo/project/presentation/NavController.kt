package org.tavo.project.presentation

// shared/src/commonMain/kotlin/com/example/shared/navigation/NavController.kt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class NavController(
    startDestination: Screen
) {
    private val _backStack = MutableStateFlow(listOf(startDestination))
    val backStack = _backStack.asStateFlow()

    val currentScreen: Screen
        get() = _backStack.value.last()

    fun navigate(route: Screen) {
        _backStack.value = _backStack.value + route
    }

    fun navigateReplace(route: Screen) {
        _backStack.value = _backStack.value.dropLast(1) + route
    }

    fun popBackStack() {
        if (_backStack.value.size > 1) {
            _backStack.value = _backStack.value.dropLast(1)
        }
    }

    fun resetToRoot() {
        _backStack.value = listOf(_backStack.value.first())
    }
}

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Detail : Screen("detail")
    object Settings : Screen("settings")

    data class ItemDetail(val itemId: String) : Screen("detail/{itemId}") {
        companion object {
            const val KEY_ITEM_ID = "itemId"
            fun createRoute(itemId: String) = "detail/$itemId"
        }
    }
}