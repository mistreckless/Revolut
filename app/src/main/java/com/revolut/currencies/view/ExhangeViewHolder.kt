package com.revolut.currencies.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.revolut.R
import com.revolut.currencies.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.exchange_item.view.*

class ExchangeViewHolder(parent: ViewGroup, private val viewModel: MainViewModel) :
        BaseExchangeViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.exchange_item, parent, false)) {

    private lateinit var currency: String

    override fun bind(currency: String, position: Int) {
        this.currency = currency
        with(itemView) {
            tvCurrency.text = currency
            setOnClickListener {
                viewModel.changeCurrent(currency, position, tvAmount.text.toString().toDouble())
            }
        }
    }

    override fun onAttach() {
        disposables.add(viewModel.observeExchange(currency)
            .distinctUntilChanged()
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext { itemView.tvAmount.text = it.toString() }
            .subscribe())
    }
}