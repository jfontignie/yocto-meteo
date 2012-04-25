/*
 * Copyright 2012 Jacques Fontignie
 *
 * This file is part of yocto-meteo.
 *
 * yocto-meteo is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * yocto-meteo is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with yocto-meteo. If not, see http://www.gnu.org/licenses/.
 *
 * For more information: go on http://yocto-meteo.blogspot.com
 */

package org.yocto.sample.client.common;

import com.google.gwt.maps.client.overlay.Marker;
import org.yoctosample.YoctoMeteo;

import java.io.Serializable;
import java.util.Date;

/**
 * Author: Jacques Fontignie
 * Date: 4/25/12
 * Time: 9:25 PM
 */
public class DataMeteo implements Serializable {

    private String serialNumber;

    private double longitude;

    private double latitude;

    private double temperature;

    private double pressure;

    private double humidity;

    private Date date;

    public DataMeteo(Marker marker, YoctoMeteo meteo) {
        setDate(new Date());
        setLongitude(marker.getLatLng().getLongitude());
        setLatitude(marker.getLatLng().getLatitude());
        setTemperature(meteo.getTemperature().getAdvertisedValue());
        setPressure(meteo.getPressure().getAdvertisedValue());
        setHumidity(meteo.getHumidity().getAdvertisedValue());
    }

    public DataMeteo() {
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
