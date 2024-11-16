package org.gopnik.service;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.GeocodingResult;
import org.gopnik.model.Drugstore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoogleMapsService
{

    private final GeoApiContext context;

    public GoogleMapsService(@Value("${google.api.key}") String apiKey)
    {
        this.context = new GeoApiContext.Builder()
                .apiKey(apiKey)
                .build();
    }


    public String findClosestDrugstore(String currentAddress, List<Drugstore> drugstores)
    {
        List<String> fullAddresses = drugstores.stream().map(Drugstore::getFullAddress).collect(Collectors.toList());
        System.out.println(fullAddresses);  //debug

        try {
            DistanceMatrix matrix = DistanceMatrixApi.newRequest(context).origins(currentAddress).destinations(fullAddresses.toArray(new String[0])).await();

            int closestIndex = -1;
            long shortestDistance = Long.MAX_VALUE;

            for (int i = 0; i < matrix.rows[0].elements.length; i++)
            {
                DistanceMatrixElement element = matrix.rows[0].elements[i];
                if (element.distance != null && element.distance.inMeters < shortestDistance)
                {
                    shortestDistance = element.distance.inMeters;
                    closestIndex = i;
                }
            }

            if (closestIndex != -1) {
                return drugstores.get(closestIndex).getFullAddress() + " dystans = " + shortestDistance + " metrow";
            } else {
                return "nie udalo sie znalezc apteki";
            }

        } catch (Exception e)
        {
            throw new RuntimeException("jakis blad nwm", e);
        }
    }
}



