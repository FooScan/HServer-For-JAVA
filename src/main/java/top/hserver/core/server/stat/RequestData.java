package top.hserver.core.server.stat;


import java.text.SimpleDateFormat;
import java.util.Date;


public class RequestData {
    private String ip;
    private String url;
    private long sentBytes;
    private long receivedBytes;
    private long speed;
    private long consumeTime;
    private String time;

    public RequestData(String ip, String url, long sentBytes, long receivedBytes, long speed, long consumeTime) {
        this.ip = ip;
        this.url = url;
        this.sentBytes = sentBytes;
        this.receivedBytes = receivedBytes;
        this.speed = speed;
        this.consumeTime = consumeTime;
        this.time = CurrentTimeStamp();
    }

    private String CurrentTimeStamp() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentTime = new Date();
        return sdfDate.format(currentTime);
    }

    // ----- getters -----
    public String getIp() {
        return ip;
    }

    public String getUrl() {
        return url;
    }

    public long getSentBytes() {
        return sentBytes;
    }

    public long getReceivedBytes() {
        return receivedBytes;
    }

    public long getSpeed() {
        return speed;
    }

    public String getTime() {
        return time;
    }

    public long getConsumeTime() {
        return consumeTime;
    }
}
