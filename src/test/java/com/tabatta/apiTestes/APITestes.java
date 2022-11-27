package com.tabatta.apiTestes;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITestes {
    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetronarTarefas() {
        RestAssured.given()
                .when()
                    .get("/todo")
                .then()
                    .statusCode(200)
        ;
    }
    @Test
    public void adcionarTarefaComSucesso(){
        RestAssured.given()
                    .body("{\"task\": \"Teste via API\", \"dueDate\": \"2022-11-29\" }\n")
                    .contentType(ContentType.JSON)
                .when()
                    .post("/todo")
                .then()
                    .log().all()
                    .statusCode(201);
    }
    @Test
    public void naoDeveAdcionarTarefaInvalida(){
        RestAssured.given()
                .body("{\"task\": \"Teste via API\", \"dueDate\": \"2012-11-29\" }\n")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .log().all()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}
