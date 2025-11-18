package com.admeliora.briefbot.comment.tool;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class WeatherService {
    private static Logger logger = LoggerFactory.getLogger(WeatherService.class);

    public record WeatherResponse(Current current) {
        public record Current(LocalDateTime time, int interval, double temperature_2m) {}
    }

    @McpTool(description = "Get the temperature (in celsius) for a specific location")
    public WeatherResponse getTemperature(
            @McpToolParam(description = "The location latitude") double latitude,
            @McpToolParam(description = "The location longitude") double longitude) {


        System.out.println("MCP TOOL WORKS! Getting temperature for " + latitude + " and " + longitude);
        logger.debug("Debug log message");
        logger.debug("MCP TOOL WORKS! Getting temperature for ");

        return RestClient.create()
                .get()
                .uri("https://api.open-meteo.com/v1/forecast?latitude={latitude}&longitude={longitude}&current=temperature_2m",
                        latitude, longitude)
                .retrieve()
                .body(WeatherResponse.class);
    }

    public record PersonResponse(String name, String lastName, String location) {}

    @McpTool(description = "Get the Users list with locations")
    public Object getUsers() {

        System.out.println("USERS LIST TOOL WAS USED");

        return List.of(
                new PersonResponse("Michał", "Janisz", "Łódź"),
                new PersonResponse("Ewelina", "Janisz", "Łódź"),
                new PersonResponse("Mateusz", "Fru", "Huta Dłutowska"),
                new PersonResponse("Karolina", "Kasa", "Poznań")
        );
    }
}