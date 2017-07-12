package com.jiajieshen.android.samples.features.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.model.IntentItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_intent.*
import org.jetbrains.anko.AnkoLogger

abstract class IntentActivity : AppCompatActivity(), AnkoLogger {

    lateinit protected var adapter: IntentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intent)

        setupToolbar()
        setupRecyclerView()
        setupIntentItemList()
    }

    open fun setupToolbar() {
    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        adapter = IntentAdapter(this, null)
        recyclerView.adapter = adapter
    }

    private fun setupIntentItemList() {
        Observable
                .create<List<IntentItem>> { e ->
                    val intentItemList = getIntentItems()
                    e.onNext(intentItemList)
                    e.onComplete()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { intentItemList ->
                    if (!isFinishing) {
                        adapter.items = intentItemList
                        adapter.notifyDataSetChanged()
                    }
                }
    }

    abstract fun getIntentItems(): List<IntentItem>
}
