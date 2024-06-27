package com.vehiculos.unow.infrastructure.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiExeceptionResponse {

    private String type = "/errors/uncategorized";
    private String title;
    private String code;
    private String detail;
    private String instance = "/errors/uncategorized/unow";

    public ApiExeceptionResponse(String type, String title, String code, String detail) {
        this.type = type;
        this.title = title;
        this.code = code;
        this.detail = detail;
    }
}
