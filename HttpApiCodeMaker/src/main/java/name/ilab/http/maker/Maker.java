package name.ilab.http.maker;

import com.google.gson.GsonBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import name.ilab.http.maker.model.*;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuijfboy on 16/1/25.
 */
public class Maker {
    private static Maker instance;

    public static final String TEMPLATE_FOLDER = "/template";
    public static final String HTTP_API_TEMPLATE_NAME = "api.ftl";
    public static final String HTTP_API_GLOBAL_MODEL_TEMPLATE_NAME = "model.ftl";
    public static final String API_JSON_FILE_SUFFIX = ".api.json";
    public static final String MODEL_JSON_FILE_SUFFIX = ".model.json";

    private Template apiTemplate;
    private Template modelTemplate;

    public static synchronized Maker getInstance() {
        return instance == null ? (instance = new Maker()) : instance;
    }

    private Maker() {
        Maker.logInfo();
        Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
        freeMarkerConfig.setDefaultEncoding("UTF-8");
        try {
            freeMarkerConfig.setClassForTemplateLoading(getClass(), TEMPLATE_FOLDER);
            apiTemplate = freeMarkerConfig.getTemplate(HTTP_API_TEMPLATE_NAME);
            Maker.logInfo("apiTemplate loaded : "
                    + apiTemplate.getName());
            modelTemplate = freeMarkerConfig.getTemplate(HTTP_API_GLOBAL_MODEL_TEMPLATE_NAME);
            Maker.logInfo("modelTemplate loaded : "
                    + modelTemplate.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        registerHttpQuickClass();
        Maker.logInfo();
        Maker.logInfo("initialized !");
    }

    private void registerHttpQuickClass() {
        AbstractClazz.registerQuickClass("HttpRequest", "name.ilab.http.HttpRequest");
        AbstractClazz.registerQuickClass("HttpResponse", "name.ilab.http.HttpResponse");
    }

    public void makeCode(List<File> fileList) {
        for (File file : fileList) {
            makeCode(file);
        }
    }

    public void makeCode(File... files) {
        makeCode(Arrays.asList(files));
    }

    public void makeCode(File file) {
        if (isModelJsonFile(file)) {
            makeModelCode(file);
        } else if (isApiJsonFile(file)) {
            makeApiCode(file);
        } else {
            throw new IllegalArgumentException(
                    "Illegal config file name : \"" + file.getName() + "\". " +
                            "Config file name should end with \".api.json\" or \".model.json\" ! ");
        }
    }

    public void makeCode(File[] apiFiles, File[] modelFiles) {
        makeApiCode(apiFiles);
        makeModelCode(modelFiles);
    }

    public void makeCode(String[] apiJsonArray, String[] modelJsonArray) {
        makeApiCode(apiJsonArray);
        makeModelCode(modelJsonArray);
    }

    public void makeApiCode(File... files) {
        for (File file : files) {
            makeApiCode(file);
        }
    }

    public void makeModelCode(File... files) {
        for (File file : files) {
            makeModelCode(file);
        }
    }

    public void makeApiCode(String... jsonArray) {
        for (String json : jsonArray) {
            makeApiCode(json);
        }
    }

    public void makeModelCode(String... jsonArray) {
        for (String json : jsonArray) {
            makeModelCode(json);
        }
    }

    public void makeApiCode(File file) {
        makeApiCode(loadStringFromFile(file));
    }

    public void makeModelCode(File file) {
        makeModelCode(loadStringFromFile(file));
    }

    public void makeApiCode(String json) {
        Maker.logInfo();
        Maker.logInfo("loading ApiSet data ...");
        ApiSet apiSet = new GsonBuilder().serializeNulls().create().fromJson(json, ApiSet.class);
        apiSet.refresh();
        Maker.logInfo();
        Maker.logInfo("ApiSet data loaded !");

        for (Api api : apiSet.getLocal().values()) {
            Maker.logInfo();
            Maker.logInfo("generating API : \n " + api);
            makeCodeFile(api, apiTemplate);
        }
        Maker.logInfo();
        Maker.logInfo("generated " + apiSet.getLocal().size() + " code file(s).");
    }

    public void makeModelCode(String json) {
        Maker.logInfo();
        Maker.logInfo("loading ModelSet data ...");
        ModelSet modelSet = new GsonBuilder().serializeNulls().create().fromJson(json, ModelSet.class);
        modelSet.refresh();
        Maker.logInfo();
        Maker.logInfo("ModelSet data loaded !");

        for (Model model : modelSet.getLocal().values()) {
            Maker.logInfo();
            Maker.logInfo("generating Model : \n " + model);
            makeCodeFile(model, modelTemplate);
        }
        Maker.logInfo();
        Maker.logInfo("generated " + modelSet.getLocal().size() + " code file(s).");
    }

    private void makeCodeFile(Clazz clazz, Template template) {
        String separatorInRegex = File.separator.equals("/") ? File.separator : "\\";
        File outputFolder = new File(clazz.getSourceFolder() + File.separator +
                clazz.getPackageName().replaceAll("\\.", separatorInRegex));
        if (!outputFolder.exists()) {
            Maker.logInfo("create output folder : " + outputFolder.getAbsolutePath());
            outputFolder.mkdirs();
        }
        String codeFilePath = outputFolder.getAbsolutePath() + File.separator + clazz.getName() + ".java";
        Map<String, Object> root = new HashMap<>();
        root.put("root", clazz);
        root.put("parsePrimaryTypeData", new PrimaryTypeParseMethod());
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(codeFilePath));
            template.process(root, writer);
            Maker.logInfo("" + clazz.getName() + " generated as " + codeFilePath);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public static boolean isSupportedJsonFile(File file) {
        return isApiJsonFile(file) || isModelJsonFile(file);
    }

    public static boolean isApiJsonFile(File file) {
        return file.getName().toLowerCase().endsWith(API_JSON_FILE_SUFFIX);
    }

    public static boolean isModelJsonFile(File file) {
        return file.getName().toLowerCase().endsWith(MODEL_JSON_FILE_SUFFIX);
    }

    public static void logInfo(String msg) {
        System.out.println("[HttpApiCodeMaker] " + msg);
    }

    public static void logInfo() {
        System.out.println();
    }

    public static void logError(String msg) {
        System.err.println("[HttpApiCodeMaker] " + msg);
    }
}
