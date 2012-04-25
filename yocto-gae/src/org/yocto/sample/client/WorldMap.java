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


import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import org.yocto.sample.client.common.DataMeteo;
import org.yoctosample.*;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public class WorldMap implements EntryPoint {

    private Logger logger;
    private YoctoHub hub;

    private final WorldMapServiceAsync worldMapService = GWT.create(WorldMapService.class);

    public void onModuleLoad() {
        /*
    * Asynchronously loads the Maps API.
    *
    * The first parameter should be a valid Maps API Key to deploy this
    * application on a public server, but a blank key will work for an
    * application served from localhost.
   */
        logger = Logger.getLogger("main");

        //Prepare the JSONP
        GWTYoctoTemplate template = new GWTYoctoTemplate("http://localhost:8001");

        hub = new YoctoHub(template);

        Maps.loadMapsApi("", "2", false, new Runnable() {
            public void run() {
                buildUi();
            }
        });


    }

    private void buildUi() {

        logger.info("Build UI");
        Geolocation location = Geolocation.getIfSupported();

        // Open a map centered on Cawker City, KS USA
        LatLng cartigny = LatLng.newInstance(46.1833, 6.0167);

        final MapWidget map = new MapWidget(cartigny, 2);
        map.setSize("100%", "100%");
        // Add some controls for the zoom level
        map.addControl(new LargeMapControl());


        final DockLayoutPanel dock = new DockLayoutPanel(Style.Unit.PX);

        dock.addNorth(map, 500);


        RootPanel.get("worldMap").add(dock);
        // Add the map to the HTML host page

        location.getCurrentPosition(new Callback<Position, PositionError>() {
            public void onFailure(PositionError reason) {

            }

            public void onSuccess(Position result) {
                Position.Coordinates coordinates = result.getCoordinates();
                final LatLng lng = LatLng.newInstance(coordinates.getLatitude(), coordinates.getLongitude());
                try {
                    Marker marker = createMarker(map, lng);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }


            }
        });

    }

    private Marker createMarker(final MapWidget map, LatLng lng) throws IOException {
        final Marker marker = new Marker(lng);
        map.setCenter(lng);
        map.addOverlay(marker);

        createYoctoHub(map, marker);
        return marker;
    }

    private void createYoctoHub(final MapWidget map, final Marker marker) throws IOException {

        logger.fine("hub created");
        hub.refresh(new RefreshCallback<YoctoHub>() {
            public void onRefresh(YoctoHub hub) throws IOException {
                logger.fine("refresh successful");

                Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
                logger.fine("All the meteo objects are" + objects);
                for (YoctoObject object : objects) {
                    final YoctoMeteo meteo = (YoctoMeteo) object;
                    meteo.refresh(new RefreshCallback() {
                        public void onRefresh(YoctoObject yoctoObject) {
                            marker.addMarkerClickHandler(new MarkerClickHandler() {

                                public void onClick(MarkerClickEvent event) {
                                    Widget html = createWidget(marker, meteo);

                                    map.getInfoWindow().open(marker,

                                            new InfoWindowContent(html));
                                }
                            });
                            logger.info("The current temperature is: " + meteo.getTemperature().getAdvertisedValue());
                            logger.info("The current humidity is: " + meteo.getHumidity().getAdvertisedValue());
                            logger.info("The current pressure is: " + meteo.getPressure().getAdvertisedValue());
                        }

                        public void onError(YoctoObject yoctoObject, IOException e) {
                            //To change body of implemented methods use File | Settings | File Templates.
                        }
                    });

                }


            }

            public void onError(YoctoHub yoctoObject, IOException e) {

            }
        });
        logger.info("hub refresh performed");

    }

    private Widget createWidget(Marker marker, YoctoMeteo meteo) {
        TreeItem root = new TreeItem();
        root.setText("Yocto-meteo: " + meteo.getSerialNumber());
        root.addTextItem("Temperature: " + meteo.getTemperature().getAdvertisedValue() + " °C");
        root.addTextItem("Humidity: " + meteo.getHumidity().getAdvertisedValue() + "%");
        root.addTextItem("Pressure: " + meteo.getPressure().getAdvertisedValue() + " hPA");

        worldMapService.addMeteo(new DataMeteo(marker, meteo), new AsyncCallback<Void>() {
            public void onFailure(Throwable caught) {
            }

            public void onSuccess(Void result) {
            }
        });
        root.setState(true);
        Tree t = new Tree();
        t.addItem(root);
        return t;

    }


}
