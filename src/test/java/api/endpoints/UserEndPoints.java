package api.endpoints;

import static io.restassured.RestAssured.given;

import api.payload.AddPlaces;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class UserEndPoints {
// Inside this class we will write only given and then and use payload variable
	
	public static Response addPlace(AddPlaces addplace) {// this method will be called in AddPlaceTest class inside createPlace() method
		
		
        
		// Perform the POST request using RestAssured and return the response
        Response response = given().log().all()
            .queryParam("key", Routes.API_KEY)
            .header("Content-Type", "application/json")
            .body(addplace)
            .when().post(Routes.ADD_PLACE);

        return response;
    }
	
	
	 // Example method for getting a place (GET request)
    public static Response getPlace(String placeId) {
        
        Response response = given()
            .queryParam("key", Routes.API_KEY)
            .queryParam("place_id", placeId)
            .header("Content-Type", "application/json")
            .when().get(Routes.GET_PLACE);
        
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
            .when().put(Routes.UPDATE_PLACE);
        
        return response;
    }
    
 // Example method for deleting a place (DELETE request)
    public static Response deletePlace(String placeId) {
        
        String deletePayload = "{ \"place_id\": \"" + placeId + "\" }"; // Create JSON payload for deletion
        
        Response response = given()
            .queryParam("key", Routes.API_KEY)
            .header("Content-Type", "application/json")
            .body(deletePayload)
            .when().delete(Routes.DELETE_PLACE);
        
        return response;
    }
    
    
}
