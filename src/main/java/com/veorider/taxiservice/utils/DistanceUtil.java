package com.veorider.taxiservice.utils;

public class DistanceUtil {

  /** calculates the distance between two locations in MILES */
  public static float calcDistance(float lat1, float lng1, float lat2, float lng2) {

    double earthRadius = 3958.75;

    double dLat = Math.toRadians(lat2-lat1);
    double dLng = Math.toRadians(lng2-lng1);

    double sindLat = Math.sin(dLat / 2);
    double sindLng = Math.sin(dLng / 2);

    double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
        * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));

    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

    double dist = earthRadius * c;

    return (float) dist;
  }
}
