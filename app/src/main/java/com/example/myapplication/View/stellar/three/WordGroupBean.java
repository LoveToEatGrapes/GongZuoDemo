package com.example.myapplication.View.stellar.three;

import java.util.ArrayList;
import java.util.List;

public class WordGroupBean {

    private String content;
    private String id;
    private String num;
    private WordGroupBean bean;
    private List<WordGroupBean> child = new ArrayList<>();
    private List<String> title = new ArrayList<>();

    private int left;
    private int right;
    private int bottom;
    private int top;

    private List<RecordXAndY> recordXAndies;

    public List<RecordXAndY> getRecordXAndies() {
        return recordXAndies;
    }

    public void setRecordXAndies(List<RecordXAndY> recordXAndies) {
        this.recordXAndies = recordXAndies;
    }

    /**
     * 记录x轴和y轴
     */
    public static class RecordXAndY{

        //存储每圈的总长度和总高度以及每个view的长度和宽度
        private int textWidth; //每个view的宽度
        private int textHeight; //每个view的高度
        private int countWidth; //view的总宽度（每圈）
        private int countHeight; //view的总高度（每圈）
        private int xPoint;//绘制的x轴坐标点
        private int yPoint;//绘制的y轴坐标点

        public int getxPoint() {
            return xPoint;
        }

        public void setxPoint(int xPoint) {
            this.xPoint = xPoint;
        }

        public int getyPoint() {
            return yPoint;
        }

        public void setyPoint(int yPoint) {
            this.yPoint = yPoint;
        }

        public int getTextWidth() {
            return textWidth;
        }

        public void setTextWidth(int textWidth) {
            this.textWidth = textWidth;
        }

        public int getTextHeight() {
            return textHeight;
        }

        public void setTextHeight(int textHeight) {
            this.textHeight = textHeight;
        }

        public int getCountWidth() {
            return countWidth;
        }

        public void setCountWidth(int countWidth) {
            this.countWidth = countWidth;
        }

        public int getCountHeight() {
            return countHeight;
        }

        public void setCountHeight(int countHeight) {
            this.countHeight = countHeight;
        }
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public void setBean(WordGroupBean bean) {
        this.bean = bean;
    }

    public WordGroupBean getBean() {
        return bean;
    }

    public void setTitle(List<String> title) {
        this.title = title;
    }

    public List<String> getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<WordGroupBean> getChild() {
        return child;
    }

    public void setChild(List<WordGroupBean> child) {
        this.child = child;
    }

    public WordGroupBean getChildIndex(int i){
        return child.get(i);
    }


}
