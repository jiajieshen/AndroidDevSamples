package com.jiajieshen.android.samples.features.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.features.tool.ToolsActivity
import com.jiajieshen.android.samples.model.IntentItem
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit private var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupIntentItemList()
    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        adapter = MainAdapter(this, null)
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

    private fun getIntentItems(): List<IntentItem> {
        val intentItemList = ArrayList<IntentItem>()
        intentItemList.add(IntentItem(getString(R.string.tools), ToolsActivity::class.java.name))
        return intentItemList
    }
}
