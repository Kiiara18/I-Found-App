package com.example.i_found.ui.theme.home

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.accessibility.AccessibilityEventCompat.ContentChangeType
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.i_found.R
import com.example.i_found.models.lost
import com.example.i_found.navigation.ADD_URL
import com.example.i_found.navigation.LOGIN_URL
import com.example.i_found.navigation.VIEW_URL
import com.example.i_found.repository.Resources
import com.example.i_found.ui.theme.IfoundTheme
import com.example.i_found.ui.theme.LGrey
import com.example.i_found.ui.theme.Lblue
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel?,
    navController: NavHostController,


){
    val homeUIState = homeViewModel?.homeUIState?: HomeUIState()

    Column (
        modifier = Modifier
            .fillMaxSize()
        ,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        TopAppBar(
            title = { Text(
                text = "I-Found",
                color = Color.White,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )},
            navigationIcon = {
                IconButton(onClick = {

                    navController.navigate(LOGIN_URL)





                }) {
                    Icon(imageVector = Icons.Default.AccountCircle,
                        contentDescription = "",
                        tint = Color.White)


                }
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(Lblue)

        )
        //End of TopAppBar
        Spacer(modifier = Modifier.height(30.dp))
        Image(
            painter = painterResource(id = R.drawable.question),
            contentDescription ="lostnfound",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(20.dp)
                .clip(CircleShape)
                .size(250.dp))

        //Row 1


            Card(
                modifier = Modifier
                    .size(width = 250.dp, height = 100.dp)
                    .clickable {

                        navController.navigate(ADD_URL)

                    },


                ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Box (
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center){
                        Image(
                            painter = painterResource(id =R.drawable.addlost),
                            contentDescription = "amazon",
                            modifier = Modifier.size(50.dp))

                    }
                    Text(
                        text ="Add items ",
                        fontSize = 20.sp,
                        color = LGrey,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)



                }

            }
            //End of card 1
            Spacer(modifier = Modifier.height(10.dp))


            Card(
                modifier = Modifier
                    .size(width = 250.dp, height = 100.dp)
                    .clickable {

                        navController.navigate(VIEW_URL)


                    },

                ) {
                Spacer(modifier = Modifier.height(10.dp))
                Column {

                    Box (
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center){
                        Image(
                            painter = painterResource(id = R.drawable.view),
                            contentDescription = "amazon",
                            modifier = Modifier.size(50.dp)

                        )

                    }
                    Text(
                        text ="View items ",
                        fontSize = 20.sp,
                        color = LGrey,
                        fontWeight = FontWeight.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center)
                }

            }

        Spacer(modifier = Modifier.height(20.dp))














    }
}
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    IfoundTheme {
        HomeScreen(homeViewModel = null, navController = rememberNavController())

    }
}


