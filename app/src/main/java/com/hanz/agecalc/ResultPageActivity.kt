package com.hanz.agecalc

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hanz.agecalc.ui.theme.AgeCalcTheme

class ResultPageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ResultAgeUser(name = intent.getStringExtra("userName"), intent.getStringArrayListExtra("userBirthDate"))
        }
    }
}

@Composable
fun ResultAgeUser(name: String?, userBirthDateFromIntent: ArrayList<String>?) {

    val contextAct2 = LocalContext.current
    val getUserAge = userBirthDateFromIntent ?: arrayListOf()

    val getYearAge = getUserAge.get(0)
    val getMonthAge = getUserAge.get(1)
    val getDayAge = getUserAge.get(2)

    Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text(text = "Hasil Perhitungan Umur", fontSize = 28.sp, modifier = Modifier.background(color = Color.Yellow))

        Spacer(modifier = Modifier.height(20.dp))

        Row (verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Rounded.Person, contentDescription = null)
            Spacer(modifier = Modifier.width(7.dp))
            Text(text = "Tn. $name", fontSize = 23.sp, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column (modifier = Modifier
            .background(color = Color.LightGray)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Berikut Umur Anda Sekarang", fontStyle = FontStyle.Italic, fontSize = 25.sp)
            Spacer(modifier = Modifier.height(20.dp))

            Row (modifier = Modifier.background(Color.Green)) {
                Text(text = "$getYearAge Tahun", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row (modifier = Modifier.background(Color.Cyan)) {
                Text(text = "$getMonthAge Bulan", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(10.dp))

            Row (modifier = Modifier.background(Color.hsl(358F, 0.75F, 0.67F, 1F))) {
                Text(text = "$getDayAge Hari", fontSize = 25.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(20.dp))
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            val navigateBack = Intent(contextAct2, MainActivity::class.java)
            contextAct2.startActivity(navigateBack)
        }) {
            Row (horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically, modifier = Modifier.height(40.dp)){
                Icon(Icons.Rounded.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(7.dp))
                Text(text = "Hitung Lagi", fontSize = 18.sp)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ResultAgeUserPreview() {
    ResultAgeUser(name = "Farhan", arrayListOf("19", "11", "23"))
}