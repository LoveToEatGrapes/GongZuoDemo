package com.example.myapplication.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.View.stellar.StellarMap
import com.example.myapplication.View.stellar.UIUtils
import com.example.myapplication.View.stellar.three.WordGroupView
import com.example.myapplication.View.stellar.three.WordGroupViewTwo
import com.example.myapplication.View.stellar.two.RandomLayoutTwo
import java.util.*
import kotlin.collections.ArrayList


class RandomTextLayoutActivity : AppCompatActivity() {

//    private var stellarMap: StellarMap = findViewById<StellarMap>(R.id.stellar_map)

    private val datas = arrayOf("超级新手计划", "乐享活系列90天计划", "钱包计划", "30天理财计划(加息2%)", "90天理财计划(加息5%)", "180天理财计划(加息10%)",
            "林业局投资商业经营", "中学老师购买车辆", "屌丝下海经商计划", "新西游影视拍摄投资", "Java培训老师自己周转", "养猪场扩大经营",
            "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划","街骂","得五","别解","亡羊","增字法"
            ,"侧扣法","瓮中鳖","藏茗山","外刚柔","楚失弓","罕用字","探玄珠","春暖花开","十字路口","千军万马","白手起家",
            "张灯结彩","风和日丽","万里长城","人来人往","自由自在","助人为乐","红男绿女","春风化雨","马到成功","拔苗助长"
            ,"安居乐业","走马观花","念念不忘",  "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划","街骂","得五","别解","亡羊","增字法"
            ,"侧扣法","瓮中鳖","藏茗山","外刚柔","楚失弓","罕用字","探玄珠","春暖花开","十字路口","千军万马","白手起家",
            "张灯结彩","风和日丽","万里长城","人来人往","自由自在","助人为乐","红男绿女","春风化雨","马到成功","拔苗助长"
            ,"安居乐业","走马观花","念念不忘" , "旅游公司扩大规模", "阿里巴巴洗钱计划", "铁路局回款计划", "高级白领赢取白富美投资计划","街骂","得五","别解","亡羊","增字法"
            ,"侧扣法","瓮中鳖","藏茗山","外刚柔","楚失弓","罕用字","探玄珠","春暖花开","十字路口","千军万马","白手起家",
            "张灯结彩","风和日丽","万里长城","人来人往","自由自在","助人为乐","红男绿女","春风化雨","马到成功","拔苗助长"
            ,"安居乐业","走马观花")

    private val ones = arrayOfNulls<String>(datas.size / 2)
    private val twos = arrayOfNulls<String>(datas.size / 2)
    private var adapter: MyAdapter? = null
    private var random: Random? = null

    private var list: MutableList<String> = ArrayList();
    private var listOne: MutableList<String> = ArrayList();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_text_layout)

        Log.e("TAG","==========================:" + datas.size)
        one()

        two()

        three()

    }

    fun three(){

        if (list.size <=0 ){
            return
        }

        for(i in 0..4){
            listOne.add(datas[i])
        }
        Log.e("TAG","===========哈哈哈：" + listOne.size)
        var random_2: WordGroupView = findViewById<WordGroupView>(R.id.random_2)
        random_2.setWords(listOne)


        val randomTwo = findViewById<WordGroupViewTwo>(R.id.randomTwo)
        randomTwo.setWords(list)
//        randomTwo.setWords(listOne)

    }


    fun two(){
        var random_1: RandomLayoutTwo = findViewById<RandomLayoutTwo>(R.id.random_1)
        adapter = MyAdapter(this@RandomTextLayoutActivity,ones,twos)

        random_1.setData(ones)
        random_1.startAnimation()
    }

    //------------------------------------------------------------------------------------------------------


    fun one(){
        var stellarMap: StellarMap = findViewById<StellarMap>(R.id.stellar_map)

        for (i in datas.indices) {
            if (i < datas.size / 2) {
                ones[i] = datas[i]
                list.add(datas[i])
            } else {
                twos[i - datas.size / 2] = datas[i]
            }
        }

        adapter = MyAdapter(this@RandomTextLayoutActivity,ones,twos)
//        val padding: Int = UIUtils.dp2px(5)
//        stellarMap.setInnerPadding(padding, padding, padding, padding)
        stellarMap.setAdapter(adapter)


        //必须调用如下的两个方法方可显示数据
        stellarMap.setRegularity(8, 8)
        stellarMap.setGroup(0, true) //显示group= 0，显示动画
    }


    internal class MyAdapter(context: Context, ones: Array<String?>, twos: Array<String?>) : StellarMap.Adapter {

        private var context : Context = context;
        private var ones: Array<String?> = ones
        private var twos: Array<String?> = twos

        //显示分组的个数
        override fun getGroupCount(): Int {
            return 10
        }

        //每组显示的个数
        override fun getCount(group: Int): Int {
            return 55
        }

        override fun getView(group: Int, position: Int, convertView: View?): View {
            var random = Random()


            Log.e("TAG", "group = $group")
            val tv = TextView(context)
            if (group == 0) {
                tv.setText(ones.get(position))
            } else {
                tv.setText(twos.get(position))
            }
            //tv.textSize = (UIUtils.dp2px(8) + random.nextInt(8)).toFloat() //设置字体大小
            tv.textSize = UIUtils.dp2px(3).toFloat() //设置字体大小
            val red: Int = random.nextInt(211)
            val green: Int = random.nextInt(211)
            val blue: Int = random.nextInt(211)
            tv.setTextColor(Color.rgb(red, green, blue))

            //设置点击事件
            tv.setOnClickListener { Toast.makeText(context, tv.text, Toast.LENGTH_SHORT).show() }
            return tv
        }

        override fun getNextGroupOnPan(group: Int, degree: Float): Int {
            return 0
        }

        //接下来显示的组别
        override fun getNextGroupOnZoom(group: Int, isZoomIn: Boolean): Int {
            var group = group
            group = if (group == 1) {
                0
            } else {
                1
            }
            return group
        }
    }

}