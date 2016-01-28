package name.ilab.http.sample;


import name.ilab.http.maker.HttpApiCode;


/**
 * Created by cuijfboy on 15/11/28.
 */

@HttpApiCode(configuration = "api")
public class Main {

    private static void generateHttpApiCode() {
//        new HttpApiCodeMaker().generate(
//                new File("full_sample_api.json"));
//
//        System.out.println();
//        System.out.println("Api code has been generated according to full_sample_api.json");
    }

    private static void invokeHttpApiCode() {
//        new SampleApiE() {
//            {
//                request.sampleApiEUrlArg1 = "1111111111111";
//                request.sampleApiEUrlArg2 = 22222222;
//            }
//
//        }.go();

//        new SampleApiA() {
//            {
//                request.commonRequestHeaderArg1 = "";
//                request.commonRequestHeaderArg2 = 111;
//                request.commonRequestBodyArg1 = "";
//                request.commonRequestBodyArg2 = 222;
//                request.commonModelHeaderArg1 = "";
//                request.commonModelHeaderArg2 = 333;
//                request.commonModelBodyArg1 = "";
//                request.commonModelBodyArg2 = 444;
//                request.sampleApiARequestHeaderArg1 = "";
//                request.sampleApiARequestHeaderArg2 = 555;
//                request.sampleApiARequestHeaderArg3 = new SampleModelA();
//                request.sampleApiARequestHeaderArg3.commonModelHeaderArg1 = "";
//                request.sampleApiARequestHeaderArg3.commonModelHeaderArg2 = 666;
//                request.sampleApiARequestHeaderArg3.commonModelBodyArg1 = "";
//                request.sampleApiARequestHeaderArg3.commonModelBodyArg2 = 777;
//                request.sampleApiARequestBodyArg1 = "";
//                request.sampleApiARequestBodyArg2 = 888;
//                request.sampleApiARequestBodyArg3 = new SampleModelB();
//                request.sampleApiARequestBodyArg3.commonModelHeaderArg1 = "";
//                request.sampleApiARequestBodyArg3.commonModelHeaderArg2 = 999;
//                request.sampleApiARequestBodyArg3.commonModelBodyArg1 = "";
//                request.sampleApiARequestBodyArg3.commonModelBodyArg2 = 1000;
//            }
//
//            @Override
//            public boolean onResponse(int statusCode, Response response) {
//                System.out.println();
//
////                System.out.println("onResponse response = " + response);
////
////                System.out.println("onResponse response.commonResponseHeaderArg1 = " + response.commonResponseHeaderArg1);
////                System.out.println("onResponse response.commonResponseHeaderArg2 = " + response.commonResponseHeaderArg2);
////                System.out.println("onResponse response.sampleApiAResponseHeaderArg1 = " + response.sampleApiAResponseHeaderArg1);
////                System.out.println("onResponse response.sampleApiAResponseHeaderArg2 = " + response.sampleApiAResponseHeaderArg2);
////
////                System.out.println("onResponse response.commonResponseBodyArg1 = " + response.commonResponseBodyArg1);
////                System.out.println("onResponse response.commonResponseBodyArg2 = " + response.commonResponseBodyArg2);
////                System.out.println("onResponse response.sampleApiAResponseBodyArg1 = " + response.sampleApiAResponseBodyArg1);
////                System.out.println("onResponse response.sampleApiAResponseBodyArg2 = " + response.sampleApiAResponseBodyArg2);
////                System.out.println("onResponse response.sampleApiAResponseBodyArg3 = " + response.sampleApiAResponseBodyArg3);
////                if (response.sampleApiAResponseBodyArg3 != null) {
////                    System.out.println("onResponse response.sampleApiAResponseBodyArg3.sampleApiAModelBArg1 = " +
////                            response.sampleApiAResponseBodyArg3.sampleApiAModelBArg1);
////                    System.out.println("onResponse response.sampleApiAResponseBodyArg3.sampleApiAModelBArg2 = " +
////                            response.sampleApiAResponseBodyArg3.sampleApiAModelBArg2);
////                }
//
//                return true;
//            }
//        }.go();
//
//        HttpApiHelper.getInstance().registerDefaultHttpClient(new SampleHttpClient());
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
//        }.go();
    }

    public static void main(String[] args) {
        System.out.println("\n********** main start **********");

//        generateHttpApiCode();

        invokeHttpApiCode();

        System.out.println("\n********** main finish **********");
    }
}
