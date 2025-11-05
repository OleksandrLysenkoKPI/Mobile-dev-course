import com.example.task3.logic.ElectricMeter
import com.example.task3.logic.SmartSystem
import com.example.task3.logic.WaterMeter

fun main() {
    val meters = SmartSystem()

    val eMeter = ElectricMeter(2.5, 1.2)
    val wMeter = WaterMeter(13.85)

    meters.addMeter(eMeter)
    meters.addMeter(wMeter)

    eMeter.measureDayUsage(5.0)
    eMeter.measureNightUsage(3.0)
    wMeter.measureUsage(2.0)
    wMeter.measureUsage(3.0)


    println(meters.generateReport())
}