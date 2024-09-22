package api.tests;

import static org.testng.Assert.assertEquals;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import com.github.javafaker.Faker;

import api.endpoints.ThruPropertiesFile;
import api.endpoints.UserEndPoints;
import api.payload.AddPlaces;
import api.payload.Location;

public class AddPlaceTestByPropertiesFile {
   Faker faker;
   AddPlaces addpla;
   Location location; // Assuming you have a Location class
   String placeId;
   
   public Logger logger;

   @BeforeClass
   public void SetupData() {
	   faker=new Faker();
	   addpla = new AddPlaces();
       location = new Location();

    // Set the Location data (latitude and longitude)
       location.setLat(faker.number().randomDouble(6, -90, 90)); // Random latitude between -90 and 90
       location.setLng(faker.number().randomDouble(6, -180, 180)); // Random longitude between -180 and 180

       // Set other fields of AddPlaces with Faker data
       addpla.setLocation(location);
       addpla.setAccuracy(faker.number().numberBetween(1, 100));
       addpla.setName(faker.company().name());
       addpla.setPhone_number(faker.phoneNumber().cellPhone());
       addpla.setAddress(faker.address().fullAddress());
       addpla.setTypes(Arrays.asList("park", "shop")); // This can be static or dynamic depending on your need
       addpla.setWebsite(faker.internet().url());
       addpla.setLanguage("English");
       // Print out to verify the generated data
       System.out.println("Generated AddPlaces Data: "+addpla.toString());
       System.out.println(addpla);	   
       
       //logs
  logger = LogManager.getLogger(this.getClass());
   }
   
   @Test(priority = 1)
	public void createPlace() {
	   logger.info("**************Creating AddPlace Post Request*************");

	Response response=	ThruPropertiesFile.addPlace(addpla);
	response.then().log().all().assertThat().statusCode(200);
	Assert.assertEquals(response.getStatusCode(), 200);
	String ss= response.asString();
	JsonPath js= new JsonPath(ss);
	placeId = js.getString("place_id");

    // Print the extracted placeId and status
    System.out.println("Place ID: " + placeId);
	String status = js.getString("status");
	System.out.println("status is" + " " +status);
	
	//System.out.println("Response is" + ""+ ss);
    logger.info("**************Created AddPlace POST*****************");

	
	}
   
   @Test(priority = 2)
	public void getResponseByPlaceId() {
	   
	   logger.info("**************GET getting addPlace info*************");

		Response response= ThruPropertiesFile.getPlace(placeId);
		response.then().log().all();
		  // Log the full response
	      String getResp =  response.then().extract().response().asString();

	      System.out.println("get request reposnse is : " +" "+getResp);
	       // Assert the status code to ensure the place details were retrieved
	       Assert.assertEquals(response.getStatusCode(), 200);
	       logger.info("**************addPlace info is displayed*****************");
	   }
   
   @Test(priority = 3)
	public void updateByPlaceId() {
 	   logger.info("**************Updating addPlace info*****************");

		 // Update the place with a new address
        String newAddress = faker.address().fullAddress();
        
     // Send PUT request to update the place with the new address
        Response responsed = ThruPropertiesFile.updatePlace(placeId, newAddress);
        responsed.then().log().all().assertThat().statusCode(200);
        
        // Verify the update success message
        String updateResp = responsed.asString();
        JsonPath js = new JsonPath(updateResp);
        String msg = js.getString("msg");
        Assert.assertEquals(msg, "Address successfully updated");

        System.out.println("Update response message: " + msg);
        
     // Check the updated response by using the GET method again
        Response updatedresponse = ThruPropertiesFile.getPlace(placeId);
        updatedresponse.then().log().all();

        // Log the full response after updating the place
        String getResp = updatedresponse.then().extract().response().asString();
        System.out.println("GET request response after update: " + getResp);
        
  	   logger.info("*************AddPlace API is updated*****************");

    }

   @Test(priority = 4)
	public void deleteByPlaceId() {
	   
 	   logger.info("**************Deleting addPlace info*****************");

	   Response response = ThruPropertiesFile.deletePlace(placeId);
	    response.then().log().all().assertThat().statusCode(200);

	    // Verify the delete success message
	    String deleteResp = response.asString();
	    JsonPath js = new JsonPath(deleteResp);
	    String status = js.getString("status");
	    Assert.assertEquals(status, "OK");

	    System.out.println("Delete response status: " + status);
	  	   logger.info("*************AddPlace API is Deleted*****************");

	}
	   
	   
	   
   }

	
