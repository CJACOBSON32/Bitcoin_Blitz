package com.example.bitcoinstoremodule

// the abstraction of items in our shop for the term project

data class ShopItem(val title: String, val price: Int, val description: String, var owned: Int)