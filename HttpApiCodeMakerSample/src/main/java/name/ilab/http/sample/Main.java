package name.ilab.http.sample;


import name.ilab.http.maker.HttpApiCode;
import name.ilab.http.maker.HttpApiCodeMaker;
import name.ilab.http.sample.generated.LoginRequest;

import java.io.File;


/**
 * Created by cuijfboy on 15/11/28.
 */

@HttpApiCode(configFile = "./res/sample_api.json")
public class Main {

    private static void generateHttpApiCode() {
        new HttpApiCodeMaker().generate(
                new File("./res/sample_api.json"));

        System.out.println();
        System.out.println("Api code has been generated according to ./res/sample_api.json");
    }

    private static void invokeHttpApiCode() {
        new LoginRequest() {
            {
                request.userName = "admin@example.com";
                request.userPassword = "passw0rd";
            }

            @Override
            public boolean onResponse(int statusCode, Response data) {
                System.out.println();
                System.out.println("onResponse data.errorCode = " + data.errorCode);
                System.out.println("onResponse data.userId = " + data.userId);
                System.out.println("onResponse data.nickName = " + data.nickName);
                System.out.println("onResponse data.session = " + data.session);
                return true;
            }
        }.go(new SampleHttpClient());
    }

    public static void main(String[] args) {
        System.out.println("\n********** main start **********");

//        generateHttpApiCode();

        invokeHttpApiCode();

        System.out.println("\n********** main finish **********");
    }
}
