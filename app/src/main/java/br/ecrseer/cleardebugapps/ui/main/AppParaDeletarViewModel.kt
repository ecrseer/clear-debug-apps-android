package br.ecrseer.cleardebugapps.ui.main

import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppParaDeletarViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val appsParaSeremDeletados = MutableLiveData<MutableList<ApplicationInfo>>()
    val todosOsApps = MutableLiveData<MutableList<ApplicationInfo>>()

    fun buscaPorAppsDebug(resolveInfoList:List<ResolveInfo>){
        try {
            val apps= mutableListOf<ApplicationInfo>()

            for (resolveInfo in resolveInfoList) {
                val flags = resolveInfo.activityInfo.applicationInfo.flags
                val isDebuggable = 0 != flags and ApplicationInfo.FLAG_DEBUGGABLE
                if(isDebuggable){
                    apps.add(resolveInfo.activityInfo.applicationInfo)
                }
            }
            todosOsApps.postValue(apps)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }





}