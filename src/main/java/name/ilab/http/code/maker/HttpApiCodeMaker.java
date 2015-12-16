package name.ilab.http.code.maker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuijfboy on 15/11/28.
 */
public class HttpApiCodeMaker {
    private final String TEMPLATE_FOLDER = "/template";
    private final String HTTP_API_TEMPLATE_NAME = "http_api.ftl";
    private final String HTTP_API_GLOBAL_MODEL_TEMPLATE_NAME = "http_api_global_model.ftl";

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
        generateApiCode();
    }

    public void generate(File apiJsonFile) {
        String apiJsonInfo = Utils.loadStringFromFile(apiJsonFile);
        generate(apiJsonInfo);
    }

    private void loadApiInfo(String apiJsonInfo) {
        System.out.println();
        System.out.println("[HttpApiCodeMaker] loading API config ...");
        apiJson = Utils.getSerializeNullGson().fromJson(apiJsonInfo, HttpApiJson.class);
        apiJson.refresh();
        System.out.println();
        System.out.println("[HttpApiCodeMaker] global config loaded :\n " + apiJson.getGlobalConfig());
    }

    public void generateApiCode() {
        for (HttpApi api : apiJson.getApiMap().values()) {
            System.out.println();
            System.out.println("[HttpApiCodeMaker] generating API : \n " + api);
            File outputFolder = new File(api.getCodeFileFolder());
            if (!outputFolder.exists()) {
                outputFolder.mkdirs();
            }
            generateCodeFile(api, httpApiTemplate);
        }
        System.out.println();
        System.out.println("[HttpApiCodeMaker] generated " + apiJson.getApiMap().size() + " API code file(s).");
    }

    private void generateGlobalModelCode() {
        HttpApi global = apiJson.getGlobalConfig();
        File outputFolder = new File(global.getCodeFileFolder());
        if (!outputFolder.exists()) {
            outputFolder.mkdirs();
        }
        for (Map.Entry<String, Map<String, String>> model : global.getModel().entrySet()) {
            HttpApi api = new HttpApi();
            api.setImportList(global.getImportList());
            api.setCodeFileFolder(global.getCodeFileFolder());
            api.setPackageName(global.getPackageName());
            api.setName(model.getKey());
            Map<String, Map<String, String>> parameterMap = new HashMap<>();
            parameterMap.put(model.getKey(), model.getValue());
            api.setModel(parameterMap);
            System.out.println();
            System.out.println("[HttpApiCodeMaker] generating global model : \n " + api);
            generateCodeFile(api, httpApiGlobalModelTemplate);
        }
        System.out.println();
        System.out.println("[HttpApiCodeMaker] generated " + apiJson.getGlobalConfig().getModel().size()
                + " api code file(s).");
    }

    private void generateCodeFile(HttpApi api, Template template) {
        String codeFilePath = api.getCodeFileFolder() + File.separator + api.getName() + ".java";
        Map<String, Object> root = new HashMap<>();
        root.put("api", api);
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
