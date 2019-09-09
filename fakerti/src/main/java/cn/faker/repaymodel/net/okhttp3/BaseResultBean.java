package cn.faker.repaymodel.net.okhttp3;

/**
 * @author FLT
 * @function
 * @motto For The Future
 */
public  class BaseResultBean {
    private int code;//状态码
    private String msg;
    private String data;//数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }



    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
