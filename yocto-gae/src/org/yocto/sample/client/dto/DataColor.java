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
 * Date: 4/27/12
 * Time: 4:40 PM
 */
//(identityType = IdentityType.APPLICATION, detachable="true")
@PersistenceCapable
public class DataColor implements Serializable, DataObject {

    @PrimaryKey
    private String serialNumber;

    private int color1;
    private int color2;

    private Date date;

    private String dataHub;

    @SuppressWarnings("unused")
    public DataColor() {
    }

    public DataColor(String serialNumber, int color1, int color2,
                     String dataHub) {
        setSerialNumber(serialNumber);
        setDate(new Date());
        setColor1(color1);
        setColor2(color2);
        setDataHub(dataHub);
    }


    public String getSerialNumber() {

        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getColor1() {
        return color1;
    }

    public void setColor1(int color1) {
        this.color1 = color1;
    }

    public int getColor2() {
        return color2;
    }

    public void setColor2(int color2) {
        this.color2 = color2;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDataHub() {
        return dataHub;
    }

    public void setDataHub(String dataHub) {
        this.dataHub = dataHub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataColor dataColor = (DataColor) o;

        if (serialNumber != null ? !serialNumber.equals(dataColor.serialNumber) : dataColor.serialNumber != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return serialNumber != null ? serialNumber.hashCode() : 0;
    }
}
