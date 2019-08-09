package com.alexeykatsuro.xbank

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.alexeykatsuro.xbank.data.*
import com.alexeykatsuro.xbank.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {


    private lateinit var binding: FragmentDashboardBinding

    private val controller = DashboardController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false).apply {
            recycler.setController(controller)
        }

        binding.update.setOnClickListener {
            controller.setData(getData())
        }
        controller.setData(getData())
        return binding.root
    }

    fun getData(): DashboardState {

        return DashboardState(
            List((0..3).random()) { Card((0..999).random().toFloat(), "AZN", "Card name", "4444 **** **** 4444") },
            List((0..10).random()) {
                Wallet(
                    (0..999).random().toFloat(),
                    "AZN",
                    "+375 44 727 53 13",
                    MobileOperator.One
                )
            },
            List((0..6).random()) {
                News(
                    "18 arl",
                    "В 2017 году казах Асхат Адылбеков откликнулся на предложение о работе",
                    "В 2017 году казах Асхат Адылбеков откликнулся на предложение о работе белорусского банка с казахстанским капиталом и переехал в Минск вместе с женой и двумя сыновьями. Два года в Беларуси круто изменили жизнь банкира."
                )
            }
        )
    }
}