package info.cs4518.bitcoinblitz.upgrades

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

abstract class Upgrade(
	val id: Int? = null,
	val name: String? = null,
	val numOwned: Int? = null,
	val cost: Float? = null,
	val description: String? = null
)

