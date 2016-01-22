package name.ilab.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuijfboy on 16/1/21.
 */
public class HttpApiHelper {

    public static final String DEFAULT_HTTP_CLIENT_NAME = "[DEFAULT_CLIENT]";

    private static HttpApiHelper instance;

    private final Map<String, IHttpClient> clientMap;
    private final List<String> badClientList;
    private final Map<String, IApiHook> hookMap;
    private final List<String> badHookList;

    public static synchronized HttpApiHelper getInstance() {
        return instance == null ? (instance = new HttpApiHelper()) : instance;
    }

    private HttpApiHelper() {
        clientMap = new HashMap<>();
        badClientList = new ArrayList<>();
        hookMap = new HashMap<>();
        badHookList = new ArrayList<>();
    }

    public IHttpClient getHttpClient(String name) {
        synchronized (clientMap) {
            if (name == null || badClientList.contains(name)) {
                return null;
            }
            IHttpClient client = clientMap.get(name);
            if (client == null) {
                try {
                    client = (IHttpClient) Class.forName(name).newInstance();
                    registerHttpClient(name, client);
                } catch (Exception e) {
                    e.printStackTrace();
                    badClientList.add(name);
                    client = null;
                }
            }
            return client;
        }
    }

    public IHttpClient getDefaultHttpClient() {
        return getHttpClient(DEFAULT_HTTP_CLIENT_NAME);
    }

    public void registerDefaultHttpClient(IHttpClient client) {
        registerHttpClient(DEFAULT_HTTP_CLIENT_NAME, client);
    }

    public void registerHttpClient(String name, IHttpClient client) {
        synchronized (clientMap) {
            clientMap.put(name, client);
            badClientList.remove(name);
        }
    }

    public void unregisterHttpClient(String name) {
        synchronized (clientMap) {
            clientMap.remove(name);
            badClientList.remove(name);
        }
    }

    public IApiHook getApiHook(String name) {
        synchronized (hookMap) {
            if (name == null || badHookList.contains(name)) {
                return null;
            }
            IApiHook hook = hookMap.get(name);
            if (hook == null) {
                try {
                    hook = (IApiHook) Class.forName(name).newInstance();
                    registerApiHook(name, hook);
                } catch (Exception e) {
                    e.printStackTrace();
                    badHookList.add(name);
                    hook = null;
                }
            }
            return hook;
        }
    }

    public void registerApiHook(String name, IApiHook hook) {
        synchronized (hookMap) {
            hookMap.put(name, hook);
            badHookList.remove(name);
        }
    }

    public void unregisterApiHook(String name) {
        synchronized (hookMap) {
            hookMap.remove(name);
            badHookList.remove(name);
        }
    }

}
