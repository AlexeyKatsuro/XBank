package com.alexeykatsuro.xbank.data

data class DashboardState(
    val cards: List<Card>,
    val wallets: List<Wallet>,
    val news: List<News>
)