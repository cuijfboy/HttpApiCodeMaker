package name.ilab.http;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class Utils {

    private static IHttpClient mockHttpClient;

    public static synchronized IHttpClient getMockHttpClient() {
        return mockHttpClient == null ? (mockHttpClient = new MockHttpClient()) : mockHttpClient;
    }

    private static Map<String, IApiHook> hookMap = new HashMap<>();
    private static List<String> badHookList = new ArrayList<>();
    private static final SimpleHook EMPTY_HOOK = new SimpleHook();

    public static synchronized IApiHook getHook(String name) {
        if (name == null || badHookList.contains(name)) {
            return null;
        }
        IApiHook hook = hookMap.get(name);
        if (hook == null) {
            try {
                hook = (IApiHook) Class.forName(name).newInstance();
                hookMap.put(name, hook);
            } catch (Exception e) {
                e.printStackTrace();
                badHookList.add(name);
                hook = null;
            }
        }
        return hook;
    }

    public static String loadStringFromFile(File file) {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            closeQuietly(bufferedReader);
        }
        return stringBuilder.toString();
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException e) {
            // Empty
        }
    }

}
