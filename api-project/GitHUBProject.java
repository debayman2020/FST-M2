package liveprojects;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHUBProject {
    RequestSpecification requestspec;
    String ssh = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCqG3nlRnfCgguwDKGrJQkWOGOE3Gq0+XvDBj+Vjud2a6PgGAYEX+upayN1mpyQfiUZH5yiSzmO1rD/JOVLX9vtFHHPRU+WhqyLYSqyXxIDw9VEl1Sp+oW3EoYRiCnV+zLKqjst1Lu0qX4uPCOvSzIg9TakcdReRZqmo/zIv515alWyycxMvUu/ajLWnyqJzciDR33A4Z932TOIRh0gftb4XSOCPDLfGI0pYAf0Vl9E+YUfCLFlBz6kTwDiOpFZ/tAr9UpktmbG/j+rUGfFxao/QRhCUZjmFMhgejoHx+JIE4b1VelSHk3gVzs5OX914t9hhhnW5wPADxrID6a70rOZ ";
    int id = 0;

    @BeforeClass
    public void setup() {
        requestspec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "token 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")
                .build();


    }

    @Test(priority = 1)
    public void reqPost() {
        //reqBody
        String reqBody = "{\"title\":\"TestAPIKey\",\"key\":\"" + ssh + "\"}";

        Response response = given().spec(requestspec)
                .body(reqBody)
                .when().post("/user/keys");

        //System.out.println(response.getBody().asPrettyString());

        id = response.then().extract().body().path("id");
        response.then().statusCode(201);
    }


    @Test(priority = 2)
    public void reqGet(){
        Response res2 = given().spec(requestspec)
                .when().get("/user/keys/" + id);

        System.out.println(res2.getBody().asPrettyString());

        res2.then().statusCode(200);
    }

    @Test(priority = 3)
    public void reqDelete(){
        Response res3 = given().spec(requestspec)
                .when().delete("/user/keys/" + id);
        System.out.println(res3.getBody().asPrettyString());
        res3.then().statusCode(204);

    }


}
