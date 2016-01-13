package name.ilab.http.maker;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by cuijfboy on 15/11/28.
 */
@SupportedAnnotationTypes({"name.ilab.http.maker.HttpApiCode"})
public class HttpApiCodeProcessor extends AbstractProcessor {

    final String TAG = "[" + HttpApiCodeProcessor.class.getSimpleName() + "]";
    Messager messager;
    List<String> configFilePathList;
    HttpApiCodeMaker generator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        configFilePathList = new ArrayList<>();
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + " initialized!");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            for (String configFilePath : this.configFilePathList) {
                if (configFilePath.trim().equals("")) {
                    continue;
                }
                generator = generator == null ? new HttpApiCodeMaker() : generator;
                messager.printMessage(Diagnostic.Kind.NOTE, TAG + " Generating code file(s) for : " + configFilePath);
                File apiJsonFile = new File(configFilePath);
                if (!apiJsonFile.exists() || !apiJsonFile.isFile()) {
                    messager.printMessage(Diagnostic.Kind.WARNING,
                            TAG + " Could not find config file : " + configFilePath);
                    return true;
                }
                try {
                    generator.generate(apiJsonFile);
                    messager.printMessage(Diagnostic.Kind.NOTE,
                            TAG + " Code file(s) has been generated successfully for : " + configFilePath);
                } catch (Exception e) {
                    messager.printMessage(Diagnostic.Kind.WARNING,
                            TAG + " Failed to generate code for : " + configFilePath);
                    e.printStackTrace();
                }
            }
            return true;
        }
        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                if (HttpApiCode.class.getName().equals(typeElement.toString())) {
                    HttpApiCode httpApiCode = element.getAnnotation(HttpApiCode.class);
                    String configFilePath = httpApiCode.configFile();
                    configFilePathList.add(configFilePath);
                    messager.printMessage(Diagnostic.Kind.NOTE,
                            TAG + " Found HttpApiCode with config file : " + configFilePath);
                    messager.printMessage(Diagnostic.Kind.NOTE,
                            TAG + " Current directory : " + (new File("").getAbsolutePath()));
                }
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
