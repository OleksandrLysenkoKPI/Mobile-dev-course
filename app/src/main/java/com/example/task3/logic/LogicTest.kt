import com.example.task3.logic.ElectricMeter
import com.example.task3.logic.WaterMeter

fun main() {
    val eMeter = ElectricMeter(1, 2.5, 1.2)
    val wMeter = WaterMeter(2, 35.5)

    eMeter.measureDayUsage(5.0)
    eMeter.measureNightUsage(3.0)
    wMeter.measureUsage(2.0)
    wMeter.measureUsage(3.0)

    println(eMeter.getInfo())
    println()
    println(wMeter.getInfo())
}