package com.wjx.android.wanandroidmvp.bean.rank;

import java.util.List;

/**
 * Created with Android Studio.
 * Description:
 *
 * @author: 王拣贤
 * @date: 2020/01/13
 * Time: 10:21
 */
public class RankData {

    /**
     * data : {"curPage":1,"datas":[{"coinCount":6674,"level":67,"rank":1,"userId":20382,"username":"g**eii"},{"coinCount":6269,"level":63,"rank":2,"userId":3559,"username":"A**ilEyon"},{"coinCount":6264,"level":63,"rank":3,"userId":27535,"username":"1**08491840"},{"coinCount":4890,"level":49,"rank":4,"userId":1260,"username":"于**家的吴蜀黍"},{"coinCount":4888,"level":49,"rank":5,"userId":2,"username":"x**oyang"},{"coinCount":4710,"level":48,"rank":6,"userId":29303,"username":"深**士"},{"coinCount":4348,"level":44,"rank":7,"userId":28694,"username":"c**ng0218"},{"coinCount":4120,"level":42,"rank":8,"userId":9621,"username":"S**24n"},{"coinCount":4107,"level":42,"rank":9,"userId":1534,"username":"j**gbin"},{"coinCount":4021,"level":41,"rank":10,"userId":2068,"username":"i**Cola"},{"coinCount":4007,"level":41,"rank":11,"userId":7710,"username":"i**Cola7"},{"coinCount":3994,"level":40,"rank":12,"userId":863,"username":"m**qitian"},{"coinCount":3985,"level":40,"rank":13,"userId":3753,"username":"S**phenCurry"},{"coinCount":3900,"level":39,"rank":14,"userId":7590,"username":"陈**啦啦啦"},{"coinCount":3837,"level":39,"rank":15,"userId":833,"username":"w**lwaywang6"},{"coinCount":3770,"level":38,"rank":16,"userId":25793,"username":"F**_2014"},{"coinCount":3742,"level":38,"rank":17,"userId":23244,"username":"a**ian"},{"coinCount":3732,"level":38,"rank":18,"userId":28607,"username":"S**Brother"},{"coinCount":3715,"level":38,"rank":19,"userId":7809,"username":"1**23822235"},{"coinCount":3692,"level":37,"rank":20,"userId":1871,"username":"l**shifu"},{"coinCount":3659,"level":37,"rank":21,"userId":6142,"username":"c**huah"},{"coinCount":3659,"level":37,"rank":22,"userId":7891,"username":"h**zkp"},{"coinCount":3642,"level":37,"rank":23,"userId":15603,"username":"r**eryxx"},{"coinCount":3634,"level":37,"rank":24,"userId":14829,"username":"l**changwen"},{"coinCount":3624,"level":37,"rank":25,"userId":12351,"username":"w**igeny"},{"coinCount":3623,"level":37,"rank":26,"userId":7541,"username":"l**64301766"},{"coinCount":3590,"level":36,"rank":27,"userId":27,"username":"y**ochoo"},{"coinCount":3590,"level":36,"rank":28,"userId":2160,"username":"R**iner"},{"coinCount":3557,"level":36,"rank":29,"userId":14032,"username":"M**eor"},{"coinCount":3557,"level":36,"rank":30,"userId":25419,"username":"蔡**打篮球"}],"offset":0,"over":false,"pageCount":518,"size":30,"total":15534}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * curPage : 1
         * datas : [{"coinCount":6674,"level":67,"rank":1,"userId":20382,"username":"g**eii"},{"coinCount":6269,"level":63,"rank":2,"userId":3559,"username":"A**ilEyon"},{"coinCount":6264,"level":63,"rank":3,"userId":27535,"username":"1**08491840"},{"coinCount":4890,"level":49,"rank":4,"userId":1260,"username":"于**家的吴蜀黍"},{"coinCount":4888,"level":49,"rank":5,"userId":2,"username":"x**oyang"},{"coinCount":4710,"level":48,"rank":6,"userId":29303,"username":"深**士"},{"coinCount":4348,"level":44,"rank":7,"userId":28694,"username":"c**ng0218"},{"coinCount":4120,"level":42,"rank":8,"userId":9621,"username":"S**24n"},{"coinCount":4107,"level":42,"rank":9,"userId":1534,"username":"j**gbin"},{"coinCount":4021,"level":41,"rank":10,"userId":2068,"username":"i**Cola"},{"coinCount":4007,"level":41,"rank":11,"userId":7710,"username":"i**Cola7"},{"coinCount":3994,"level":40,"rank":12,"userId":863,"username":"m**qitian"},{"coinCount":3985,"level":40,"rank":13,"userId":3753,"username":"S**phenCurry"},{"coinCount":3900,"level":39,"rank":14,"userId":7590,"username":"陈**啦啦啦"},{"coinCount":3837,"level":39,"rank":15,"userId":833,"username":"w**lwaywang6"},{"coinCount":3770,"level":38,"rank":16,"userId":25793,"username":"F**_2014"},{"coinCount":3742,"level":38,"rank":17,"userId":23244,"username":"a**ian"},{"coinCount":3732,"level":38,"rank":18,"userId":28607,"username":"S**Brother"},{"coinCount":3715,"level":38,"rank":19,"userId":7809,"username":"1**23822235"},{"coinCount":3692,"level":37,"rank":20,"userId":1871,"username":"l**shifu"},{"coinCount":3659,"level":37,"rank":21,"userId":6142,"username":"c**huah"},{"coinCount":3659,"level":37,"rank":22,"userId":7891,"username":"h**zkp"},{"coinCount":3642,"level":37,"rank":23,"userId":15603,"username":"r**eryxx"},{"coinCount":3634,"level":37,"rank":24,"userId":14829,"username":"l**changwen"},{"coinCount":3624,"level":37,"rank":25,"userId":12351,"username":"w**igeny"},{"coinCount":3623,"level":37,"rank":26,"userId":7541,"username":"l**64301766"},{"coinCount":3590,"level":36,"rank":27,"userId":27,"username":"y**ochoo"},{"coinCount":3590,"level":36,"rank":28,"userId":2160,"username":"R**iner"},{"coinCount":3557,"level":36,"rank":29,"userId":14032,"username":"M**eor"},{"coinCount":3557,"level":36,"rank":30,"userId":25419,"username":"蔡**打篮球"}]
         * offset : 0
         * over : false
         * pageCount : 518
         * size : 30
         * total : 15534
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * coinCount : 6674
             * level : 67
             * rank : 1
             * userId : 20382
             * username : g**eii
             */

            private int coinCount;
            private int level;
            private int rank;
            private int userId;
            private String username;

            public int getCoinCount() {
                return coinCount;
            }

            public void setCoinCount(int coinCount) {
                this.coinCount = coinCount;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public int getRank() {
                return rank;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }
        }
    }
}
