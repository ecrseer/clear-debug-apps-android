package br.ecrseer.cleardebugapps.ui.main

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.ItemTouchHelper
import br.ecrseer.cleardebugapps.AppParaDeletarRecyclerViewAdapter
import br.ecrseer.cleardebugapps.R

/**
 * A fragment representing a list of Items.
 */
class AppParaDeletarFragment : Fragment() {

    private var columnCount = 1

    private lateinit var viewModel: AppParaDeletarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            columnCount = it.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)
        viewModel= ViewModelProvider(this).get(AppParaDeletarViewModel::class.java)
        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                buscaPorAppsDebug()

                adapter = viewModel.todosOsApps.value?.let { AppParaDeletarRecyclerViewAdapter(it) }
                viewModel.todosOsApps.observe(viewLifecycleOwner, Observer {
                    adapter = AppParaDeletarRecyclerViewAdapter(it)
                })

            }
        }
        return view
    }
    val itemTouchHelper = ItemTouchHelper(
        object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

            // Drag
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            // Ação de jogar o item para os lados
            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int) {

                val position = viewHolder.bindingAdapterPosition
                removerApp(position)
//                    adapter.notifyItemRemoved(position)
                /*if (direction == ItemTouchHelper.LEFT){
                } else if (direction == ItemTouchHelper.RIGHT){
                }*/
            }

        })
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rv = view.findViewById<RecyclerView>(R.id.list)
        itemTouchHelper.attachToRecyclerView(rv)
    }
    fun buscaPorAppsDebug() {
        try {
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val resolveInfoList:List<ResolveInfo> = requireActivity()?.packageManager.queryIntentActivities(intent, 0)
            viewModel.buscaPorAppsDebug(resolveInfoList)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    fun removerApp(posicaoRV:Int){


        val intent2 = Intent(Intent.ACTION_DELETE)
        viewModel.todosOsApps.value?.let {
            val pacoteApp = it?.get(posicaoRV).packageName
            intent2.data = Uri.parse("package:${pacoteApp}")
            println("stranger love vo del $pacoteApp")
            startActivity(intent2)
        }
    }

    override fun onResume() {
        super.onResume()
        buscaPorAppsDebug()
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            AppParaDeletarFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}