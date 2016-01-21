package name.ilab.http.sample;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import name.ilab.http.maker.HttpApiCode;
import name.ilab.http.maker.HttpApiCodeMaker;
import name.ilab.http.preset.FileDownloadRequest;
import name.ilab.http.sample.generated.SampleApiA;

import java.io.File;


/**
 * Created by cuijfboy on 15/11/28.
 */

@HttpApiCode(configFile = "full_sample_api.json")
public class Main {

    private static void generateHttpApiCode() {
        new HttpApiCodeMaker().generate(
                new File("full_sample_api.json"));

        System.out.println();
        System.out.println("Api code has been generated according to full_sample_api.json");
    }

    private static void invokeHttpApiCode() {
        new SampleApiA() {
            {
                request.commonRequestHeaderArg1 = "commonHeaderArg1";
                request.commonRequestHeaderArg2 = "commonHeaderArg2";
                request.sampleApiARequestHeaderArg1 = "headerArg1";
                request.sampleApiARequestHeaderArg2 = "headerArg2";

                request.commonRequestBodyArg1 = "commonBodyArg1";
                request.commonRequestBodyArg2 = 100;
                request.sampleApiARequestBodyArg1 = "bodyArg1";
                request.sampleApiARequestBodyArg2 = 200;
                request.sampleApiARequestBodyArg3 = new SampleApiAModelA();
                request.sampleApiARequestBodyArg3.sampleApiAModelAArg1 = "bodyArg3Ag1";
                request.sampleApiARequestBodyArg3.sampleApiAModelAArg2 = 300;
            }

            @Override
            public boolean onResponse(int statusCode, Response response) {
                System.out.println();

                System.out.println("onResponse response = " + response);

                System.out.println("onResponse response.commonResponseHeaderArg1 = " + response.commonResponseHeaderArg1);
                System.out.println("onResponse response.commonResponseHeaderArg2 = " + response.commonResponseHeaderArg2);
                System.out.println("onResponse response.sampleApiAResponseHeaderArg1 = " + response.sampleApiAResponseHeaderArg1);
                System.out.println("onResponse response.sampleApiAResponseHeaderArg2 = " + response.sampleApiAResponseHeaderArg2);

                System.out.println("onResponse response.commonResponseBodyArg1 = " + response.commonResponseBodyArg1);
                System.out.println("onResponse response.commonResponseBodyArg2 = " + response.commonResponseBodyArg2);
                System.out.println("onResponse response.sampleApiAResponseBodyArg1 = " + response.sampleApiAResponseBodyArg1);
                System.out.println("onResponse response.sampleApiAResponseBodyArg2 = " + response.sampleApiAResponseBodyArg2);
                System.out.println("onResponse response.sampleApiAResponseBodyArg3 = " + response.sampleApiAResponseBodyArg3);
                if (response.sampleApiAResponseBodyArg3 != null) {
                    System.out.println("onResponse response.sampleApiAResponseBodyArg3.sampleApiAModelBArg1 = " +
                            response.sampleApiAResponseBodyArg3.sampleApiAModelBArg1);
                    System.out.println("onResponse response.sampleApiAResponseBodyArg3.sampleApiAModelBArg2 = " +
                            response.sampleApiAResponseBodyArg3.sampleApiAModelBArg2);
                }

                return true;
            }
        }.go(new SampleHttpClient());

//        new FileDownloadRequest() {
//            {
//                url = "<downloadUrl>";
//                fileSavePath = "<fileSavePath>";
//            }
//
//            @Override
//            public boolean onResponse(int statusCode, Response response) {
//                System.out.println();
//                System.out.println("response : response.responseType = " + response.getResponseType());
//                System.out.println("response : response.url = " + response.getUrl());
//                System.out.println("response : response.file = " + response.file);
//                return true;
//            }
//        }.go(new SampleHttpClient());
    }

    public static void main(String[] args) {
        System.out.println("\n********** main start **********");

//        generateHttpApiCode();

        invokeHttpApiCode();

        System.out.println("\n********** main finish **********");
    }
}
