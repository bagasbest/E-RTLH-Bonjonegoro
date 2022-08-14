package com.sounekatlogo.ertlhbojonegoro.register

import android.content.Context
import org.json.JSONArray

object Desa {

    fun jsonToList(context: Context, desaList: ArrayList<DesaModel>) {

        val jsonData = context.resources.openRawResource(
            context.resources.getIdentifier(
                "desa",
                "raw",
                context.packageName
            )
        ).bufferedReader().use { it.readText() }

        val outputJsonString = JSONArray(jsonData)

        for (i in 0 until outputJsonString.length()) {
            val poster = outputJsonString.getJSONObject(i)

            val no = poster.get("No.")
            val desa = poster.get("Desa")
            val kodeWilayah = poster.get("Kode Wilayah")
            val kecamatan = poster.get("Kecamatan")
            val model = DesaModel()
            model.id = "" + no
            model.desa = "" + desa
            model.code = "" + kodeWilayah
            model.kecamatan = "" + kecamatan
            desaList.add(model)
        }
    }


}