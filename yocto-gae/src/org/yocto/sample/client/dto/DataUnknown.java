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
 * Time: 3:40 PM
 */
@PersistenceCapable
public class DataUnknown implements Serializable, DataObject {

    @PrimaryKey
    private String serialNumber;
    private Date date;

    public void setSerialNumber(String serialNumber) {
        setSerialNumber(serialNumber);
        setDate(new Date());
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSerialNumber() {

        return serialNumber;
    }

    public Date getDate() {
        return date;
    }

    public DataUnknown(String serialNumber) {
        this.serialNumber = serialNumber;

    }

}
