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

import com.google.gwt.i18n.shared.DateTimeFormat;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TreeItem;
import org.yocto.sample.client.dto.*;
import org.yocto.sample.client.functions.AbstractFunction;
import org.yocto.sample.client.functions.Color;
import org.yocto.sample.client.functions.Hub;
import org.yocto.sample.client.functions.Meteo;
import org.yoctosample.YoctoCallback;

import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/27/12
 * Time: 2:28 PM
 */
public class WidgetFactory {

    private static final Logger logger = Logger.getLogger(WidgetFactory.class.getName());

    public static TreeItem createWidget(AbstractFunction function) {
        if (function instanceof Hub)
            return createHubWidget((Hub) function);
        if (function instanceof Meteo)
            return createMeteoWidget((Meteo) function);
        if (function instanceof Color)
            return createColorWidget((Color) function);
        return createUnknownWidget(function);
    }

    private static TreeItem createColorWidget(Color function) {
        return createLoadableWidget(function, new CreationListener<DataColor>() {
            public void createWidget(TreeItem root, DataColor color) {
                root.setText("Yocto-color: " + color.getSerialNumber());
                root.addItem(new HTML("<small><i>last updated:" +
                        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(color.getDate()) + "</i></small>"));
                root.addItem(new HTML("<font color=\"#" + Integer.toHexString(color.getColor2()) + "\">&#9632;</span>"));
                root.addItem(new HTML("<font color=\"#" + Integer.toHexString(color.getColor1()) + "\">&#9632;</span>"));
            }
        });
    }

    private static TreeItem createMeteoWidget(Meteo function) {
        return createLoadableWidget(function, new CreationListener<DataMeteo>() {
            public void createWidget(TreeItem root, DataMeteo meteo) {
                root.setText("Yocto-meteo: " + meteo.getSerialNumber());
                root.addItem(new HTML("<small><i>last updated:" +
                        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(meteo.getDate()) + "</i></small>"));
                root.addTextItem("Temperature: " + meteo.getTemperature() + " °C");
                root.addTextItem("Humidity: " + meteo.getHumidity() + "%");
                root.addTextItem("Pressure: " + meteo.getPressure() + " hPA");

            }
        });
    }

    private static TreeItem createUnknownWidget(AbstractFunction function) {
        return createLoadableWidget(function, new CreationListener<DataUnknown>() {
            public void createWidget(TreeItem root, DataUnknown unknown) {
                root.setText("Unknown: " + unknown.getSerialNumber());
                root.addItem(new HTML("<small><i>The device is not supported yet</i></small>"));

            }
        });
    }

    private static TreeItem createHubWidget(final Hub hub) {
        return createLoadableWidget(hub, new CreationListener<DataHub>() {
            public void createWidget(TreeItem root, DataHub dataHub) {
                root.setText("hub:" + dataHub.getSerialNumber());
                root.addItem(new HTML("<small><i>last updated:" +
                        DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.DATE_TIME_MEDIUM).format(dataHub.getDate()) + "</i></small>"));
                for (AbstractFunction child : hub.getChildren()) {
                    root.addItem(WidgetFactory.createWidget(child));
                }

            }
        });


    }

    private static TreeItem createLoadableWidget(AbstractFunction function, final CreationListener listener) {
        DataObject dto = function.getDto();
        final TreeItem root = new TreeItem();
        if (function.getYocto() != null) {
            if (function.getDto() != null) {
                listener.createWidget(root, dto);
            } else {
                root.setText("Loading...");
            }
            function.refresh(new YoctoCallback<AbstractFunction>() {
                public void onSuccess(AbstractFunction result) {
                    root.removeItems();
                    listener.createWidget(root, result.getDto());
                }

                public void onError(Throwable t) {
                    logger.severe("Impossible to retrieve object: " + t);
                }
            });

        } else {
            listener.createWidget(root, function.getDto());
        }
        root.setState(true);
        return root;
    }


    private interface CreationListener<T> {
        public void createWidget(TreeItem root, T object);
    }

}
