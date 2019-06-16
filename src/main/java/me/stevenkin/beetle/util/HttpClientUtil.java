package me.stevenkin.beetle.util;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 * Created by Administrator on 2016/8/24.
 */
public class HttpClientUtil {

    public static CloseableHttpClient createHttpsClient(){
        return  HttpClients.createDefault();
    }
}
