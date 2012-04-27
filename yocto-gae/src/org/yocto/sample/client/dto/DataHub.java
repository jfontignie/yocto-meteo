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
 * For the demo: yocto-meteo.appspot.com
 */

package org.yocto.sample.client.dto;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: Jacques Fontignie
 * Date: 4/26/12
 * Time: 11:25 PM
 */
@PersistenceCapable
public class DataHub implements Serializable, DataObject {

    @PrimaryKey
    private String serialNumber;
    private double longitude;
    private double latitude;
    private Date date;

    public DataHub(String serialNumber, double longitude, double latitude, Date date) {
        this.date = date;
        setSerialNumber(serialNumber);
        setLongitude(longitude);
        setLatitude(latitude);
    }

    protected DataHub() {
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getSerialNumber() {
        return serialNumber;
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

    public Date getDate() {
        return date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataHub dataHub = (DataHub) o;

        if (serialNumber != null ? !serialNumber.equals(dataHub.serialNumber) : dataHub.serialNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return serialNumber != null ? serialNumber.hashCode() : 0;
    }
}
