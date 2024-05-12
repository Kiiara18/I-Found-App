package com.example.i_found.ui.theme.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.i_found.models.lost
import com.example.i_found.repository.Resources
import com.example.i_found.ui.theme.IfoundTheme
import com.example.i_found.ui.theme.Lblue

@Composable
fun ViewItems(
    homeViewModel: HomeViewModel? = null,
    lost: lost,
    navController: NavHostController



) {
    val homeUIState = homeViewModel?.homeUIState ?: HomeUIState()
    Column (
        modifier = Modifier.fillMaxSize()
    ){
        when (homeUIState.lostList) {
            is Resources.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(16.dp)
                ) {

                    items(
                        homeUIState.lostList.data ?: emptyList()
                    ){
                            item ->
                        Item(lost = lost, onClick = {

                        }) {


                        }
                    }

                }

            }

            else -> {
                Text(
                    text = homeUIState
                        .lostList.throwable?.localizedMessage ?: "Error"
                )
            }

        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Item(
    lost: lost,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {

    Card(
        modifier = Modifier
            .combinedClickable(
                onClick = { onClick.invoke() },
                onLongClick = { onLongClick.invoke() }
            )
            .padding(8.dp)
            .background(Lblue)
            .fillMaxWidth()


    ) {
        Column {
            Text(
                text = lost.name,
                modifier = Modifier.padding(4.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lost.description,
                modifier = Modifier.padding(4.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lost.date,
                modifier = Modifier.padding(4.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lost.contact,
                modifier = Modifier.padding(4.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = lost.location,
                modifier = Modifier.padding(4.dp),
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(4.dp))

            Button(onClick = {

            }) {

            }
        }


    }


}
@Preview(showBackground = true)
@Composable
fun ViewPreview(){
    IfoundTheme {
        ViewItems(lost = lost(), navController = rememberNavController())

    }
}


