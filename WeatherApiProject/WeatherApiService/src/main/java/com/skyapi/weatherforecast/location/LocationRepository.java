package com.skyapi.weatherforecast.location;

import com.skyapi.weatherforecast.common.Location;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/** LocationRepository */
public interface LocationRepository extends CrudRepository<Location, String> {

    @Query("SELECT l FROM Location l WHERE l.trashed = false")
    public List<Location> findUntrashed();

    @Query("SELECT l FROM Location l WHERE l.trashed = false AND l.code = ?1")
    public Location findByCode(String locationCode);

    @Modifying
    @Query("UPDATE Location SET trashed = true WHERE code = ?1")
    public void trashedByCode(String locationCode);
}
