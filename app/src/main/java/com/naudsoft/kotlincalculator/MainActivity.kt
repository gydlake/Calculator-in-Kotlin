package com.naudsoft.kotlincalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {
    var lastDot = false
    var lastNumeric = false
    var lastIsEqual = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onDigit(view: View) {
        //Toast.makeText(this, "Button works", Toast.LENGTH_SHORT).show()
        if (lastIsEqual) {
            tvInput.text=""
            lastIsEqual = false
        }
        tvInput.append((view as Button).text)
        lastNumeric = true
    }

    fun onDot(view: View) {
        if (lastNumeric && !oneDotInside(tvInput.text.toString())) {
            tvInput.append(".")
        }
    }

    fun oneDotInside (value: String): Boolean {
        return value.contains(".")
    }

    fun onClear(view: View) {
        tvInput.text= ""
        lastDot= false
        lastNumeric = false

    }

    fun onEqual(view: View) {
        val value= tvInput.text.toString()
        var theSubString:String= value
        var thePrefix:String =""
        lastIsEqual = true

        try {
            if (operatorInside(value)) {
                val minusAtBeginning = value.startsWith("-")
                if (minusAtBeginning) {
                    theSubString = value.substring(1)
                    thePrefix = "-"
                }

                if (theSubString.contains("-")) {
                    val theSplit = theSubString.split("-")
                    val one = thePrefix + theSplit[0]
                    val two = theSplit[1]
                    tvInput.text = removeDotZero((one.toDouble() - two.toDouble()).toString())
                }
                else if (value.contains("+")) {
                    val theSplit = value.split("+")
                    val one = theSplit[0]
                    val two = theSplit[1]
                    tvInput.text = removeDotZero((one.toDouble() + two.toDouble()).toString())
                }  else if (value.contains("*")) {
                    val theSplit = value.split("*")
                    val one = theSplit[0]
                    val two = theSplit[1]
                    tvInput.text = removeDotZero((one.toDouble() * two.toDouble()).toString())
                } else if (value.contains("/")) {
                    val theSplit = value.split("/")
                    val one = theSplit[0]
                    val two = theSplit[1]
                    tvInput.text = removeDotZero((one.toDouble() / two.toDouble()).toString())
                }

            }
        } catch (e: ArithmeticException) {

        }
    }

    fun onOperator(view: View) {
        lastIsEqual = false
        if (!operatorInside(tvInput.text.toString()))
            tvInput.append((view as Button).text)
    }

    fun operatorInside (value: String): Boolean {
        val theSubStr:String
        if (value.startsWith("-")) {
            theSubStr= value.substring(1)
        } else {
            theSubStr = value
        }
        if (theSubStr.contains("+")||theSubStr.contains("-")||theSubStr.contains("*")||theSubStr.contains("/"))
            return true
        else
            return false
    }

    fun removeDotZero(value: String): String {
        if (value.endsWith(".0"))
            return value.dropLast(2)
        else
            return value
    }

    fun onPlusMinus(view: View) {
        val value= tvInput.text.toString()
        if (isNumeric(value)) {
            tvInput.text= removeDotZero((value.toDouble()*(-1)).toString())
        }

    }

    fun isNumeric(value:String):Boolean{
        try {
            value.toDouble()
            return true
        } catch (e: NumberFormatException) {
            return false
        }
    }

    fun onMC(view: View) {
        btnM1.text = ""
        btnM2.text = ""
        btnM3.text = ""
    }

    fun onMPlus(view: View) {
        val value = tvInput.text.toString()

        if (isNumeric(value)) {
            if (btnM2.text != "") {
                btnM3.text = btnM2.text
                btnM2.text = btnM1.text
            } else if (btnM1.text != "") {
                btnM2.text = btnM1.text
            }
            btnM1.text = value
        }
    }

    fun onMMinus(view: View) {
        if (btnM3.text != "") {
            btnM3.text = ""
        } else if (btnM2.text != "") {
            btnM2.text = ""
        } else {
            btnM1.text =""
        }
    }

    fun onInsertM(view: View) {
        val value = tvInput.text.toString()
        if (value==""||value.endsWith("+")||value.endsWith("-")
            ||value.endsWith("*")||value.endsWith("/"))
                tvInput.append((view as Button).text)
    }

//    fun onM1(view:View) {
//        insertM(view)
//    }
//
//    fun onM2(view:View) {
//        insertM(view)
//    }
//
//    fun onM3(view:View) {
//        insertM(view)
//    }

}