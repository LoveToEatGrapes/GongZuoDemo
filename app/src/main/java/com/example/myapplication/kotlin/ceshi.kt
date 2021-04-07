package com.example.myapplication.kotlin

import com.example.myapplication.kotlin.HomeFragmentBean.MenuData
import java.util.*

class ceshi {
    private var ccAll: MutableList<MenuData>? = null
    private fun ce() {
        ccAll = ArrayList()
        val c = MenuData()
        for (i in 0..6) {
            c.id = "1111"
            c.imgUrlID = 111111
            ccAll!!.add(c)
        }
    }
}