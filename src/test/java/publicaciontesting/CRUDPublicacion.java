package publicaciontesting;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.hamcrest.Matchers.*;
import static io.restassured.RestAssured.*;
import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CRUDPublicacion {

    final String url = "https://jsonplaceholder.typicode.com/"; //Seteo la url con la que vamos a trabajar
    public static String value="2";


    @Before
    public void setUp() {
        RestAssured.config = new RestAssuredConfig().encoderConfig(EncoderConfig.encoderConfig().defaultContentCharset("UTF-8")); //Esto lo que hace es configurar Rest Assure con la codificacion UTF-8
        RestAssured.baseURI = url; //Seteo la baseURI
    }

    @Test
    public void a_createPost() {
        //Para el body
        Map<String, Object> post = new HashMap<>();
        post.put("title", "La casa de los muertos");
        post.put("body", "Libro de suspenso");
        post.put("userId", 8);

        JsonPath jsonPath = given().log().all().contentType(ContentType.JSON).body(post)
                .when().post("/posts")
                .then().log().all().assertThat().statusCode(201).and().body("id", notNullValue()
                        , "title", equalTo("La casa de los muertos")
                        , "body", equalTo("Libro de suspenso")
                        , "userId", equalTo(8)).extract().jsonPath();


    }


    @Test
    public void b_updatePost() {
        Map<String, Object> post = new HashMap<>();
        post.put("title", "La casa de los vivos");
        post.put("body", "Libro de aventuras");

        given().log().all().contentType(ContentType.JSON).body(post)
                .when().put("/posts/" + value)
                .then().log().all().assertThat().statusCode(200).and().body("title", equalTo("La casa de los vivos")
                , "body", equalTo("Libro de aventuras"));
    }


    @Test
    public void c_getPost() {
        given().log().all().contentType(ContentType.JSON)
                .when().get("/posts/" + value)
                .then().log().all().assertThat().statusCode(200);

    }


    @Test
    public void d_deletePost() {
        given().log().all().contentType(ContentType.JSON)
                .when().delete("posts/" + value)
                .then().log().all().assertThat().statusCode(200);

    }


}
