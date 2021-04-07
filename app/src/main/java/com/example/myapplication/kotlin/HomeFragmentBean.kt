package com.example.myapplication.kotlin

class HomeFragmentBean {
    var menuDataList: List<MenuData>? = null
    var inspirationDataList: List<InspirationData>? = null

    class MenuData {
        var imgUrl: String? = null
        var imgUrlID = 0
        var title: String? = null
        var id: String? = null

    }

    class InspirationData {
        var id: String? = null
        var content: String? = null
        var title: String? = null

    }
}