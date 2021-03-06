/******************************************************************************

            This file is automatically generated by HttpApiCodeMaker.
            Do not modify this file -- YOUR CHANGES WILL BE ERASED!

******************************** CONFIGURATION ********************************

"SampleResponseModelA": "SampleResponseModelA": {
  "source": "./src/main/java/",
  "package": "name.ilab.http.sample.generated",
  "import": [
    "java.util.List",
    "java.util.Map",
    "name.ilab.http.HttpResponse"
  ],
  "inner": {},
  "name": "SampleResponseModelA",
  "extends": "HttpResponse",
  "implements": [],
  "body": {
    "sampleResponseModelABodyArg1": "String",
    "sampleResponseModelABodyArg2": "int",
    "commonModelBodyArg2": "int",
    "commonModelBodyArg1": "String"
  },
  "header": {
    "sampleResponseModelAHeaderArg1": "String",
    "sampleResponseModelAHeaderArg2": "int",
    "commonModelHeaderArg2": "int",
    "commonModelHeaderArg1": "String"
  }
}

******************************************************************************/

package name.ilab.http.sample.generated;

import java.util.List;
import java.util.Map;
import name.ilab.http.HttpResponse;

import name.ilab.http.HttpResponse;
import name.ilab.http.HttpMethod;
import name.ilab.http.ResponseType;
import java.util.Map;

import name.ilab.http.HttpApiHelper;

public class SampleResponseModelA extends HttpResponse {

    // header
    public transient String sampleResponseModelAHeaderArg1;
    public transient int sampleResponseModelAHeaderArg2;
    public transient int commonModelHeaderArg2;
    public transient String commonModelHeaderArg1;

    // body
    public String sampleResponseModelABodyArg1;
    public int sampleResponseModelABodyArg2;
    public int commonModelBodyArg2;
    public String commonModelBodyArg1;

    public SampleResponseModelA(HttpResponse httpResponse) {
        super(httpResponse);
    }

    public SampleResponseModelA(ResponseType responseType, int statusCode, HttpMethod method, String url,
            Map<String, String> header) {
        super(responseType, statusCode, method, url, header);
    }

    @Override
    public String toString() {
        return HttpApiHelper.toJson(this);
    }

    public static SampleResponseModelA valueOf(String valueString) {
        return HttpApiHelper.fromJson(valueString, SampleResponseModelA.class);
    }

}


