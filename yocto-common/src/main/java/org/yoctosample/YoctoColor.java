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
 * Author: Jacques Fontignie
 * Date: 4/23/12
 * Time: 11:12 PM
 */
public class YoctoColor extends YoctoObjectImpl {

    //api?ctx=colorLed1&rgbColor=0x000000

    private YoctoColorValue colorLed1;
    private YoctoColorValue colorLed2;

    public YoctoColor(YoctoHub yoctoHub, String serialNumber, YoctoTemplate template, String relativePath) {
        super(yoctoHub, serialNumber, YoctoProduct.YOCTO_COLOR, template, relativePath + ".json");
    }

    @Override
    protected void refreshObject(YoctoMap map) {
        colorLed1 = new YoctoColorValue(map.getMap("colorLed1"));
        colorLed2 = new YoctoColorValue(map.getMap("colorLed2"));
    }

    public YoctoColorValue getColorLed1() {
        return colorLed1;
    }

    public YoctoColorValue getColorLed2() {
        return colorLed2;
    }

}
