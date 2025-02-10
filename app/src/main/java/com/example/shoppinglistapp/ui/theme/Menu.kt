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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.shoppinglistapp.ui.theme.Purple90

@OptIn(ExperimentalMaterial3Api::class) // NavigationBar は Experimental
@Composable
fun MyNavigationBar(navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) } // 選択されたアイテムを追跡
    val items = listOf("home", "barcodeScan", "setting")

    NavigationBar(
        modifier =
        Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)), // 上部のみ角丸
        containerColor = Purple90,

        ) {
        items.forEachIndexed { index, item ->
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
                selected = navController.currentDestination?.route == item,
                onClick = { navController.navigate(item) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewScreen() {

}
