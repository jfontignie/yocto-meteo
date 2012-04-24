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

package org.yocto.sample.client;


import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class WorldMap implements EntryPoint {


    public void onModuleLoad() {
        /*
    * Asynchronously loads the Maps API.
    *
    * The first parameter should be a valid Maps API Key to deploy this
    * application on a public server, but a blank key will work for an
    * application served from localhost.
   */

        Maps.loadMapsApi("", "2", false, new Runnable() {
            public void run() {
                buildUi();
            }
        });

        //Prepare the JSONP

        String url = "http://127.0.0.1:4444/";
        GWTYoctoTemplate template = new GWTYoctoTemplate(url);


    }

    private void buildUi() {
        Geolocation location = Geolocation.getIfSupported();


        // Open a map centered on Cawker City, KS USA
        LatLng cartigny = LatLng.newInstance(46.1833, 6.0167);

        final MapWidget map = new MapWidget(cartigny, 2);
        map.setSize("100%", "100%");
        // Add some controls for the zoom level
        map.addControl(new LargeMapControl());


        final DockLayoutPanel dock = new DockLayoutPanel(Style.Unit.PX);

        dock.addNorth(map, 500);

        Label label = new Label("Hello world");
        RootPanel.get("comment").add(label);

        RootPanel.get("worldMap").add(dock);
        // Add the map to the HTML host page

        location.getCurrentPosition(new Callback<Position, PositionError>() {
            public void onFailure(PositionError reason) {

            }

            public void onSuccess(Position result) {
                Position.Coordinates coordinates = result.getCoordinates();
                final LatLng lng = LatLng.newInstance(coordinates.getLatitude(), coordinates.getLongitude());
                final Marker marker = new Marker(lng);

                marker.addMarkerClickHandler(new MarkerClickHandler() {
                    public void onClick(MarkerClickEvent event) {
                        map.getInfoWindow().open(marker, new InfoWindowContent("You are here!"));
                    }
                });

                map.setCenter(lng);
                map.addOverlay(marker);

            }
        });

    }


}
