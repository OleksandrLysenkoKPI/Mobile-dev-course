package com.example.task3.logic

// Абстрактний клас -- спільна логіка для всіх лічильників
abstract class SmartMeter(
    val id: Int, val unit: String, val tariff: Double
) {
    var totalUsage: Double = 0.0

    abstract fun measureUsage(amount: Double)
    abstract fun getInfo(): String

    fun calculateCost(): Double {
        return totalUsage * tariff
    }
}

class ElectricMeter(
    id: Int, private val dayTariff: Double, private val nightTariff: Double
) : SmartMeter(id, "kWh", tariff = 0.0) { // базовий тариф не використовується напряму
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

    fun calculateTotalCost(): Double {
        return (dayUsage * dayTariff) + (nightUsage * nightTariff)
    }
    override fun measureUsage(amount: Double) {
        // Не використовується у цьому класі напряму
    }

    override fun getInfo(): String {
        val totalCost = calculateTotalCost()
        return """
            Electric Meter #$id
            - Day usage: $dayUsage kWh * $dayTariff $ = ${dayUsage * dayTariff} $
            - Night usage: $nightUsage kWh * $nightTariff $ = ${"%.2f".format(nightUsage * nightTariff)} $
            = Total: $totalUsage kWh => ${"%.2f".format(totalCost)} $
        """.trimIndent()
    }
}

class WaterMeter(id: Int, tariff: Double, private val limit: Double = 4.0) :
    SmartMeter(id, "m^3", tariff) {

    override fun measureUsage(amount: Double) {
        totalUsage += amount
        if (totalUsage > limit) {
            println("Water limit exceeded! ($totalUsage / $limit $unit)")
        }
    }

    override fun getInfo(): String {
        return "Water Meter #$id: $totalUsage $unit used (Limit: $limit)"
    }
}
