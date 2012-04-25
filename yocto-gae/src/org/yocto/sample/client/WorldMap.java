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
import com.google.gwt.user.client.ui.RootPanel;
import org.yoctosample.*;

import java.io.IOException;
import java.util.Collection;
import java.util.logging.Logger;

public class WorldMap implements EntryPoint {

    private Logger logger;

    public void onModuleLoad() {
        /*
    * Asynchronously loads the Maps API.
    *
    * The first parameter should be a valid Maps API Key to deploy this
    * application on a public server, but a blank key will work for an
    * application served from localhost.
   */
        logger = Logger.getLogger("main");
        Maps.loadMapsApi("", "2", false, new Runnable() {
            public void run() {
                buildUi();
            }
        });

        //Prepare the JSONP

        String url = "http://localhost/";
        GWTYoctoTemplate template = new GWTYoctoTemplate(url);


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


        GWTYoctoTemplate template = new GWTYoctoTemplate("http://localhost:8001");
        try {
            YoctoHub hub = new YoctoHub(template);
            logger.info("hub created");
            hub.refresh(new RefreshCallback<YoctoHub>() {
                public void onRefresh(YoctoHub hub) {
                    logger.info("refresh successful");
                    try {
                        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
                        logger.info("All the meteo objects are" + objects);
                        for (YoctoObject object : objects) {
                            final YoctoMeteo meteo = (YoctoMeteo) object;
                            meteo.refresh(new RefreshCallback() {
                                public void onRefresh(YoctoObject yoctoObject) {
                                    logger.info("The current temperature is: " + meteo.getTemperature().getAdvertisedValue());
                                }

                                public void onError(YoctoObject yoctoObject, IOException e) {
                                    //To change body of implemented methods use File | Settings | File Templates.
                                }
                            });

                        }
                    } catch (IOException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }

                }

                public void onError(YoctoHub hub, IOException e) {
                    //To change body of implemented methods use File | Settings | File Templates.
                }
            });
            logger.info("hub refresh performed");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//
//        try {
//            logger.info("Query api.json");
//            template.aSyncQuery("api.json", new QueryListener() {
//                public void resultEvent(YoctoMap map) {
//                    logger.info("result received");
//                }
//
//                public void exceptionEvent(IOException e) {
//                    logger.info("exception received");
//                }
//            });
//        } catch (IOException e) {
//            logger.severe(e.toString());
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

        //YoctoHub hub = new YoctoHub(new GWTYoctoTemplate("http://127.0.0.1"));

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
