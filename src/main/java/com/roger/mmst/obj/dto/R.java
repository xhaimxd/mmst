package com.roger.mmst.obj.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static R<Void> ok() {
        return ok(null);
    }

    public static <T> R<T> ok(T data) {
        return new R<>(200, "success", data);
    }

    public static R<Void> error(String message) {
        return new R<>(500, message, null);
    }

}
