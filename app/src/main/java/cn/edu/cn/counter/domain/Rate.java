package cn.edu.cn.counter.domain;

public class Rate {
    private String nation;
    private String rate;

    public Rate(){
        super();
    }

    public Rate(String nation, String rate) {
        this.nation = nation;
        this.rate = rate;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
