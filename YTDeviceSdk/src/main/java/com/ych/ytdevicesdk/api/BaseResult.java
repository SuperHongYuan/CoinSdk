package com.ych.ytdevicesdk.api;


/**
 * @author huang
 */
public class BaseResult<T> {

    /**
     * ResponseStatus : {"ErrorCode":"0","Message":null}
     * Data : {}
     */


    private BaseResult.ResponseStatusBean ResponseStatus;
    private T Data;

    public BaseResult.ResponseStatusBean getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(BaseResult.ResponseStatusBean responseStatus) {
        this.ResponseStatus = responseStatus;
    }

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        this.Data = data;
    }

    public static class ResponseStatusBean {
        /**
         * ErrorCode : 0
         * Message : null
         */

        private long ErrorCode;
        private String Message;

        public long getErrorCode() {
            return ErrorCode;
        }

        public void setErrorCode(long errorCode) {
            this.ErrorCode = errorCode;
        }

        public String getMessage() {
            return Message;
        }

        public void setMessage(String message) {
            this.Message = message;
        }

        @Override
        public String toString() {
            return "ResponseStatusBean{" +
                    "ErrorCode='" + ErrorCode + '\'' +
                    ", Message='" + Message + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResponseBaseBean{" +
                "ResponseStatus=" + ResponseStatus +
                ", Data=" + Data +
                '}';
    }
}