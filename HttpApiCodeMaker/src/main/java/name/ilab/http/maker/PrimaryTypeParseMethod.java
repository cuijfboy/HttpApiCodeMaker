package name.ilab.http.maker;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuijfboy on 16/1/22.
 */
public class PrimaryTypeParseMethod implements TemplateMethodModelEx {

    private final static Map<String, String> parseMethodMap =
            new HashMap<String, String>() {
                {
                    put("int", "%1$s == null ? 0 : Integer.parseInt(%1$s)");
                    put("Integer", "%1$s == null ? null : Integer.valueOf(%1$s)");

                    put("short", "%1$s == null ? 0 : Short.parseShort(%1$s)");
                    put("Short", "%1$s == null ? null : Short.valueOf(%1$s)");

                    put("long", "%1$s == null ? 0 : Long.parseLong(%1$s)");
                    put("Long", "%1$s == null ? null : Long.valueOf(%1$s)");

                    put("byte", "%1$s == null ? 0 : Byte.parseByte(%1$s)");
                    put("Byte", "%1$s == null ? null : Byte.valueOf(%1$s)");

                    put("boolean", "%1$s == null ? false : Boolean.parseBoolean(%1$s)");
                    put("Boolean", "%1$s == null ? null : Boolean.valueOf(%1$s)");

                    put("double", "%1$s == null ? 0 : Double.parseDouble(%1$s)");
                    put("Double", "%1$s == null ? null : Double.valueOf(%1$s)");

                    put("float", "%1$s == null ? 0 : Float.parseFloat(%1$s)");
                    put("Float", "%1$s == null ? null : Float.valueOf(%1$s)");

                    put("char", "%1$s == null ? 0 : %1$s.charAt(0)");
                    put("Character", "%1$s == null ? null : Character.valueOf(%1$s.charAt(0))");

                    put("String", "%1$s");
                }
            };

    public String getParseMethod(String type, String field) {
        String method = parseMethodMap.get(type);
        return method == null ?
                String.format("%2$s == null ? null : %1$s.valueOf(%2$s)", type, field) :
                String.format(method, field);
    }

    @Override
    public Object exec(List argList) throws TemplateModelException {
        if (argList.size() != 2 || argList.get(0) == null || argList.get(1) == null) {
            throw new TemplateModelException("Wrong argList");
        }
        String type = ((SimpleScalar) argList.get(0)).getAsString();
        String field = ((SimpleScalar) argList.get(1)).getAsString();
        return getParseMethod(type, field);
    }
}
