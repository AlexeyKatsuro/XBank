package com.alexeykatsuro.xbank

import android.content.Context

import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.ModelView
import com.airbnb.epoxy.ModelView.Size
import com.alexeykatsuro.xbank.decorations.LinePagerIndicatorDecoration1

@ModelView(saveViewState = true, autoLayout = Size.MATCH_WIDTH_WRAP_HEIGHT)
class CarouselPagerIndicator(context: Context) : Carousel(context) {

    init {
        addItemDecoration(LinePagerIndicatorDecoration1())
    }

    //override fun getSnapHelperFactory(): Nothing? = null

}
