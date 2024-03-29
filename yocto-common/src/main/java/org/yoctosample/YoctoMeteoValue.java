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

package org.yoctosample;

import org.yoctosample.common.YoctoMap;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 12:32 PM
 */
public class YoctoMeteoValue {

    private YoctoMap service;

    public YoctoMeteoValue(YoctoMap service) {
        this.service = service;
    }

    public String getLogicalName() {
        return service.getString("logicalName");
    }

    public float getAdvertisedValue() {
        return Float.valueOf(service.getString("advertisedValue"));
    }

    public float getCurrentValue() {
        return Float.valueOf(service.getString("currentValue"));
    }

    public float getLowestValue() {
        return Float.valueOf(service.getString("lowestValue"));
    }

    public float getHighestValue() {
        return Float.valueOf(service.getString("highestValue"));
    }

    public String toString() {
        return "name: '" + getLogicalName() + "' advertised:" + getAdvertisedValue();
    }
}
