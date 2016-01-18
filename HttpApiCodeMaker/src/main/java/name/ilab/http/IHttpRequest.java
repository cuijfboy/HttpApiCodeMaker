package name.ilab.http;

import java.io.File;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public interface IHttpRequest {

    HttpMethod getMethod();

    String getUrl();

    Map<String, String> getHeader();

    String getBody();

    ResponseType getResponseType();

    String getFileSavePath();

    void onResponse(int statusCode, Map<String, String> header, String body);

    void onResponse(int statusCode, Map<String, String> header, File file);

    void onResponse(int statusCode, Map<String, String> header, byte[] data);
}
