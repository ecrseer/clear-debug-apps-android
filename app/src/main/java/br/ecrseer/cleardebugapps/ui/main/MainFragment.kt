package br.ecrseer.cleardebugapps.ui.main

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.ecrseer.cleardebugapps.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }
    fun buscaPorAppsDebug() {
        try {
            val apps= mutableListOf<ApplicationInfo>()
            val intent = Intent(Intent.ACTION_MAIN, null)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            val resolveInfoList:List<ResolveInfo> = requireActivity()?.packageManager.queryIntentActivities(intent, 0)
            var c = 0
            for (resolveInfo in resolveInfoList) {
                val flags = resolveInfo.activityInfo.applicationInfo.flags
                val isDebuggable = 0 != flags and ApplicationInfo.FLAG_DEBUGGABLE
                if(isDebuggable){
                    apps.add(resolveInfo.activityInfo.applicationInfo)
                }

            }
            val intent2 = Intent(Intent.ACTION_DELETE)
            intent2.data = Uri.parse("package:${apps?.get(0).packageName}")
            startActivity(intent2)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
    }

}