package edu.gatech.cs1332.ratattack.model;

import android.location.Address;
import android.location.Geocoder;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Created by Brent on 10/10/2017.
 */

public class Rat implements Parcelable {
    private String uniquekey;
    private String create_date;
    private String location_type;
    private String incident_zip;
    private String incident_address;
    private String city;
    private String borough;
    private String Latitude;
    private String Longitude;


    /**
     * Creates a new Rat Object with passed in information.
     *
     * @param uk  Unique Key
     * @param cd  Created Date
     * @param lt  Location Type
     * @param iz  Incident Zip
     * @param ia  Incident Address
     * @param cy  City
     * @param bh  Borough
     * @param lat Latitude
     * @param lon Longitude
     */
    public Rat(String uk, String cd, String lt, String iz, String ia, String cy, String bh, String lat, String lon) {
        uniquekey = uk;
        create_date = cd;
        location_type = lt;
        incident_zip = iz;
        incident_address = ia;
        city = cy;
        borough = bh;
        Latitude = lat;
        Longitude = lon;
    }

    public String getUniquekey() {
        return uniquekey;
    }

    /**
     * @param uniquekey unique key of the column
     */
    public void setUniquekey(String uniquekey) {
        this.uniquekey = uniquekey;
    }

    /**
     * @return create_date the date the data was created
     */
    public String getCreate_date() {
        return create_date;
    }

    /**
     * @param create_date create date of the rat sighting
     */
    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    /**
     * @return location_type
     */
    public String getLocation_type() {
        return location_type;
    }

    /**
     * @param location_type the location of the sighting
     */
    public void setLocation_type(String location_type) {
        this.location_type = location_type;
    }

    /**
     * @return incident_zip the zip of the sighting
     */
    public String getIncident_zip() {
        return incident_zip;
    }

    /**
     * @param incident_zip zip of sighting
     */
    public void setIncident_zip(String incident_zip) {
        this.incident_zip = incident_zip;
    }

    /**
     * @return incident_address the address of the sighting
     */
    public String getIncident_address() {
        return incident_address;
    }

    /**
     * @param incident_address the address of the sighting
     */
    public void setIncident_address(String incident_address) {
        this.incident_address = incident_address;
    }

    /**
     * @return city the city of the sighting
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city of the sighting
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return borough the borough of the sighting
     */
    public String getBorough() {
        return borough;
    }

    /**
     * @param borough the borough of the sighting
     */
    public void setBorough(String borough) {
        this.borough = borough;
    }

    /**
     * @return Latitude the borough of the sighting
     */
    public String getLatitude() {
        return Latitude;
    }

    /**
     * @param Latitude the Latitude of the sighting
     */
    public void setLatitude(String Latitude) {
        this.Latitude = Latitude;
    }

    /**
     * @return Longitude the Longitude of the sighting
     */
    public String getLongitude() {
        return Longitude;
    }

    /**
     * @param Longitude the Longitude of the sighting
     */
    public void setLongitude(String Longitude) {
        this.Longitude = Longitude;
    }

//    @Override
//    public String toString() {
//        return "Rat: " + uniquekey + " is created on " + create_date + " with address: " +
//                incident_address + " zip: " + incident_zip + " city: " + city;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(uniquekey);
        parcel.writeString(create_date);
        parcel.writeString(location_type);
        parcel.writeString(incident_zip);
        parcel.writeString(incident_address);
        parcel.writeString(city);
        parcel.writeString(borough);
        parcel.writeString(Latitude);
        parcel.writeString(Longitude);
    }

    public Rat(Parcel in) {
        uniquekey = in.readString();
        create_date = in.readString();
        location_type = in.readString();
        incident_zip = in.readString();
        incident_address = in.readString();
        city = in.readString();
        borough = in.readString();
        Latitude = in.readString();
        Longitude = in.readString();
    }

    public static final Parcelable.Creator<Rat> CREATOR = new Parcelable.Creator<Rat>() {


        @Override
        public Rat createFromParcel(Parcel in) {
            return new Rat(in);
        }

        @Override
        public Rat[] newArray(int i) {
            return new Rat[i];
        }
    };

    //Returns True if the rat has Latitude/Longitude
    public boolean validLoc() {
        try {
            Double.parseDouble(Latitude);
            Double.parseDouble(Longitude);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    //Creates and returns the marker
    public MarkerOptions getMarker(Geocoder gc) {
        MarkerOptions pointer;
        double lat = 0;
        double lon = 0;
        if (validLoc()) {
            lat = Double.parseDouble(Latitude);
            lon = Double.parseDouble(Longitude);
        } else {
            //geocoder stuff
            List<Address> add = null;
            try {
                add = gc.getFromLocationName(location_type
                        + incident_zip + incident_address + city + borough, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (add.size() < 1) {
                return null;
            }
            lat = add.get(0).getLatitude();
            lon = add.get(0).getLongitude();
        }

        pointer = new MarkerOptions()
                .position(new LatLng(lat, lon)).title(uniquekey)
                .title("rat" + uniquekey).snippet(incident_address + borough + city + incident_zip);
        return pointer;
    }
}