package org.ansoya.drugs.drugs.entity;

import org.ansoya.drugs.drugs.engine.Constans;

public abstract class Results {
    public static <T> Result<T> newSuccessResult(String statusText) {
        return newSuccessResult(null, statusText);
    }

    public static <T> Result<T> newSuccessResult(T data) {
        return newSuccessResult(data, Constans.RESULT_SUCCESS);
    }

    public static <T> Result<T> newSuccessResult(T data, String statusText) {
        return newResult(data, Boolean.TRUE, statusText);
    }

    public static <T> Result<T> newFailedResult(String statusText) {
        return newFailedResult(null, statusText);
    }

    public static <T> Result<T> newFailedResult(T data, String statusText) {
        return newResult(data, Boolean.FALSE, statusText);
    }


    @SuppressWarnings({"rawtypes", "unchecked"})
    public static <T> Result<T> newResult(T data, boolean isSucess, String statusText) {

        Result<T> result = new Result();
        result.setData(data);
        result.setSucess(isSucess);
        result.setStatusText(statusText);
        return result;
    }
}
