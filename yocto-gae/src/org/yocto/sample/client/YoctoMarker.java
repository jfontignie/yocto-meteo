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

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import org.yocto.sample.client.common.DataMeteo;

import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/26/12
 * Time: 1:25 PM
 */
public class YoctoMarker extends Marker {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Widget widget;

    public YoctoMarker(final MapWidget map, DataMeteo meteo) {
        super(LatLng.newInstance(meteo.getLatitude(), meteo.getLongitude()));
        init(map, meteo);
    }

    public YoctoMarker(final MapWidget map, DataMeteo meteo, MarkerOptions options) {
        super(LatLng.newInstance(meteo.getLatitude(), meteo.getLongitude()), options);
        init(map, meteo);
    }

    private void init(final MapWidget map, DataMeteo meteo) {
        logger.finer("initializing click listener");
        widget = createWidget(meteo);
        addMarkerClickHandler(new MarkerClickHandler() {
            public void onClick(MarkerClickEvent event) {
                map.getInfoWindow().open(YoctoMarker.this, new InfoWindowContent(widget));
            }
        });
        map.addOverlay(this);
    }

    private Widget createWidget(DataMeteo meteo) {
        logger.finer("initializing widget");
        TreeItem root = new TreeItem();
        root.setText("Yocto-meteo: " + meteo.getSerialNumber());
        root.addItem(new HTML("<small><i>last updated:" +
                DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(meteo.getDate()) + "</i></small>"));
        root.addTextItem("Temperature: " + meteo.getTemperature() + " °C");
        root.addTextItem("Humidity: " + meteo.getHumidity() + "%");
        root.addTextItem("Pressure: " + meteo.getPressure() + " hPA");

        root.setState(true);
        Tree t = new Tree();
        t.addItem(root);
        return t;

    }
}
