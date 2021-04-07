package com.example.myapplication.activity

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.View.stellar.three.WordCloudView
import java.util.*


class RandomTextLayoutTwoActivity : AppCompatActivity() {

    //    private var stellarMap: StellarMap = findViewById<StellarMap>(R.id.stellar_map)
    var random: Random? = Random()
    var ppp = 0
    var wcv_two: WordCloudView? = null
    var weight = 20
    var off = 2
    private val datas = arrayOf("超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划", "街骂", "得五", "别解", "亡羊", "增字法", "侧扣法", "瓮中鳖", "藏茗山", "外刚柔", "楚失弓", "罕用字", "探玄珠", "春暖花开", "十字路口", "千军万马", "白手起家",
            "张灯结彩", "风和日丽", "万里长城", "人来人往", "自由自在", "助人为乐", "红男绿女", "春风化雨", "马到成功", "拔苗助长", "安居乐业", "走马观花", "念念不忘", "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划", "街骂", "得五", "别解", "亡羊", "增字法", "侧扣法", "瓮中鳖", "藏茗山", "外刚柔", "楚失弓", "罕用字", "探玄珠", "春暖花开", "十字路口", "千军万马", "白手起家",
            "张灯结彩", "风和日丽", "万里长城", "人来人往", "自由自在", "助人为乐", "红男绿女", "春风化雨", "马到成功", "拔苗助长", "安居乐业", "走马观花", "念念不忘", "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划", "街骂", "得五", "别解", "亡羊", "增字法", "侧扣法", "瓮中鳖", "藏茗山", "外刚柔", "楚失弓", "罕用字", "探玄珠", "春暖花开", "十字路口", "千军万马", "白手起家",
            "张灯结彩", "风和日丽", "万里长城", "人来人往", "自由自在", "助人为乐", "红男绿女", "春风化雨", "马到成功", "拔苗助长", "安居乐业", "走马观花")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_text_layout_two)

        Log.e("TAG", "==========================:" + datas.size)
        wcv_two = findViewById<WordCloudView>(R.id.wcv_two)
        one()

//        two();

    }

    fun one() {
        val handler: Handler = object : Handler() {
            override fun handleMessage(msg: Message) {
                var s = System.currentTimeMillis().toString()
                s = datas[random!!.nextInt(datas.size)]
                if (ppp < 2) {
                    wcv_two!!.addTextView(s, 25, "#DC143C")
                } else if (ppp < 6 && ppp > 1) {
                    wcv_two!!.addTextView(s, 20, "#FFC0CB")
                } else if (ppp < 10 && ppp > 5) {
                    wcv_two!!.addTextView(s, 15, "#0000FF")
                } else {
                    wcv_two!!.addTextView(s, 10, "#00FFFF")
                }
                ppp++
                sendEmptyMessageDelayed(0, 100)
            }
        }
        handler.sendEmptyMessage(0)
    }


    fun two(){
        for (i in datas.indices) {
            val s = datas[i]
            if (i < 2) {
                wcv_two!!.addTextView(s, 25, "#DC143C")
            } else if (i < 6 && i > 1) {
                wcv_two!!.addTextView(s, 20, "#FFC0CB")
            } else if (i < 10 && i > 5) {
                wcv_two!!.addTextView(s, 15, "#0000FF")
            } else {
                wcv_two!!.addTextView(s, 10, "#00FFFF")
            }
//            wcv_two!!.addTextView(s, weight)
//            if (--off == 0) {
//                off = 3
//                if (weight > 5) weight--
//                weight--
//                if (weight == 5) return
//            }
        }
    }

}