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
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import org.yocto.sample.client.common.JavascriptYoctoMap;
import org.yoctosample.QueryListener;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 7:12 PM
 */
public class GWTYoctoTemplate implements YoctoTemplate {

    Logger logger = Logger.getLogger("yoctoTemplate");

    private String url;
    private JsonpRequestBuilder jsonp;

    public GWTYoctoTemplate(String url) {
        this.url = url;
        jsonp = new JsonpRequestBuilder();
    }

    private final native JavascriptYoctoMap asYoctoMap(String json) /*-{
        eval('var res = ' + json);
        return res;
    }-*/;


    public YoctoMap query(String relativePath) throws IOException {
        aSyncQuery(relativePath, new QueryListener() {
            public void resultEvent(YoctoMap map) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void exceptionEvent(IOException e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });

        throw new IllegalStateException("Not implemented yet");
    }

    public void aSyncQuery(String relativePath, final QueryListener listener) throws IOException {

        logger.info("querying the url: " + url + relativePath);
        String newUrl = url + relativePath;

        // Send request to server and catch any errors.
        RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, newUrl);

        try {
            Request request = builder.sendRequest(null, new RequestCallback() {
                public void onError(Request request, Throwable exception) {
                    logger.severe("Couldn't retrieve JSON");
                    logger.severe(exception.toString());
                }

                public void onResponseReceived(Request request, Response response) {
                    if (200 == response.getStatusCode()) {
                        logger.info("success");
                        String result = response.getText();
                        JavascriptYoctoMap map = asYoctoMap(result);
                        listener.resultEvent(map);
                    } else {
                        logger.severe("Couldn't retrieve JSON (" + response.getStatusCode() + ") (" + response.getStatusText()
                                + ")");
                    }
                }
            });
        } catch (RequestException e) {
            logger.severe("Couldn't retrieve JSON");
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
