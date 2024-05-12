package com.example.i_found.items

import android.annotation.SuppressLint
import android.graphics.Matrix.ScaleToFit
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.i_found.R
import com.example.i_found.models.lost
import com.example.i_found.ui.theme.IfoundTheme
import com.example.i_found.ui.theme.Lblue
import com.example.i_found.ui.theme.pblue
import com.google.firebase.firestore.DocumentId
import kotlinx.coroutines.launch

object Utils{
    val colors = listOf(
        Color(0xFF6672B4),
        Color(0xFF56ABEE),
        Color(0xFFA7CDEB)

    )
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsScreen(
    itemViewModel: ItemViewModel?,
    lostId: String,
    navController: NavHostController
){
    val itemUIState = itemViewModel?.itemUIState ?:ItemUIState()

    val islostNotBlank = itemUIState.lost.isNotBlank() &&
            itemUIState.name.isNotBlank()

    val islostIdNotBlank = lostId.isNotBlank()

    LaunchedEffect(key1 = Unit) {
        if (islostIdNotBlank){
            itemViewModel?.getLost(lostId)


        }else{
            itemViewModel?.resetState()
        }

    }

    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (islostIdNotBlank){
                        itemViewModel?.updateLost(lostId)
                    }else{
                        itemViewModel?.addItem()
                    }
                }) {
                Icon(
                    imageVector = Icons.Default.Check , contentDescription = null )
                
            }
        }


    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(pblue),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            if (itemUIState.itemAddedStatus){
                scope.launch {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Added Item Successfully")
                    itemViewModel?.resetItemAddedStatus()

                }



            }
            if (itemUIState.updateItemStatus){
                scope.launch {
                    scaffoldState.snackbarHostState
                        .showSnackbar("Item updated")
                    itemViewModel?.resetItemAddedStatus()

                }
            }
            Text(
                text = "Add items",
                color = Color.Black,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif
            )
            Spacer(modifier = Modifier.height(20.dp))




            OutlinedTextField(
                value = itemUIState.name ,
                onValueChange = {
                    itemViewModel?.onNameChange(it)
                },
                label = {Text(text = " Item Name")},

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = itemUIState.location ,
                onValueChange = {
                    itemViewModel?.onlocationChange(it)
                },
                label = {Text(text = " Location")},

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = itemUIState.contact,
                onValueChange = {
                  itemViewModel?.oncontactChange(it)
                },
                label = {Text(text = " Contact")},

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = itemUIState.date ,
                onValueChange = {
                    itemViewModel?.ondateChange(it)
                },
                label = {Text(text = "Date dd/mm/yy")},

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)

            )

            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = itemUIState.description,
                onValueChange = {
                    itemViewModel?.ondescriptionChange(it)
                },
                label = {Text(text = "Description")},
                modifier = Modifier.size(width = 280.dp, height = 140.dp),

                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)

            )
            Spacer(modifier = Modifier.height(10.dp))
            Image(
                painter = painterResource(id = R.drawable.fill),
                contentDescription ="lostnfound",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(20.dp)
                    .clip(CircleShape)
                    .size(200.dp))

        }
        
    }

}
@Preview(showBackground = true)
@Composable
fun PreviewItemScreen(){
    IfoundTheme{
        ItemsScreen(itemViewModel = null, lostId = String() , navController = rememberNavController() )

    }
}