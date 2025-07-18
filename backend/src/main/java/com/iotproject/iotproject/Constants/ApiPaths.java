package com.iotproject.iotproject.Constants;

public class ApiPaths {

    // Sensors 
    public static final String TRAFFIC_SENSOR = "/api/sensor/traffic-sensor/generate";
    public static final String STREET_LIGHT_SENSOR = "/api/sensor/street-light/generate";
    public static final String AIR_POLLUTION_SENSOR = "/api/sensor/air-pollution/generate";
    public static final String TRAFFIC_ALERTS = "/api/alerts/traffic";
    public static final String LIST_ALERTS = "/list";
    public static final String ACKNOWLEDGE_ALERTS = "/{id}/acknowledge";
    public static final String FILTER_STREET_LIGHT = "/api/sensor/street-light/filter";
    public static final String FILTER_TRAFFIC = "/api/sensor/traffic-sensor/filter";
    public static final String FILTER_AIR_POLLUTION = "/api/sensor/air-pollution/filter";


    // Auth
    public static final String AUTH_BASE = "/api/auth";
    public static final String INITIATE_SIGNUP = "/initiate-signup";
    public static final String COMPLETE_REGISTRATION = "/complete-registration";
    public static final String LOGIN = "/login";
    public static final String VERIFY_OTP = "/verify-otp";

    // Profile
    public static final String PROFILE_BASE = "/api/profile"; 
    public static final String UPDATE_PASSWORD = "/password";
    public static final String FORGET_PASSWORD = "/forgot-password";
    public static final String RESET_PASSWORD = "/reset-password";

    // Settings
    public static final String SETTING_BASE = "/api/settings";

    private ApiPaths() {
        // Prevent instantiation
    }
}
