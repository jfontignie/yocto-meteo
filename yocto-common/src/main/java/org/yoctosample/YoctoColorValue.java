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
 */

package org.yoctosample;

import java.util.Map;

/**
 * Author: Jacques Fontignie
 * Date: 4/23/12
 * Time: 11:17 PM
 */
public class YoctoColorValue {

    private Map<String, Object> service;
    private ColorMove rgbMove;
    private ColorMove hslMove;

    public YoctoColorValue(Map<String, Object> service) {
        this.service = service;
        this.rgbMove = new ColorMove((Map<String, Object>) service.get("rgbMove"));
        this.hslMove = new ColorMove((Map<String, Object>) service.get("hslMove"));
    }

    public String getLogicalName() {
        return service.get("logicalName").toString();
    }

    public String getAdvertisedValue() {
        return service.get("advertisedValue").toString();
    }

    public int getRgbColor() {
        return Integer.valueOf(service.get("rgbColor").toString());
    }

    public int getHslColor() {
        return Integer.valueOf(service.get("hslColor").toString());
    }

    public ColorMove getRgbMove() {
        return rgbMove;
    }

    public ColorMove getHslMove() {
        return hslMove;
    }

    public boolean getRgbColorAtPowerOn() {
        return Boolean.valueOf(service.get("rgbColorAtPowerOn").toString());
    }

    public class ColorMove {
        private Map<String, Object> service;

        public ColorMove(Map<String, Object> service) {
            this.service = service;
        }

        public boolean isMoving() {
            return Boolean.valueOf(service.get("moving").toString());
        }

        public int getTarget() {
            return Integer.valueOf(service.get("target").toString());
        }

        public int getMs() {
            return Integer.valueOf(service.get("ms").toString());
        }
    }


}
