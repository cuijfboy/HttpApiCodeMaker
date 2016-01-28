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
public class AnnotationProcessor extends AbstractProcessor {

    final String TAG = "[" + AnnotationProcessor.class.getSimpleName() + " : HttpApiCode]";
    Messager messager;
    List<File> fileList;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        fileList = new ArrayList<>();
        Maker.getInstance();
        messager.printMessage(Diagnostic.Kind.NOTE,
                TAG + " Current directory : " + (new File("").getAbsolutePath()));
        messager.printMessage(Diagnostic.Kind.NOTE, TAG + " initialized!");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        if (annotations.size() == 0) {
            messager.printMessage(Diagnostic.Kind.NOTE, TAG + " Generating code files ...");
            try {
                Maker.getInstance().makeCode(fileList);
            } catch (Exception e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.ERROR, TAG + " FATAL ERROR !!!");
            }
            messager.printMessage(Diagnostic.Kind.NOTE, TAG + " Generating code files ... DONE !");
            return true;
        }
        for (TypeElement typeElement : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(typeElement)) {
                if (HttpApiCode.class.getName().equals(typeElement.toString())) {
                    HttpApiCode httpApiCode = element.getAnnotation(HttpApiCode.class);
                    collectConfiguration(httpApiCode.configuration());
                }
            }
        }
        return true;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private void collectConfiguration(String[] configuration) {
        for (String path : configuration) {
            messager.printMessage(Diagnostic.Kind.NOTE,
                    TAG + " Found HttpApiCode annotation with configuration : " + path);
            if (path != null && !path.trim().equals("")) {
                File file = new File(path);
                if (file.exists()) {
                    if (file.isFile() && Maker.isSupportedJsonFile(file)) {
                        fileList.add(file);
                        messager.printMessage(Diagnostic.Kind.NOTE,
                                TAG + " Got one valuable config file : " + file);
                        continue;
                    } else if (file.isDirectory()) {
                        messager.printMessage(Diagnostic.Kind.NOTE,
                                TAG + " This is a folder : " + path);
                        for (File subFile : file.listFiles()) {
                            if (subFile.exists() && subFile.isFile() && Maker.isSupportedJsonFile(subFile)) {
                                fileList.add(subFile);
                                messager.printMessage(Diagnostic.Kind.NOTE,
                                        TAG + " Got one valuable config file : " + file);
                                continue;
                            }
                            messager.printMessage(Diagnostic.Kind.WARNING,
                                    TAG + " Not a valuable config file : " + subFile);
                        }
                        continue;
                    }
                }
            }
            messager.printMessage(Diagnostic.Kind.WARNING,
                    TAG + " Not a valuable config file : " + path);
        }
    }

}
