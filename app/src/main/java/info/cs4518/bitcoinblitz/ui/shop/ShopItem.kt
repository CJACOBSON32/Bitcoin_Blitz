package com.example.bitcoinstoremodule

// the abstraction of items in our shop for the term project

data class ShopItem<T>(
	val title: String,
	val price: Long,
	val description: String,
	var owned: Int,
	val upgrade: T
)