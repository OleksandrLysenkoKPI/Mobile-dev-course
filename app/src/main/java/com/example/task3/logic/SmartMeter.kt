package com.example.task3.logic

// Абстрактний клас -- спільна логіка для всіх лічильників
abstract class SmartMeter(
   val unit: String
) {
    var totalUsage: Double = 0.0

    abstract fun measureUsage(amount: Double)
    abstract fun getInfo(): String

    abstract fun calculateTotalCost(): Double
}

class ElectricMeter(
    private val dayTariff: Double, private val nightTariff: Double
) : SmartMeter("kWh") {
    private var dayUsage = 0.0
    private var nightUsage = 0.0

    fun measureDayUsage(amount: Double) {
        dayUsage += amount
        totalUsage = dayUsage + nightUsage
    }

    fun measureNightUsage(amount: Double) {
        nightUsage += amount
        totalUsage = dayUsage + nightUsage
    }

    override fun calculateTotalCost(): Double {
        return (dayUsage * dayTariff) + (nightUsage * nightTariff)
    }

    override fun measureUsage(amount: Double) {
        // не використовується напряму
    }

    override fun getInfo(): String {
        val totalCost = calculateTotalCost()
        return """
            Electric Meter:
            - Day usage: $dayUsage kWh * $dayTariff $ = ${dayUsage * dayTariff} $
            - Night usage: $nightUsage kWh * $nightTariff $ = ${"%.2f".format(nightUsage * nightTariff)} $
            = Total: $totalUsage kWh = ${"%.2f".format(totalCost)} $
        """.trimIndent()
    }
}

class WaterMeter(private val tariff: Double, private val limit: Double = 4.0):
    SmartMeter("m^3") {

    override fun measureUsage(amount: Double) {
        totalUsage += amount
    }

    fun checkLimit(): String {
        var message: String
        if (totalUsage > limit) {
            message = "Water usage higher than norm! ($totalUsage / $limit $unit)"
        } else {
            message = "Water usage is within normal limits."
        }
        return message
    }

    override fun calculateTotalCost(): Double {
        return totalUsage * tariff
    }

    override fun getInfo(): String {
        val totalCost = calculateTotalCost()
        val limitMessage = checkLimit()
        return """
            Water Meter:
            - $totalUsage $unit used (Norm: $limit)
            - $limitMessage.
            - Total: $totalUsage $unit * $tariff $ = $totalCost $
        """.trimIndent()
    }
}
