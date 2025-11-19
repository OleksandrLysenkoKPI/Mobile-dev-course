package com.example.task4

import kotlin.reflect.KProperty

class RateHolder<T>(private val data: MutableMap<String, T>) {

    fun getRate(currency: String): T? = try {
        data[currency]
    } catch (e: Exception) {
        null
    }

    fun setRate(currency: String, rate: T) {
        try {
            data[currency] = rate
        } catch (e: Exception) {
            println("Error while setting rate: ${e.message}")
        }
    }

    fun hasCurrency(currency: String): Boolean = currency in data
}

fun RateHolder<Double>.convert(amount: Double, from: String, to: String): Double? {
    return try {
        val fromRate = getRate(from) ?: return null
        val toRate = getRate(to) ?: return null

        amount * (fromRate / toRate)
    } catch (e: Exception) {
        null
    }
}

class RateDelegate(private val holder: RateHolder<Double>, private val currency: String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): Double {
        return holder.getRate(currency) ?: 0.0
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: Double) {
        holder.setRate(currency, value)
    }
}