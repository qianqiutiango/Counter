package cn.edu.cn.counter.domain;

public class RateItem {
    private int id;
    private String CurName;
    private String CurRate;

    public RateItem() {
    }

    public RateItem(int id, String curName, String curRate) {
        this.id = id;
        CurName = curName;
        CurRate = curRate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCurName() {
        return CurName;
    }

    public void setCurName(String curName) {
        CurName = curName;
    }

    public String getCurRate() {
        return CurRate;
    }

    public void setCurRate(String curRate) {
        CurRate = curRate;
    }
}
