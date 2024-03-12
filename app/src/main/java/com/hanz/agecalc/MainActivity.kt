package com.hanz.agecalc

import android.content.Intent
import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerFormatter
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import java.lang.NumberFormatException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            act1InputPage()
        }
    }
}

//fun getAge(getMills: Long?): List<Int> {
//
//    val year: Int = SimpleDateFormat("dd", Locale.getDefault()).format(getMills).toInt()
//    val month: Int = SimpleDateFormat("MM", Locale.getDefault()).format(getMills).toInt()
//    val date: Int = SimpleDateFormat("yyyy", Locale.getDefault()).format(getMills).toInt()
//
//    val curAge: Period = Period.between(
//        LocalDate.of(year, month, date),
//        LocalDate.now()
//    )
//
//    return listOf(curAge.days, curAge.months, curAge.years)
//}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun act1InputPage(){

    fun getAgeSTR(getMillsStr: String?): String {

        if (getMillsStr.isNullOrBlank()){
            return ""
        }

        try {
            val getMills: Long = getMillsStr.toLong()

            val year: Int = SimpleDateFormat("dd", Locale.getDefault()).format(getMills).toInt()
            val month: Int = SimpleDateFormat("MM", Locale.getDefault()).format(getMills).toInt()
            val date: Int = SimpleDateFormat("yyyy", Locale.getDefault()).format(getMills).toInt()
            val curAge: Period = Period.between(
                LocalDate.of(year, month, date),
                LocalDate.now()
            )

            return "$curAge.days/$curAge.months/$curAge.years"

        } catch (e: NumberFormatException) {
            return "Tanggal Lahir User"
        }

    }

    val context = LocalContext.current
    var nama by remember {
        mutableStateOf("")
    }

    val state = rememberDatePickerState()
    val millisToLocalDate = state.selectedDateMillis?.let {
        DateFormatterMills().convertMillisToLocalDate(it)
    }
    val dateToString = millisToLocalDate?.let {
        DateFormatterMills().dateToString(millisToLocalDate)
    } ?: ""

    val ageToString = millisToLocalDate?.let {
        AgeCalculating().calc(millisToLocalDate)
    }

    val openDialog = remember {
        mutableStateOf(false)
    }

    var tglLahirUser by remember {
        mutableStateOf("Pilih Tanggal Lahir")
    }

    Column(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Row ( horizontalArrangement = Arrangement.End,
                  verticalAlignment = Alignment.CenterVertically,
                  modifier = Modifier
                      .width(100.dp)
                      .height(50.dp)) {

                Text(text = "Nama:")
                Spacer(modifier = Modifier.width(14.dp))

            }

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = nama,
                onValueChange = {
                    nama = it
                },
                label = {
                    Row (horizontalArrangement = Arrangement.Center) {
                        Icon(Icons.Rounded.Person, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Row (modifier = Modifier.height(20.dp).align(Alignment.CenterVertically)){
                            Text(text = "Masukin Nama", fontSize = 16.sp, fontWeight = FontWeight.Normal)
                        }

                    }
                }

            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Row (modifier = Modifier.size(100.dp, 50.dp),
                 verticalAlignment = Alignment.CenterVertically,
                 horizontalArrangement = Arrangement.End) {
                Text(text = "Tanggal Lahir: ",
                    modifier = Modifier.width(100.dp))
                    Spacer(modifier = Modifier.width(14.dp))

            }

            Button(onClick = {
                             openDialog.value = true
            }, modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Icon(Icons.Rounded.DateRange, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = tglLahirUser)
            }
            if (openDialog.value) {
                    DatePickerDialog(
                    onDismissRequest = {
                        openDialog.value = false
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                                tglLahirUser = dateToString

                            }
                        ) {
                            Text("OK")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                openDialog.value = false
                            }
                        ) {
                            Text("CANCEL")
                        }
                    }
                )
                {
                    DatePicker(
                        state = state
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(17.dp))

        Row (modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End) {
            Button(
                onClick = {
                    val navigateToResult = Intent(context,ResultPageActivity::class.java)
                    navigateToResult.putStringArrayListExtra("userBirthDate", ageToString)
                    navigateToResult.putExtra("userName", nama)
                    context.startActivity(navigateToResult)
                },
                modifier = Modifier.width(110.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Hitung")
            }
        }


    }
}

@Preview(showBackground = true)
@Composable
fun previewAct1(){
    act1InputPage()
}