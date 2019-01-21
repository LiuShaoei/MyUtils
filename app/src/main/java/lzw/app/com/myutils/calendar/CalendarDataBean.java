package lzw.app.com.myutils.calendar;

/**
 * Created by LiuZhaowei on 2019/1/21 0021.
 */
public class CalendarDataBean {
    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    private boolean state;
    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    private String platform;
    private int amount;
}
