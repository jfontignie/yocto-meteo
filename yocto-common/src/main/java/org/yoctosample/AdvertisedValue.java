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

package org.yoctosample;

import org.yoctosample.common.YoctoMap;

/**
 * Author: Jacques Fontignie
 * Date: 4/29/12
 * Time: 8:40 PM
 */
public class AdvertisedValue {
    private YoctoMap service;
    private String type;

    public AdvertisedValue(YoctoMap service) {
        this.service = service;
    }

    public AdvertisedValue(String name, YoctoMap service) {
        this.type = name;
        this.service = service;
    }

    public String getHardwareId() {
        return service.getString("hardwareId");
    }

    public String getLogicalName() {
        return service.getString("logicalName");
    }

    public String getAdvertisedValue() {
        return service.getString("advertisedValue");
    }

    public int getIndex() {
        return service.getInt("index");
    }

    public String getType() {
        return type;
    }

}
