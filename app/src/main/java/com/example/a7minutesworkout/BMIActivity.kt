package com.example.a7minutesworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.a7minutesworkout.databinding.ActivityBmiBinding
import java.math.BigDecimal
import java.math.RoundingMode

class BMIActivity : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS_VIEW = "METRIC_UNIT_VIEW"
        private const val US_UNITS_VIEW = "US_UNIT_VIEW"
    }
    private var currentVisibleView: String = METRIC_UNITS_VIEW

    private var binding: ActivityBmiBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBmiBinding.inflate(layoutInflater)
        //connect the layout to this activity
        setContentView(binding?.root)


        setSupportActionBar(binding?.toolbarBmiActivity)
        if(supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "Calculate BMI"
        }
        binding?.toolbarBmiActivity?.setNavigationOnClickListener {
            onBackPressed()
        }

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId: Int ->
            if(checkedId == R.id.rbMetricUnits) {
                makeVisibleMetricUnitsView()
            } else {
                makeVisibleUsUnitsView()
            }
        }

        binding?.btnCalculateUnits?.setOnClickListener {
            if(validateMetricUnit()){
                val heightValue: Float = binding?.etMetricUnitHeight?.text.toString().toFloat() / 100
                val weightValue: Float = binding?.etMetricUnitWeight?.text.toString().toFloat()
                val bmi = weightValue/ (heightValue*heightValue)

                //display BMI results
                displayBMIResult(bmi)
            } else {
                Toast.makeText(this, "Please enter valid values.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun makeVisibleMetricUnitsView() {
        currentVisibleView = METRIC_UNITS_VIEW
        binding?.apply{
            tilMetricUnitHeight.visibility = View.VISIBLE
            tilMetricUnitWeight.visibility = View.VISIBLE
            tilUsMetricUnitWeight.visibility = View.GONE // make weight view Gone.
            tilMetricUsUnitHeightFeet.visibility = View.GONE
            tilMetricUsUnitHeightInch.visibility = View.GONE

            etMetricUnitHeight.text!!.clear() // height value is cleared if it is added.
            etMetricUnitWeight.text!!.clear() // weight value is cleared if it is added.

            llDiplayBMIResult.visibility = View.INVISIBLE
        }
    }

    private fun makeVisibleUsUnitsView() {
        currentVisibleView = US_UNITS_VIEW // Current View is updated here.
        binding?.apply{
            tilMetricUnitHeight.visibility =
                View.INVISIBLE // METRIC  Height UNITS VIEW is InVisible
            tilMetricUnitWeight.visibility =
                View.INVISIBLE // METRIC  Weight UNITS VIEW is InVisible
            tilUsMetricUnitWeight.visibility = View.VISIBLE // make weight view visible.
            tilMetricUsUnitHeightFeet.visibility = View.VISIBLE // make height feet view visible.
            tilMetricUsUnitHeightInch.visibility = View.VISIBLE // make height inch view visible.

            etUsMetricUnitWeight.text!!.clear() // weight value is cleared.
            etUsMetricUnitHeightFeet.text!!.clear() // height feet value is cleared.
            etUsMetricUnitHeightInch.text!!.clear() // height inch is cleared.

            llDiplayBMIResult.visibility = View.INVISIBLE
        }
    }

    private fun displayBMIResult(bmi: Float) {
        val bmiLabel: String
        val bmiDescription: String

        //compareTo 等於return 0，小於return負值，大於return正值
        //if bmi小於等於15
        if (bmi.compareTo(15f) <= 0) {
            bmiLabel = "Very severely underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(15f) > 0 && bmi.compareTo(16f) <= 0
        ) {
            bmiLabel = "Severely underweight"
            bmiDescription = "Oops!You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(16f) > 0 && bmi.compareTo(18.5f) <= 0
        ) {
            bmiLabel = "Underweight"
            bmiDescription = "Oops! You really need to take better care of yourself! Eat more!"
        } else if (bmi.compareTo(18.5f) > 0 && bmi.compareTo(25f) <= 0
        ) {
            bmiLabel = "Normal"
            bmiDescription = "Congratulations! You are in a good shape!"
        } else if (java.lang.Float.compare(bmi, 25f) > 0 && java.lang.Float.compare(
                bmi,
                30f
            ) <= 0
        ) {
            bmiLabel = "Overweight"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(30f) > 0 && bmi.compareTo(35f) <= 0
        ) {
            bmiLabel = "Obese Class | (Moderately obese)"
            bmiDescription = "Oops! You really need to take care of your yourself! Workout maybe!"
        } else if (bmi.compareTo(35f) > 0 && bmi.compareTo(40f) <= 0
        ) {
            bmiLabel = "Obese Class || (Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        } else {
            bmiLabel = "Obese Class ||| (Very Severely obese)"
            bmiDescription = "OMG! You are in a very dangerous condition! Act now!"
        }

        // This is used to round the result value to 2 decimal values after "."
        val bmiValue = BigDecimal(bmi.toDouble()).setScale(2, RoundingMode.HALF_EVEN).toString()

        binding?.llDiplayBMIResult?.visibility = View.VISIBLE
        binding?.tvBMIValue?.text = bmiValue // Value is set to TextView
        binding?.tvBMIType?.text = bmiLabel // Label is set to TextView
        binding?.tvBMIDescription?.text = bmiDescription // Description is set to TextView
    }

    private fun validateMetricUnit(): Boolean{
        var isValid = true
        binding?.tilMetricUnitHeight?.error = null
        binding?.tilMetricUnitWeight?.error = null

        if(binding?.etMetricUnitWeight?.text.toString().isEmpty() && binding?.etMetricUnitHeight?.text.toString().isEmpty()){
            binding?.tilMetricUnitWeight?.error = "請輸入體重"
            binding?.tilMetricUnitHeight?.error = "請輸入身高"
            isValid = false
        }
        else if(binding?.etMetricUnitWeight?.text.toString().isEmpty()) {
            binding?.tilMetricUnitWeight?.error = "請輸入體重"
            isValid = false
        } else if(binding?.etMetricUnitHeight?.text.toString().isEmpty()) {
            binding?.tilMetricUnitHeight?.error = "請輸入身高"
            isValid = false
        }

        return isValid
    }

}