package com.hanz.agecalc

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.Period

class AgeCalculating {
    @RequiresApi(Build.VERSION_CODES.O)
    fun calc(birthDate: LocalDate): ArrayList<String>{
        val curAge: Period = Period.between(
        birthDate,
        LocalDate.now()
    )
    return arrayListOf(curAge.years.toString(), curAge.months.toString(),  curAge.days.toString())
    }

}