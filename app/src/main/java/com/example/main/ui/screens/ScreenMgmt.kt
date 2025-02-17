package com.example.main.ui.screens



import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Timelapse
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Timelapse
import androidx.compose.material.icons.outlined.Timer
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
        labelID = R.string.mainScreenLabel,      // Label in der BottomBar
        selectedIcon = Icons.Filled.Home,       // Icon in der BottomBar
        unselectedIcon = Icons.Outlined.Home,     // Icon in der BottomBar
        showFab = true
    )

    object Timer : MyScreens(
        route = "timer",                          // eindeutige Kennung
        titleID = R.string.timerScreenTitle,      // Titel in der TopBar
        labelID = R.string.timerScreenLabel,      // Label in der BottomBar
        selectedIcon = Icons.Filled.Timer,   // Icon in der BottomBar
        unselectedIcon = Icons.Outlined.Timer, // Icon in der BottomBar
        showFab = false
    )

    object EditToDo : MyScreens(
        route = "edit_todo",
        titleID = R.string.editToDoTitle,
        showBackArrow = true
    )



    companion object {
        val allScreens = listOf<MyScreens>(Main, EditToDo, Timer)

        val bottomBarScreens = listOf<MyScreens>(Main, Timer)

        fun fromRoute(route: String): MyScreens? =
            allScreens.firstOrNull { it.route == route }
    }
}



