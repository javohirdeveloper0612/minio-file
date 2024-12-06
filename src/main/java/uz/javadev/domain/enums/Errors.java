package uz.retail.core.domain.enumeration;

import uz.isd.commons.result.HasResult;

public enum Errors implements HasResult {
    FILE_NOT_FOUND(-400, "FILE NOT FOUND"),
    TEMPLATE_ALREADY_EXIST(-1000, "TEMPLATE WITH THIS NAME ALREADY EXISTS"),
    FILE_UPLOAD_FAIL(-1001, "FILE COULD NOT BE UPLOADED"),
    FILE_DOWNLOAD_FAIL(-1002, "FILE COULD NOT DOWNLOAD"),
    INVALID_BUCKET_NAME(-1003, "INVALID BUCKET NAME"),
    SERVICE_NOT_IMPLEMENTED(10021, "SERVICE NOT IMPLEMENTED"),
    UNSUPPORTED_TEMPLATE_FILE(-1004, "UNSUPPORTED CONTENT TYPE"),
    INVALID_REQUEST(-1005, "INVALID REQUEST"),
    FILE_DELETE_FAIL(-1005,"FILE_DELETE_FAIL")
    ;

    private final String message;
    private final Integer code;

    Errors(Integer code, String message) {
        this.message = message;
        this.code = code;
    }


    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

}
