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

package org.yocto.sample.client;


import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import org.yocto.sample.client.dto.DataColor;
import org.yocto.sample.client.dto.DataHub;
import org.yocto.sample.client.dto.DataMeteo;
import org.yocto.sample.client.functions.*;
import org.yocto.sample.client.ui.MarkerFactory;
import org.yoctosample.YoctoCallback;
import org.yoctosample.YoctoHub;
import org.yoctosample.YoctoObject;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * Display a worldmap with all the yoctohub found and all their objects.
 * In order to go faster some calls are made at the same time
 * <p/>
 * 1) get the location
 * 2) get the currentHub
 * 3) get the DB
 */
public class WorldMap implements EntryPoint {

    private static final Logger logger = Logger.getLogger(WorldMap.class.getName());

    private final WorldMapServiceAsync worldMapService = GWT.create(WorldMapService.class);

    private MapWidget map;

    private Hub currentHub = null;
    private HashMap<String, Hub> hubs;
    private static LatLng latLng = null;
    private Marker currentMarker = null;


    public void onModuleLoad() {

        hubs = new HashMap<String, Hub>();

        //Loading page is taken from:


        //Let's initialize the currentHub and template.
        //The values taken are the default values
        GWTYoctoTemplate template = new GWTYoctoTemplate("http://localhost:4444");
        currentHub = new Hub(new YoctoHub(template));

        //Load the Map
        Maps.loadMapsApi("", "2", false, new Runnable() {
            public void run() {
                buildUi();
            }
        });


    }

    private void createCallbacks() {
        SequentialCallback callback = new SequentialCallback();


        //noinspection unchecked
        getLatLng(callback.createCallback(
                new AsyncCallback<LatLng>() {
                    public void onFailure(Throwable caught) {
                        logger.warning("Imposible to retrieve location: " + caught);
                    }

                    public void onSuccess(LatLng result) {
                        logger.info("Location has been found at: " + result);
                        currentMarker = MarkerFactory.getInstance().getEmptyMarker(result.getLongitude(), result.getLatitude());
                        map.addOverlay(currentMarker);
                    }
                }));


        //noinspection unchecked
        currentHub.refresh(callback.createCallback(new YoctoCallback<Hub>() {
            public void onSuccess(Hub hub) {
                logger.info("Hub with serial number: " + hub.getYocto().getSerialNumber() + " has been found");
                if (latLng != null) {

                    hub.getDto().setLatitude(latLng.getLatitude());
                    hub.getDto().setLongitude(latLng.getLongitude());
                    map.removeOverlay(currentMarker);
                    currentMarker = MarkerFactory.getInstance().getYoctoMarker(map, hub);
                    map.addOverlay(currentMarker);


                    //Now let's save the hub in DB
                    worldMapService.addHub(hub.getDto(), new AsyncCallback<Void>() {
                        public void onFailure(Throwable caught) {
                            logger.severe("Impossible to save hub: " + caught);
                        }

                        public void onSuccess(Void result) {
                            logger.fine("Hub successfully saved");
                        }
                    });

                    refreshHub(hub);
                }
            }

            public void onError(Throwable t) {
                logger.warning("Imposible to retrieve current Hub: " + t);
            }
        }));

        //noinspection unchecked
        worldMapService.listHubs(callback.createCallback(new AsyncCallback<List<DataHub>>() {
            public void onFailure(Throwable caught) {
                logger.warning("Impossible to retrieve the hubs from the database: " + caught);
                setLoaded();
            }

            public void onSuccess(List<DataHub> result) {
                logger.info("hubs in DB found: " + result.size());
                for (DataHub hub : result) {
                    Hub newHub = new Hub(hub);
                    if (!newHub.matches(currentHub) || latLng == null) {
                        map.addOverlay(MarkerFactory.getInstance().getDBMarker(map, newHub));
                        hubs.put(newHub.getDto().getSerialNumber(), newHub);
                    }

                }
                fillHubs();
                setLoaded();
            }
        }));
    }

    private void fillHubs() {
        worldMapService.listMeteos(new AsyncCallback<List<DataMeteo>>() {
            public void onFailure(Throwable caught) {
                logger.severe("Impossible to retrieve the meteos: " + caught);
            }

            public void onSuccess(List<DataMeteo> result) {
                logger.info("meteos found: " + result.size());
                for (DataMeteo meteo : result) {
                    Hub current = hubs.get(meteo.getDataHub());
                    if (current != null)
                        current.add(new Meteo(meteo));
                }
            }
        });

        worldMapService.listColors(new AsyncCallback<List<DataColor>>() {
            public void onFailure(Throwable caught) {
                logger.severe("Impossible to retrieve the meteos: " + caught);
            }

            public void onSuccess(List<DataColor> result) {
                logger.info("colors found: " + result.size());
                for (DataColor color : result) {
                    Hub current = hubs.get(color.getDataHub());
                    if (current != null)
                        current.add(new Color(color));
                }
            }
        });
    }

    private void refreshHub(Hub hub) {
        YoctoHub yHub = hub.getYocto();
        for (YoctoObject yObject : yHub.findAll()) {
            if (!hub.getYocto().equals(yObject)) {
                AbstractFunction function = FunctionFactory.create(yObject);
                function.refresh(new YoctoCallback() {
                    public void onSuccess(Object result) {
                        logger.info("Successfully read!");
                    }

                    public void onError(Throwable t) {
                        logger.severe("Impossible to read device: " + t);
                    }
                });
                hub.add(function);
            }
        }
    }

    private void buildUi() {

        logger.info("Build UI");

        //Note: that in order to get locations and markers: the MAP api must be loaded first.
        createCallbacks();


        LatLng cartigny = LatLng.newInstance(46.1833, 6.0167);

        map = new MapWidget(cartigny, 2);
        map.setSize("100%", "100%");
        // Add some controls for the zoom level
        map.addControl(new LargeMapControl());

        final DockLayoutPanel dock = new DockLayoutPanel(Style.Unit.PX);

        dock.addNorth(map, 500);

        RootPanel.get("worldMap").add(dock);
        // Add the map to the HTML host page

    }

    private void setLoaded() {
        DOM.removeChild(RootPanel.getBodyElement(), DOM.getElementById("loading"));
    }

    public synchronized static void getLatLng(final AsyncCallback<LatLng> callback) {

        if (latLng == null) {
            Geolocation location = Geolocation.getIfSupported();
            location.getCurrentPosition(new Callback<Position, PositionError>() {
                public void onFailure(PositionError reason) {
                    callback.onFailure(reason);
                }

                public void onSuccess(Position result) {
                    logger.info("Location found at: " + result);
                    latLng = LatLng.newInstance(result.getCoordinates().getLatitude(),
                            result.getCoordinates().getLongitude());
                    callback.onSuccess(latLng);
                }
            });
        } else {
            callback.onSuccess(latLng);
        }
    }
}
