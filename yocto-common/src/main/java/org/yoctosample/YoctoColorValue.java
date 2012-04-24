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

import org.yoctosample.common.YoctoMap;

/**
 * Author: Jacques Fontignie
 * Date: 4/23/12
 * Time: 11:17 PM
 */
public class YoctoColorValue {

    private YoctoMap service;
    private ColorMove rgbMove;
    private ColorMove hslMove;

    public YoctoColorValue(YoctoMap service) {
        this.service = service;
        this.rgbMove = new ColorMove(service.getMap("rgbMove"));
        this.hslMove = new ColorMove(service.getMap("hslMove"));
    }

    public String getLogicalName() {
        return service.getValue("logicalName").toString();
    }

    public String getAdvertisedValue() {
        return service.getValue("advertisedValue").toString();
    }

    public int getRgbColor() {
        return Integer.valueOf(service.getValue("rgbColor").toString());
    }

    public int getHslColor() {
        return Integer.valueOf(service.getValue("hslColor").toString());
    }

    public ColorMove getRgbMove() {
        return rgbMove;
    }

    public ColorMove getHslMove() {
        return hslMove;
    }

    public boolean getRgbColorAtPowerOn() {
        return Boolean.valueOf(service.getValue("rgbColorAtPowerOn").toString());
    }

    public static class ColorMove {
        private YoctoMap service;

        public ColorMove(YoctoMap service) {
            this.service = service;
        }

        public boolean isMoving() {
            return Boolean.valueOf(service.getValue("moving").toString());
        }

        public int getTarget() {
            return Integer.valueOf(service.getValue("target").toString());
        }

        public int getMs() {
            return Integer.valueOf(service.getValue("ms").toString());
        }
    }


}
