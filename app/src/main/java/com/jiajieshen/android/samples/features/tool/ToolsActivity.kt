package com.jiajieshen.android.samples.features.tool

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.jiajieshen.android.samples.R
import com.jiajieshen.android.samples.constants.ToolKey
import com.jiajieshen.android.samples.model.Tool
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class ToolsActivity : AppCompatActivity() {

    lateinit private var adapter: ToolsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tools)

        setupRecyclerView()
        setupToolList()
    }

    private fun setupRecyclerView() {
        recyclerView.setHasFixedSize(true)
        adapter = ToolsAdapter(this, null)
        recyclerView.adapter = adapter
    }

    private fun setupToolList() {
        Observable
                .create<List<Tool>> { e ->
                    val items = getTools()
                    e.onNext(items)
                    e.onComplete()
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { items ->
                    adapter.items = items
                    adapter.notifyDataSetChanged()
                }
    }

    private fun getTools(): ArrayList<Tool> {
        val items = ArrayList<Tool>()
        items.add(Tool(ToolKey.IMAGE_PALETTE, getString(R.string.tool_image_palette)))
        return items
    }
}
