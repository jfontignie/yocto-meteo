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

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.yocto.sample.client.dto.DataColor;
import org.yoctosample.YoctoColor;

/**
 * Author: Jacques Fontignie
 * Date: 4/27/12
 * Time: 4:38 PM
 */
public class Color extends AbstractFunction<Color, DataColor, YoctoColor> {


    public Color(YoctoColor color) {
        super(null, color);
    }

    public Color(DataColor color) {
        super(color, null);
    }

    @Override
    protected DataColor createDTO(final YoctoColor result) {
        DataColor color = new DataColor(result.getSerialNumber(),
                result.getColorLed1().getRgbColor(),
                result.getColorLed2().getRgbColor(),
                result.getHub().getSerialNumber());

        worldMapService.addColor(color, new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
                logger.severe("Impossible to save color: " + caught);
            }

            public void onSuccess(Void result) {
                logger.info("Successfully saved color");
            }
        });

        return color;
    }
}
