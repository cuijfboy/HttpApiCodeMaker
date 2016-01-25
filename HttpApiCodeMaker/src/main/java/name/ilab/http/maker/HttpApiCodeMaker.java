package name.ilab.http.maker;

import com.google.gson.GsonBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import name.ilab.http.Utils;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApiCodeMaker {
    public static final String TEMPLATE_FOLDER = "/template";
    public static final String HTTP_API_TEMPLATE_NAME = "api.ftl";
    public static final String HTTP_API_GLOBAL_MODEL_TEMPLATE_NAME = "model.ftl";


    private HttpApiJson apiJson;
    private Template httpApiTemplate;
    private Template httpApiGlobalModelTemplate;

    public HttpApiCodeMaker() {
        System.out.println();
        Configuration freeMarkerConfig = new Configuration(Configuration.VERSION_2_3_23);
        freeMarkerConfig.setDefaultEncoding("UTF-8");

        try {
            freeMarkerConfig.setClassForTemplateLoading(getClass(), TEMPLATE_FOLDER);
            httpApiTemplate = freeMarkerConfig.getTemplate(HTTP_API_TEMPLATE_NAME);
            System.out.println("[HttpApiCodeMaker] httpApiTemplate loaded : "
                    + httpApiTemplate.getName());
            httpApiGlobalModelTemplate = freeMarkerConfig.getTemplate(HTTP_API_GLOBAL_MODEL_TEMPLATE_NAME);
            System.out.println("[HttpApiCodeMaker] httpApiGlobalModelTemplate loaded : "
                    + httpApiGlobalModelTemplate.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        System.out.println("[HttpApiCodeMaker] initialized !");
    }

    public void generate(String apiJsonInfo) {
        loadApiInfo(apiJsonInfo);
        generateGlobalModelCode();
        generateBaseModelCode();
        generateApiCode();
    }

    public void generate(File apiJsonFile) {
        String apiJsonInfo = Utils.loadStringFromFile(apiJsonFile);
        generate(apiJsonInfo);
    }

    private void loadApiInfo(String apiJsonInfo) {
        System.out.println();
        System.out.println("[HttpApiCodeMaker] loading API config data ...");
        apiJson = new GsonBuilder().serializeNulls().create().fromJson(apiJsonInfo, HttpApiJson.class);
        apiJson.refresh();
        System.out.println();
        System.out.println("[HttpApiCodeMaker] API config data loaded !");
    }

    public void generateApiCode() {
        for (HttpApi api : apiJson.getApiMap().values()) {
            System.out.println();
            System.out.println("[HttpApiCodeMaker] generating API : \n " + api);
            generateCodeFile(api, httpApiTemplate);
        }
        System.out.println();
        System.out.println("[HttpApiCodeMaker] generated " + apiJson.getApiMap().size() + " API code file(s).");
    }

    private void generateGlobalModelCode() {
        HttpApi global = apiJson.getGlobalConfig();
        for (Map.Entry<String, Map<String, String>> model : global.getModel().entrySet()) {
            generateModelCode(model.getKey(), null, null, model.getValue());
        }
        System.out.println();
        System.out.println("[HttpApiCodeMaker] generated " + apiJson.getGlobalConfig().getModel().size()
                + " model code file(s).");
    }

    private void generateBaseModelCode() {
        generateModelCode(HttpApi.BASE_REQUEST_MODEL, null,
                apiJson.getGlobalConfig().getRequestParameterMap());
        generateModelCode(HttpApi.BASE_RESPONSE_MODEL, HttpApi.BASE_RESPONSE,
                apiJson.getGlobalConfig().getResponseParameterMap());
        System.out.println();
        System.out.println("[HttpApiCodeMaker] generated base model code files.");
    }

    private void generateModelCode(String name, String parentClass,
                                   Map<String, String> headerParameterMap, Map<String, String> bodyParameterMap) {
        Map<String, Map<String, String>> parameterMap = new HashMap<>();
        parameterMap.put(HttpApi.HEADER, headerParameterMap);
        parameterMap.put(HttpApi.BODY, bodyParameterMap);
        generateModelCode(name, parentClass, parameterMap);
    }

    private void generateModelCode(String name, String parentClass, Map<String, Map<String, String>> parameterMap) {
        HttpApi global = apiJson.getGlobalConfig();
        HttpApi api = new HttpApi();
        api.setOutputPath(global.getOutputPath());
        api.setPackageName(global.getPackageName());
        api.setImportList(global.getImportList());
        api.setName(name);
        HttpApi.prepareParameterMap(parameterMap);
        api.setRequestParameterMap(parameterMap);
        api.setParentClass(parentClass);
        System.out.println();
        System.out.println("[HttpApiCodeMaker] generating model : \n " + name + " : " + parameterMap);
        generateCodeFile(api, httpApiGlobalModelTemplate);
    }

    private void generateCodeFile(HttpApi api, Template template) {
        File outputFolder = new File(api.getOutputPath());
        if (!outputFolder.exists()) {
            System.out.println("[HttpApiCodeMaker] create output folder : " + outputFolder.getAbsolutePath());
            outputFolder.mkdirs();
        }
        String codeFilePath = api.getOutputPath() + File.separator + api.getName() + ".java";
        Map<String, Object> root = new HashMap<>();
        root.put("api", api);
        root.put("parsePrimaryTypeData", new PrimaryTypeParseMethod());
        try {
            Writer writer = new OutputStreamWriter(new FileOutputStream(codeFilePath));
            template.process(root, writer);
            System.out.println("[HttpApiCodeMaker] " + api.getName() + " generated as " + codeFilePath);
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
