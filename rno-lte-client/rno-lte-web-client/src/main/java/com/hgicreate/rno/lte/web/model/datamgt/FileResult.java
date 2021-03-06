package com.hgicreate.rno.lte.web.model.datamgt;

import lombok.Data;

@Data
public class FileResult {
    private boolean result;
    private String msg;
    private int statusCode;
    private String filename;
    private byte[] fileBody;
    private long fileLength;
}
