package com.example.goshopapp.presentation.screens.userlists

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.goshopapp.data.FirebaseAuth
import com.example.goshopapp.data.FirebaseFirestoreManage
import com.example.goshopapp.presentation.navigation.AppScreens
import com.example.goshopapp.presentation.screens.login.LoginScreen
import com.google.firebase.firestore.CollectionReference

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListsScreen(navController: NavHostController) {
    if (userId() == "") {
        navController.navigate(AppScreens.HomeScreen.route)
    }
    val userId = userId()
    val userLists = getUserLists(userId)
}

private fun getUserLists(uid: String): CollectionReference {
    val storeManager = FirebaseFirestoreManage()
    return storeManager.getUserLists(uid)
}

private fun userId(): String {
    val authManager = FirebaseAuth()
    if (authManager.getCurrentUserId() == null || authManager.getCurrentUserId() == "") {
        return ""
    } else {
        return authManager.getCurrentUserId()!!
    }
}

@Preview(showBackground = true)
@Composable
fun UserListsPreview() {
    val navController = rememberNavController()
    UserListsScreen(navController)
}