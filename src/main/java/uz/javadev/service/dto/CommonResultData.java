package uz.retail.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResultData<T> implements Serializable {
    private Boolean success;
    private String message;
    private T data;

    public CommonResultData(T data) {
//        super(SUCCESS);
        this.data = data;
    }

    public static <T> CommonResultData<T> success() {
        return new CommonResultData<>(true, "OK", null);
    }

    public static <T> CommonResultData<T> success(T data) {
        return new CommonResultData<>(true, "OK", data);
    }

    public static <T> CommonResultData<T> success(String message, T data) {
        return new CommonResultData<>(true, message, data);
    }

    public static <T> CommonResultData<T> failed(String message, T data) {
        return new CommonResultData<>(false, message, data);
    }

    public static <T> CommonResultData<T> failed(String message) {
        return new CommonResultData<>(false, message, null);
    }
}
