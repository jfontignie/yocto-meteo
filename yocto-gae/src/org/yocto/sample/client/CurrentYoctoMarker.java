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

package org.yocto.sample.client;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import org.yocto.sample.client.common.DataMeteo;

import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/26/12
 * Time: 2:43 PM
 */
public class CurrentYoctoMarker extends YoctoMarker {

    private static final Logger logger = Logger.getLogger(CurrentYoctoMarker.class.getName());

    private static MarkerOptions createOptions() {
        Icon icon = Icon.newInstance("http://maps.google.com/mapfiles/marker_purple.png");
        //Default size is: 20:34
//        icon.setIconSize(Size.newInstance(12, 20));
//        icon.setShadowSize(Size.newInstance(22, 20));
//        icon.setIconAnchor(Point.newInstance(6, 20));
//        icon.setInfoWindowAnchor(Point.newInstance(5, 1));

        icon.setIconSize(Size.newInstance(20, 34));
        icon.setShadowSize(Size.newInstance(32, 20));
        //icon.setIconAnchor(Point.newInstance(10, 34));
        icon.setInfoWindowAnchor(Point.newInstance(5, 1));

        MarkerOptions options = MarkerOptions.newInstance(icon);

        return options;
    }

    public CurrentYoctoMarker(final MapWidget map, DataMeteo meteo) {
        super(map, meteo, createOptions());
    }
}