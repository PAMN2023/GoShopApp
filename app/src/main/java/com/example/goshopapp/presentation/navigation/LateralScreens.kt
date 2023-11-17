package com.example.goshopapp.presentation.navigation

sealed class LateralScreens(
    val title: String,
    val route: String
) {
    object LoginScreen: LateralScreens("INICIO DE SESIÓN", "login_screen")
    object RegisterScreen: LateralScreens("REGISTRO", "register_screen")
    object ListsScreen: LateralScreens("MIS LISTAS", "lists_screen")
    object ExpensesScreen: LateralScreens("MIS GASTOS", "expenses_screen")
    object FavouritesScreen: LateralScreens("FAVORITOS", "favourites_screen")
    object HistoryScreen: LateralScreens("MI HISTORIAL", "history_screen")
}