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
import org.yoctosample.common.YoctoTemplate;

/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:41 PM
 */
public class YoctoMeteo extends YoctoObjectImpl {

    private YoctoMeteoValue temperature;
    private YoctoMeteoValue humidity;
    private YoctoMeteoValue pressure;

    public YoctoMeteo(YoctoHub hub, String serialNumber, YoctoTemplate template, String relativePath) {
        super(hub, serialNumber, YoctoProduct.YOCTO_METEO, template, relativePath + ".json");
    }

    public YoctoMeteoValue getTemperature() {
        return temperature;
    }

    public YoctoMeteoValue getHumidity() {
        return humidity;
    }

    public YoctoMeteoValue getPressure() {
        return pressure;
    }


    @Override
    protected void refreshObject(YoctoMap result) {
        temperature = createValue(result, "temperature");
        humidity = createValue(result, "humidity");
        pressure = createValue(result, "pressure");
    }

    private YoctoMeteoValue createValue(YoctoMap result, String name) {
        YoctoMap value = result.getMap(name);
        return new YoctoMeteoValue(value);
    }
}
