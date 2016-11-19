package org.ansoya.drugs.support;

import lombok.Builder;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

/**
 * Created by Administrator on 2016/11/6.
 */
@Builder
public class HttpClientWrap {

    private String url;
    private CloseableHttpClient httpClient;

    public synchronized CloseableHttpClient getHttpClient() {
        if (null == httpClient) {
            httpClient = HttpClients.createDefault();
        }
        return httpClient;
    }

    public byte[] post(byte[] bytes, String contentType) {
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new ByteArrayEntity(bytes));
            if (contentType != null)
                httpPost.setHeader("Content-type", contentType);
            CloseableHttpResponse httpResponse = getHttpClient().execute(httpPost);
            try {
                HttpEntity entityResponse = httpResponse.getEntity();
                int contentLength = (int) entityResponse.getContentLength();
                if (contentLength <= 0)
                    throw new IOException("No response");
                byte[] respBuffer = new byte[contentLength];
                if (entityResponse.getContent().read(respBuffer) != respBuffer.length)
                    throw new IOException("Read response buffer error");
                return respBuffer;
            } finally {
                httpResponse.close();
            }
        } catch (Throwable throwable) {
            throw new RuntimeException(throwable);
        }
    }
}
