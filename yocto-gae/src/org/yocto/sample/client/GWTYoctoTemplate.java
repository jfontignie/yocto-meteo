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

import com.google.gwt.http.client.*;
import org.yoctosample.YoctoCallback;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;

import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 7:12 PM
 */
public class GWTYoctoTemplate implements YoctoTemplate {

    private Logger logger = Logger.getLogger(this.getClass().getName());

    private String url;

    public GWTYoctoTemplate(String url) {
        this.url = url;
    }

    private native JavascriptYoctoMap asYoctoMap(String json) /*-{
        eval('var res = ' + json);
        return res;
    }-*/;


    public YoctoMap query(String relativePath) {
        throw new IllegalStateException("Not implemented yet");
    }

    public void aSyncQuery(String relativePath, final YoctoCallback<YoctoMap> listener) {

        logger.info("querying the url: " + url + relativePath);
        String newUrl = url + relativePath;

        // Send request to server and catch any errors.
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, newUrl);

        try {
            builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    logger.severe("Couldn't retrieve JSON");
                    logger.severe(exception.toString());
                    listener.onError(exception);
                }

                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        logger.info("success");
                        String result = response.getText();
                        JavascriptYoctoMap map = asYoctoMap(result);
                        listener.onSuccess(map);
                    } else {
                        listener.onError(new IllegalStateException("Couldn't retrieve JSON (" + response.getStatusCode() + ") (" + response.getStatusText()
                                + ")"));
                    }
                }
            });
        } catch (RequestException e) {
            logger.severe("Couldn't retrieve JSON");
            listener.onError(e);
        }


//
//        jsonp.requestObject(newUrl, new AsyncCallback<JavascriptYoctoMap>() {
//            public void onFailure(Throwable caught) {
//                logger.severe("Impossible to get json object: " + caught);
//            }
//
//            public void onSuccess(JavascriptYoctoMap result) {
//                logger.info("Received the result from json");
//                listener.resultEvent(result);
//            }
//
//        });
    }
}
