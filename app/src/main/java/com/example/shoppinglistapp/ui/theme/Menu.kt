import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.shoppinglistapp.ui.theme.Purple90

@OptIn(ExperimentalMaterial3Api::class) // NavigationBar は Experimental
@Composable
fun MyNavigationBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) } // 選択されたアイテムを追跡
    val items = listOf("home", "barcodeScan", "setting")

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        modifier =
        Modifier
            .background(MaterialTheme.colorScheme.primary)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)), // 上部のみ角丸
        containerColor = Purple90,

        ) {
        items.forEachIndexed { index, item ->
            val isSelected = currentRoute == item
            NavigationBarItem(
                icon = {
                    when (item) {
                        "home" -> Icon(Icons.Filled.Home, contentDescription = "Home")
                        "barcodeScan" -> Icon(
                            Icons.Filled.Search,
                            contentDescription = "BarcodeScan"
                        )

                        "setting" -> Icon(Icons.Filled.Settings, contentDescription = "Setting")
                    }
                },
                label = { Text(item.replaceFirstChar { it.uppercase() }) },
                selected = isSelected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.Black,
                    selectedTextColor = Color.Black,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray
                ),
                onClick = { navController.navigate(item)
                println("test!!!123 ${item} ${currentRoute}")
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {

}
