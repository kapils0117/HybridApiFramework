package api.endpoints;

public class Routes {
	
		// Base URL for all requests
		    public static  String BASE_URL = "https://rahulshettyacademy.com";
		    // Common query parameter (API Key)
		    public static  String API_KEY = "qaclick123";

		    // Route for adding a place (POST)
		    public static  String ADD_PLACE = BASE_URL+"/maps/api/place/add/json";
		    
		    // Route for getting a place (GET)
		    public static  String GET_PLACE = BASE_URL+"/maps/api/place/get/json";
		    
		    // Route for updating a place (PUT)
		    public static  String UPDATE_PLACE = BASE_URL+"/maps/api/place/update/json";
		    
		    // Route for deleting a place (DELETE)
		    public static  String DELETE_PLACE = BASE_URL+"/maps/api/place/delete/json";
		    
		   
	
}
