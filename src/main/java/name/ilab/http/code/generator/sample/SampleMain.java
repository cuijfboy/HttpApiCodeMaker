package name.ilab.http.code.generator.sample;


import name.ilab.http.code.generator.annotation.HttpApiCode;

/**
 * Created by cuijfboy on 15/11/28.
 */

@HttpApiCode(configFile = "./res/sample_api.json")
public class SampleMain {

//    private static void generateHttpApiCode() {
//        new HttpApiCodeMaker().generate(
//                new File("./res/sample_api.json"));
//
//        System.out.println();
//        System.out.println("Api code has been generated according to ./res/sample_api.json");
//    }

//    private static void invokeHttpApiCode() {
//        new LoginRequest3() {
//            {
//                LoginRequest3.userName = "admin@example.com";
//                LoginRequest3.userPassword = "passw0rd";
//            }
//
//            @Override
//            public boolean onResponse(int statusCode, Response data) {
//                System.out.println();
//                System.out.println("onResponse data.errorCode = " + data.errorCode);
//                System.out.println("onResponse data.userId = " + data.userId);
//                System.out.println("onResponse data.nickName = " + data.nickName);
//                System.out.println("onResponse data.session = " + data.session);
//                return true;
//            }
//        }.go();
//    }

    public static void main(String[] args) {
        System.out.println("\n********** main start **********");

//        generateHttpApiCode();

//        invokeHttpApiCode();

        System.out.println("\n********** main finish **********");
    }
}
