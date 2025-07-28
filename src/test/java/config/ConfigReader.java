package config;

public class ConfigReader {
    private static final String DEFAULT_BASE_URL = "https://fakerestapi.azurewebsites.net";

    public static String getBaseUrl() {
        String url = System.getProperty("baseUrl");
        return (url != null && !url.isEmpty()) ? url : DEFAULT_BASE_URL;
    }
}