package com.example.main.ui.screens



import androidx.compose.ui.graphics.vector.ImageVector
import com.example.main.R
import java.security.KeyStore.TrustedCertificateEntry


// hier "Verwaltungsinfo" zu allen Bildschirmen listen
// ----------------------------------------------------------------

sealed class MyScreens(
    val route: String,
    val titleID: Int = R.string.emptyString,
    val labelID: Int = R.string.emptyString,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null,
    val showBackArrow: Boolean = false,
    val showFab: Boolean = false
) {

    // BottomNavScreens benötigen Title, Label, Icons
    // ----------------------------------------------------------------

    object Main : MyScreens(
        route = "main",                          // eindeutige Kennung
        titleID = R.string.mainScreenTitle,                // Titel in der TopBar
        showFab = true
    )

    object EditToDo : MyScreens(
        route = "edit_todo",
        titleID = R.string.editToDoTitle,
        showBackArrow = true
    )



    companion object {
        val allScreens = listOf<MyScreens>(Main, EditToDo)

        val bottomBarScreens = listOf<MyScreens>()

        fun fromRoute(route: String): MyScreens? =
            allScreens.firstOrNull { it.route == route }
    }
}



