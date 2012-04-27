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

package org.yocto.sample.client.ui;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.geom.Point;
import com.google.gwt.maps.client.geom.Size;
import com.google.gwt.maps.client.overlay.Icon;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import org.yocto.sample.client.functions.Hub;

/**
 * Author: Jacques Fontignie
 * Date: 4/27/12
 * Time: 11:50 AM
 * <p/>
 * Provides the different possible markers...
 * 1) location found but nothing else
 * 2) DB found
 * 3) yoctoHub
 */
public class MarkerFactory {

    private static MarkerFactory instance;
    public static final String IMG_LOCATION = "http://maps.google.com/mapfiles/marker_grey.png";
    public static final String IMG_DB = "http://maps.google.com/mapfiles/marker.png";
    public static final String IMG_YOCTO = "http://maps.google.com/mapfiles/marker_green.png";


    public static MarkerFactory getInstance() {
        if (instance == null) instance = new MarkerFactory();
        return instance;
    }

    private static MarkerOptions createOptions(String img) {
        Icon icon = Icon.newInstance(img);
        icon.setIconSize(Size.newInstance(20, 34));
        icon.setIconAnchor(Point.newInstance(10, 34));
        icon.setInfoWindowAnchor(Point.newInstance(5, 1));
        return MarkerOptions.newInstance(icon);
    }


    private Marker getMarker(final MapWidget map, Hub hub, String url, final boolean reload) {
        final Marker marker = createMarker(hub.getDto().getLongitude(),
                hub.getDto().getLatitude(), url);

        final HubWidget widget = new HubWidget(hub);
        marker.addMarkerClickHandler(new MarkerClickHandler() {
            public void onClick(MarkerClickEvent event) {
                Widget content = widget.getWidget(reload);
                PopupPanel popup = new PopupPanel(true);
                ScrollPanel panel = new ScrollPanel();
                popup.add(panel);

                panel.setWidget(content);
                popup.show();
                //map.getInfoWindow().open(marker, new InfoWindowContent());
            }
        });

        return marker;

    }

    public Marker getDBMarker(final MapWidget map, Hub hub) {
        return getMarker(map, hub, IMG_DB, true);
    }

    public Marker getEmptyMarker(double longitude, double latitude) {
        return createMarker(longitude, latitude, IMG_LOCATION);
    }

    public Marker getYoctoMarker(final MapWidget map, Hub hub) {
        return getMarker(map, hub, IMG_YOCTO, true);
    }

    private Marker createMarker(double longitude, double latitude, String img) {
        return new Marker(LatLng.newInstance(latitude, longitude), createOptions(img));
    }
}
