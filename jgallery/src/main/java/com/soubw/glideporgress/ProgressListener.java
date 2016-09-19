package com.soubw.glideporgress;

/**
 * @author WX_JIN on 2016/9/18
 *         wangxiaojin@soubw.com
 *         http://soubw.com
 */
interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}