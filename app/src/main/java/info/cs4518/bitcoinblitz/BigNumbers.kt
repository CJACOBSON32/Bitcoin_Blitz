package info.cs4518.bitcoinblitz

import java.text.DecimalFormat
import kotlin.math.pow

class BigNumbers {
	companion object {
		fun numToStr(long: Long): String {
			if (long >= 10f.pow(12)) { // Trillions
				val divided = long/10.0.pow(12)
				val formatter = DecimalFormat("#,###,###.2fT")
				return formatter.format(divided)
			} else if (long >= 10f.pow(9)) { // Billions
				val divided = long/10.0.pow(9)
				return String.format("%.2fB", divided)
			} else if (long >= 10f.pow(8)) { // Millions
				val divided = long/10.0.pow(6)
				return String.format("%.2fM", divided)
			} else if (long >= 10f.pow(5)) { // Thousands
				val divided = long/10.0.pow(3)
				return String.format("%.2fK", divided)
			} else {
				val formatter = DecimalFormat("#,###,###")
				return formatter.format(long)
			}
		}

		fun numToStr(int: Int): String {
			return numToStr(int.toLong())
		}
	}
}