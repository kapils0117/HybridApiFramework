package api.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.testng.annotations.Test;
import io.restassured.response.Response;


import api.endpoints.UserEndPoints;
import api.payload.AddPlaces;
import api.payload.Location;
import api.utilities.DataProviders;

public class DDTests {
	
	 // A list to store all the place IDs
    static List<String> placeIds = new ArrayList<>();

    @Test(priority = 1, dataProvider = "Data", dataProviderClass = DataProviders.class)
    public void AddPlacedataTest(String latStr, String lngStr, String accuracyStr, String name, String phone_number, String address, String types, String website, String language) {
        // Convert lat, lng, accuracy to appropriate types
        double lat = Double.parseDouble(latStr);
        double lng = Double.parseDouble(lngStr);
        int accuracy = Integer.parseInt(accuracyStr);

        // Create Location and AddPlaces objects
        Location location = new Location();
        location.setLat(lat);
        location.setLng(lng);

        AddPlaces addpla = new AddPlaces();
        addpla.setLocation(location);
        addpla.setAccuracy(accuracy);
        addpla.setName(name);
        addpla.setPhone_number(phone_number);
        addpla.setAddress(address);

        // Convert types string into a list
        List<String> typesList = Arrays.asList(types.split(","));
        addpla.setTypes(typesList);

        addpla.setWebsite(website);
        addpla.setLanguage(language);

        // Call the API to add the place
        Response response = UserEndPoints.addPlace(addpla);
        response.then().log().all().assertThat().statusCode(200);

        // Extract and store placeId in the list
        String placeId = response.jsonPath().getString("place_id");
        placeIds.add(placeId);
        System.out.println("Place ID added: " + placeId);
    }

    @Test(priority = 2)
    public void deleteAllPlacesTest() {
      
        	if (placeIds.size() > 0)
        		//OR if (!placeIds.isEmpty()) // we can use this condition also 
        {
            // Iterate over all placeIds and delete them
            for (String placeId : placeIds) {
                System.out.println("Deleting Place ID: " + placeId);
                Response response = UserEndPoints.deletePlace(placeId);
                response.then().log().all().assertThat().statusCode(200);
            }
            // Clear the list after deletion
            placeIds.clear();
        } else {
            System.out.println("No place IDs found to delete.");
        }
    }


}

