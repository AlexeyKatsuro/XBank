package com.alexeykatsuro.xbank.data

data class Wallet(
    val amount: Float,
    val currency: String,
    val number: String,
    val operator: MobileOperator)