package com.soubw.glideporgress;

/**
 * author：WX_JIN
 * email：wangxiaojin@soubw.com
 * link: http://soubw.com
 */
interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}