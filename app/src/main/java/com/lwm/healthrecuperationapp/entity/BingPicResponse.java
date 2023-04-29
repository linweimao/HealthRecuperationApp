package com.lwm.healthrecuperationapp.entity;

import java.util.List;

public class BingPicResponse {

    /**
     * images : [{"startdate":"20230412","fullstartdate":"202304121600","enddate":"20230413","url":"/th?id=OHR.SnowdoniaNational_ZH-CN7415540950_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp","urlbase":"/th?id=OHR.SnowdoniaNational_ZH-CN7415540950","copyright":"斯诺登尼亚国家公园，威尔士，英国 (© Sebastian Wasek/eStock Photo)","copyrightlink":"https://www.bing.com/search?q=%E6%96%AF%E8%AF%BA%E7%99%BB%E5%B0%BC%E4%BA%9A%E5%9B%BD%E5%AE%B6%E5%85%AC%E5%9B%AD&form=hpcapt&mkt=zh-cn","title":"徒步旅行者的向往之地","quiz":"/search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20230412_SnowdoniaNational%22&FORM=HPQUIZ","wp":true,"hsh":"23c6bc20066aa8692651709847ba04ac","drk":1,"top":1,"bot":1,"hs":[]}]
     */

    private List<ImagesBean> images;


    public List<ImagesBean> getImages() {
        return images;
    }

    public void setImages(List<ImagesBean> images) {
        this.images = images;
    }

    public static class ImagesBean {
        /**
         * startdate : 20230412
         * fullstartdate : 202304121600
         * enddate : 20230413
         * url : /th?id=OHR.SnowdoniaNational_ZH-CN7415540950_1920x1080.jpg&rf=LaDigue_1920x1080.jpg&pid=hp
         * urlbase : /th?id=OHR.SnowdoniaNational_ZH-CN7415540950
         * copyright : 斯诺登尼亚国家公园，威尔士，英国 (© Sebastian Wasek/eStock Photo)
         * copyrightlink : https://www.bing.com/search?q=%E6%96%AF%E8%AF%BA%E7%99%BB%E5%B0%BC%E4%BA%9A%E5%9B%BD%E5%AE%B6%E5%85%AC%E5%9B%AD&form=hpcapt&mkt=zh-cn
         * title : 徒步旅行者的向往之地
         * quiz : /search?q=Bing+homepage+quiz&filters=WQOskey:%22HPQuiz_20230412_SnowdoniaNational%22&FORM=HPQUIZ
         * wp : true
         * hsh : 23c6bc20066aa8692651709847ba04ac
         * drk : 1
         * top : 1
         * bot : 1
         * hs : []
         */

        private String startdate;
        private String fullstartdate;
        private String enddate;
        private String url;
        private String urlbase;
        private String copyright;
        private String copyrightlink;
        private String title;
        private String quiz;
        private boolean wp;
        private String hsh;
        private int drk;
        private int top;
        private int bot;
        private List<?> hs;

        public String getStartdate() {
            return startdate;
        }

        public void setStartdate(String startdate) {
            this.startdate = startdate;
        }

        public String getFullstartdate() {
            return fullstartdate;
        }

        public void setFullstartdate(String fullstartdate) {
            this.fullstartdate = fullstartdate;
        }

        public String getEnddate() {
            return enddate;
        }

        public void setEnddate(String enddate) {
            this.enddate = enddate;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUrlbase() {
            return urlbase;
        }

        public void setUrlbase(String urlbase) {
            this.urlbase = urlbase;
        }

        public String getCopyright() {
            return copyright;
        }

        public void setCopyright(String copyright) {
            this.copyright = copyright;
        }

        public String getCopyrightlink() {
            return copyrightlink;
        }

        public void setCopyrightlink(String copyrightlink) {
            this.copyrightlink = copyrightlink;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getQuiz() {
            return quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

        public boolean isWp() {
            return wp;
        }

        public void setWp(boolean wp) {
            this.wp = wp;
        }

        public String getHsh() {
            return hsh;
        }

        public void setHsh(String hsh) {
            this.hsh = hsh;
        }

        public int getDrk() {
            return drk;
        }

        public void setDrk(int drk) {
            this.drk = drk;
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public int getBot() {
            return bot;
        }

        public void setBot(int bot) {
            this.bot = bot;
        }

        public List<?> getHs() {
            return hs;
        }

        public void setHs(List<?> hs) {
            this.hs = hs;
        }
    }
}
