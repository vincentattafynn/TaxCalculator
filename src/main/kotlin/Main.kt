const val ssnitPercentage: Double = 0.055
fun SSNIT_func(gross_income: Double): Double {
    //function to calculate amount paid to SSNIT
    val ssnitReturn = gross_income * ssnitPercentage
    return ssnitReturn
}

fun calculateTaxableIncome(ssnitReturn: Double, grossIncome: Double): Double {
    return grossIncome - ssnitReturn
}

fun main() {
    println("Enter your Gross Income: ")
    val grossIncome: String? = readLine()
    val newGrossIncome = grossIncome?.toDouble()
    if (newGrossIncome != null) {
        val amtToSsnit = SSNIT_func(newGrossIncome)
        val taxableIncome = calculateTaxableIncome(amtToSsnit,newGrossIncome)
        println("$taxableIncome")
        println("You'll pay $amtToSsnit to SSNIT")
    }
}





