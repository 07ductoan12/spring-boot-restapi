package com.skyapi.weatherforecast;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.skyapi.weatherforecast.common.Location;

/**
 * GeolocationService
 */
@Service
public class GeolocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(GeolocationService.class);

    private String DBPath = "ip2locdb/IP2LOCATION-LITE-DB3.BIN";
    private IP2Location ipLocator = new IP2Location();

    public GeolocationService() {
        try {
            ipLocator.Open(DBPath);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    public Location getLocation(String ipAddress) throws GeoLocationException {
        try {
            IPResult ipResult = ipLocator.IPQuery(ipAddress);

            if (!"OK".equals(ipResult.getStatus())) {
                throw new GeoLocationException(
                        "Geolocaiton location failed with status: " + ipResult.getStatus());
            }

            LOGGER.info(ipResult.toString());

            return new Location(ipResult.getCity(), ipResult.getRegion(), ipResult.getCountryLong(),
                    ipResult.getCountryShort());
        } catch (IOException ex) {
            throw new GeoLocationException("Geolocation failed", ex);
        }
    }
}
