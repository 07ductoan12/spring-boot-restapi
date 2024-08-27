package com.toan.weatherforecast.location;

import org.springframework.data.repository.CrudRepository;

import com.toan.weatherforecast.common.Location;

public interface LocationRepository extends CrudRepository<Location, String> {

}
