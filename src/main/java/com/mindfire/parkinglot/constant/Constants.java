package com.mindfire.parkinglot.constant;

public class Constants {
	
	 // General API Messages
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String LOGIN_SUCCESS = "Login successful, returns JWT token";
    public static final String TOKEN_KEY = "token";

    // HTTP Status Messages
    public static final String UNAUTHORIZED = "Unauthorized";

    // Error codes
    public static final int UNAUTHORIZED_CODE = 401;
    public static final int SUCCESS_CODE = 200;
    public static final int BAD_REQUEST_CODE = 400;
    public static final int NOT_FOUND_CODE = 404;

    // JWT-related messages
    public static final String JWT_LOG_MESSAGE = "Generated JWT Token: ";
    
    // Car Parking Messages
    public static final String PARKING_SUCCESS = "Car parked successfully";
    public static final String PARKING_LOT_FULL = "Parking lot is full";
    public static final String SLOT_RETRIEVED = "Slot retrieved successfully";
    public static final String SLOT_NOT_FOUND = "Slot not found for the given ID - ";
    public static final String UNPARKING_SUCCESS = "Car unparked successfully";
    public static final String CAR_NOT_FOUND = "Car not found";
    
    // Swagger required messages
    public static final String API_TITLE = "Parking Lot API";
    public static final String SECURITY_SCHEME_NAME = "mindfire";
    public static final String SECURITY_SCHEME_TYPE = "bearer";
    public static final String BEARER_FORMAT = "JWT";
    

	

}
