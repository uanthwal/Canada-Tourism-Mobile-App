package com.developer.tourismapp;

public class AppConstants {
    public static String BASE_URL = "http://travel-canada-elb-1597027388.us-east-1.elb.amazonaws.com";
    public static String LOGIN_URL = BASE_URL + ":3000/login";
    public static String REGISTER_URL = BASE_URL + ":3000/register";
    public static String VERIFY_OTP = BASE_URL + ":3000/verify-otp";
    public static String SEARCH_URL = BASE_URL + ":3001/api/search";
    public static String GET_TRAVEL_MODES = BASE_URL + ":3002/api/modes";
    public static String GET_ALL_PROVINCES = BASE_URL + ":3002/api/get-all-provinces";
    public static String GET_BOOKING_BY_ID = BASE_URL + ":3002/api/get-booking-by-id";
    public static String GET_USER_INFO_BY_SESSION = BASE_URL + ":3000/api/get-user-info-by-session";
    public static String BOOK_TICKET = BASE_URL + ":3002/api/book-ticket";
    public static String GET_HOTSPOTS = BASE_URL + ":3001/get-hotspots";
    public static String GENERATE_TICKET = BASE_URL + ":3002/api/generate-ticket";

    public static String S3_BASE_URL = "https://tourismapp14.s3.amazonaws.com/";
}
