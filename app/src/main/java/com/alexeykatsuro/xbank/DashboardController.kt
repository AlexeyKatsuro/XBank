package com.alexeykatsuro.xbank

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.TypedEpoxyController
import com.airbnb.epoxy.carousel
import com.alexeykatsuro.xbank.data.DashboardState

class DashboardController : TypedEpoxyController<DashboardState>() {
    override fun buildModels(data: DashboardState) {

        val cardModels = data.cards.mapIndexed { index, card ->
            CardBindingModel_()
                .id("card$index")
                .item(card)
        }

        val walletModels = data.wallets.mapIndexed { index, wallet ->
            WalletBindingModel_()
                .id("wallet$index")
                .item(wallet)
        }
        val newsModels = data.news.mapIndexed { index, news ->
            NewsBindingModel_()
                .id("news$index")
                .item(news)

        }


        carouselPagerIndicator {
            id("card carousel")
            models(cardModels)
            padding(Carousel.Padding.dp(0, 0))
           // numViewsToShowOnScreen(1.2f)
        }

        walletModels.forEach { it.addTo(this) }

        carousel {
            id("news carousel")
            models(newsModels)
            padding(Carousel.Padding.dp(8, 0))
            numViewsToShowOnScreen(1.2f)

        }

    }
}