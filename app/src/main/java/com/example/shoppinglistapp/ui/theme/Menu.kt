import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
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
import com.example.shoppinglistapp.ui.theme.Purple90

@OptIn(ExperimentalMaterial3Api::class) // NavigationBar は Experimental
@Composable
fun MyNavigationBar() {
    var selectedItem by remember { mutableStateOf(0) } // 選択されたアイテムを追跡

    NavigationBar(
        modifier =
        Modifier
            .background(MaterialTheme.colorScheme.secondary)
            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)), // 上部のみ角丸
        containerColor = Purple90,

        ) {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedItem == 0,
            onClick = { selectedItem = 0 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Favorite") },
            label = { Text("Favorite") },
            selected = selectedItem == 1,
            onClick = { selectedItem = 1 }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart") },
            label = { Text("Cart") },
            selected = selectedItem == 2,
            onClick = { selectedItem = 2 }
        )
    }
}

@Preview
@Composable
fun PreviewScreen() {

}
