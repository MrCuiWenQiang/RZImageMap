package cn.faker.repaymodel.mvp;

/**
 * Function :
 * Remarks  :
 * Created by Mr.C on 2018/12/18 0018.
 */
public class BaseMVPModel {
    public interface CommotListener<T> {
        void result(T t);
    }

    public static class MessageEntity<T> {
        private boolean status;
        private String message;
        private T data;

        public static MessageEntity success() {
            return success(null);
        }

        public static MessageEntity success(String msg) {
            return new MessageEntity(true, msg);
        }

        public static MessageEntity fail(String msg) {
            return new MessageEntity(false, msg);
        }

        public MessageEntity() {
        }

        public MessageEntity(boolean status, String message) {
            this.status = status;
            this.message = message;
        }

        public MessageEntity(boolean status, String message, T data) {
            this.status = status;
            this.message = message;
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public MessageEntity setData(T data) {
            this.data = data;
            return this;
        }

        public boolean isStatus() {
            return status;
        }

        public MessageEntity setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public MessageEntity setMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
