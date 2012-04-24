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

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 7:18 PM
 */
public class JSONRequest {
    public static void get(String url, JSONRequestHandler handler) {
        String callbackName = "JSONCallback" + handler.hashCode();
        get(url + callbackName, callbackName, handler);
    }

    public static void get(String url, String callbackName, JSONRequestHandler handler) {
        createCallbackFunction(handler, callbackName);
        addScript(url);
    }

    public static native void addScript(String url) /*-{
        var scr = document.createElement("script");
        scr.setAttribute("language", "JavaScript");
        scr.setAttribute("src", url);
        document.getElementsByTagName("body")[0].appendChild(scr);
    }-*/;

    private native static void createCallbackFunction(JSONRequestHandler obj, String callbackName)/*-{
        tmpcallback = function (j) {
            obj.@org.yocto.sample.client.JSONRequestHandler::onRequestComplete(Lcom/google/gwt/core/client/JavaScriptObject;)(j);
        };
        eval("window." + callbackName + "=tmpcallback");
    }-*/;
}