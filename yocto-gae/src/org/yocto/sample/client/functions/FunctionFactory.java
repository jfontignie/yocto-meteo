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

package org.yocto.sample.client.functions;

import org.yoctosample.YoctoColor;
import org.yoctosample.YoctoHub;
import org.yoctosample.YoctoMeteo;
import org.yoctosample.YoctoObject;

/**
 * Author: Jacques Fontignie
 * Date: 4/27/12
 * Time: 2:40 PM
 */
public class FunctionFactory {
    public static AbstractFunction create(YoctoObject yObject) {
        if (yObject instanceof YoctoMeteo)
            return new Meteo((YoctoMeteo) yObject);
        if (yObject instanceof YoctoHub)
            return new Hub((YoctoHub) yObject);
        if (yObject instanceof YoctoColor)
            return new Color((YoctoColor) yObject);
        return new Unknwon(yObject);
    }
}
