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

import org.yocto.sample.client.common.JavascriptYoctoMap;
import org.yoctosample.QueryListener;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;

import java.io.IOException;

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 7:12 PM
 */
public class GWTYoctoTemplate implements YoctoTemplate {

    private String url;

    public GWTYoctoTemplate(String url) {
        this.url = url;
    }

    public YoctoMap query(String relativePath) throws IOException {
        JSONRequest.get(url, new JSONRequestHandler<JavascriptYoctoMap>() {
            public void onRequestComplete(JavascriptYoctoMap object) {

            }
        });
        throw new IllegalStateException("Not implemented yet");
    }

    public void aSyncQuery(String relativePath, final QueryListener listener) throws IOException {
        JSONRequest.get(url, new JSONRequestHandler<JavascriptYoctoMap>() {
            public void onRequestComplete(JavascriptYoctoMap object) {
                listener.resultEvent(object);
            }
        });
    }
}
