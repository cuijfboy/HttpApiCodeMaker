package name.ilab.http;

/**
 * Created by cuijfboy on 15/12/1.
 */
public class MockHttpClient implements IHttpClient {

    @Override
    public void request(IHttpRequest request) {
        System.out.println();
        System.out.println("MockHttpClient request.getMethod() = " + request.getMethod());
        System.out.println("MockHttpClient request.getUrl() = " + request.getUrl());
        System.out.println("MockHttpClient request.getHeader() = " + request.getHeader());
        System.out.println("MockHttpClient request.getBody() = " + request.getBody());
        System.out.println("MockHttpClient response.statusCode = " + 200);
        System.out.println("MockHttpClient response.header = " + null);
        System.out.println("MockHttpClient response.body = " + null);

        request.onResponse(200, null, null);
    }
}