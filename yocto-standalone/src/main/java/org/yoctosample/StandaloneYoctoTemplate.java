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

package org.yoctosample;

import org.codehaus.jackson.map.ObjectMapper;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;
import org.yoctosample.utils.StandaloneYoctoMap;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/8/12
 * Time: 12:28 AM
 */
public class StandaloneYoctoTemplate implements YoctoTemplate {

    private URL url;
    private URLConnectionReader reader;

    public StandaloneYoctoTemplate(URL url, URLConnectionReader reader) {
        this.url = url;
        this.reader = reader;
    }

    public void aSyncQuery(String relativePath, YoctoCallback<YoctoMap> listener) {
        Thread thread = new Thread(new BackgroundQuerier(relativePath, listener));
        thread.start();
    }

    public YoctoMap query(String relativePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            URL newUrl = new URL(url, relativePath);
            String content = reader.getContent(newUrl);
            if (content == null) throw new IllegalStateException("Content is empty");
            return new StandaloneYoctoMap((Map<String, Object>) mapper.readValue(content, Map.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    private class BackgroundQuerier implements Runnable {

        private YoctoCallback<YoctoMap> listener;
        private String relativePath;

        public BackgroundQuerier(String relativePath, YoctoCallback<YoctoMap> listener) {
            this.relativePath = relativePath;
            this.listener = listener;
        }

        public void run() {
            YoctoMap result;
            try {
                result = query(relativePath);
            } catch (Throwable t) {
                listener.onError(t);
                return;
            }
            listener.onSuccess(result);
        }
    }


}
