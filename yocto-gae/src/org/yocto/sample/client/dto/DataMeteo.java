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
 * Date: 4/25/12
 * Time: 9:25 PM
 */
//(identityType = IdentityType.APPLICATION, detachable="true")
@PersistenceCapable
public class DataMeteo implements Serializable, DataObject {


    @PrimaryKey
    private String serialNumber;

    private double temperature;

    private double pressure;

    private double humidity;

    private Date date;

    private String dataHub;

    @SuppressWarnings("unused")
    public DataMeteo() {
    }

    public DataMeteo(String serialNumber, double temperature, double pressure, double humidity,
                     String dataHub) {
        setSerialNumber(serialNumber);
        setDate(new Date());
        setTemperature(temperature);
        setPressure(pressure);
        setHumidity(humidity);
        setDataHub(dataHub);
    }

    private void setDataHub(String dataHub) {
        this.dataHub = dataHub;
    }

    public String getDataHub() {
        return dataHub;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataMeteo dataMeteo = (DataMeteo) o;
        return !(serialNumber != null ? !serialNumber.equals(dataMeteo.serialNumber) : dataMeteo.serialNumber != null);

    }

    @Override
    public int hashCode() {
        return serialNumber != null ? serialNumber.hashCode() : 0;
    }
}
