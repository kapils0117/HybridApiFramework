package api.endpoints;

import static io.restassured.RestAssured.given;

import java.util.ResourceBundle;

import api.payload.AddPlaces;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ThruPropertiesFile {
// Inside this class we will write only given and then and use payload variable
	
	//Method created for getting URL'S from properties file
	static ResourceBundle getURL() {
		
		ResourceBundle routes= 	ResourceBundle.getBundle("routes");//It will load properties file , also here "routes" is properties file name
		return routes;
	}
	
	public static Response addPlace(AddPlaces addplace) {// this method will be called in AddPlaceTest class inside createPlace() method
		
		
        
		// Perform the POST request using RestAssured and return the response
		String posturl = getURL().getString("post_url");// calling above getURL method and passing variable from properties file to call posturl
        Response response = given().log().all()
            .queryParam("key", Routes.API_KEY)
            .header("Content-Type", "application/json")
            .body(addplace)
            .when().post(posturl);

        return response;
    }
	
	
	 // Example method for getting a place (GET request)
    public static Response getPlace(String placeId) {
		String geturl = getURL().getString("get_url");// calling above getURL method and passing variable from properties file to call geturl

        Response response = given()
            .queryParam("key", Routes.API_KEY)
            .queryParam("place_id", placeId)
            .header("Content-Type", "application/json")
            .when().get(geturl);
        
        return response;
    }
    
    // Example method for updating a place (PUT request)
   /* public static Response updatePlace(String placeId, AddPlace payload) {
        
        Response response = given()
            .queryParam("key", Routes.API_KEY)
            .header("Content-Type", "application/json")
            .body(payload)
            .when().put(Routes.UPDATE_PLACE);
        
        return response;
    }*/
    
    // Method to update a place (PUT request)
    public static Response updatePlace(String placeId, String address) {
		String updateurl = getURL().getString("put_url");// calling above getURL method and passing variable from properties file to call puturl

    	
         //Create the payload with placeId and new address
        String updatePayload = "{\n" +
                "\"place_id\":\"" + placeId + "\",\n" +
                "\"address\":\"" + address + "\",\n" +
                "\"key\":\"" + Routes.API_KEY + "\"\n" +
                "}";
        
        // Perform the PUT request using RestAssured and return the response
        Response response = given().log().all()
            .queryParam("key", Routes.API_KEY)
            .header("Content-Type", "application/json")
            .body(updatePayload)
            .when().put(updateurl);
        
        return response;
    }
    
 // Example method for deleting a place (DELETE request)
    public static Response deletePlace(String placeId) {
		String deleteurl = getURL().getString("delete_url");// calling above getURL method and passing variable from properties file to call deleteurl

        String deletePayload = "{ \"place_id\": \"" + placeId + "\" }"; // Create JSON payload for deletion
        
        Response response = given()
            .queryParam("key", Routes.API_KEY)
            .header("Content-Type", "application/json")
            .body(deletePayload)
            .when().delete(deleteurl);
        
        return response;
    }
    
    
}
