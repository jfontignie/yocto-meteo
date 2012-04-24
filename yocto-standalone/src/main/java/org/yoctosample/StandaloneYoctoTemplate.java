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

package org.yoctosample;

import org.codehaus.jackson.map.ObjectMapper;
import org.yoctosample.common.StandaloneYoctoMap;
import org.yoctosample.common.YoctoMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/8/12
 * Time: 12:28 AM
 */
public class StandaloneYoctoTemplate implements YoctoTemplate {

    private URL url;

    public StandaloneYoctoTemplate(URL url) {
        this.url = url;
    }

    public void aSyncQuery(String relativePath, QueryListener listener) throws IOException {
        Thread thread = new Thread(new BackgroundQuerier(relativePath, listener));
        thread.start();
    }

    public YoctoMap query(String relativePath) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String content = URLConnectionReader.getContent(new URL(url, relativePath));
        try {
            return new StandaloneYoctoMap((Map<String, Object>) mapper.readValue(content, Map.class));
        } catch (IOException e) {
            System.out.println("Error while reading " + new URL(url, relativePath));
            throw e;
        }
    }

    private class BackgroundQuerier implements Runnable {

        private QueryListener listener;
        private String relativePath;

        public BackgroundQuerier(String relativePath, QueryListener listener) {
            this.relativePath = relativePath;
            this.listener = listener;
        }

        public void run() {
            try {
                listener.resultEvent(query(relativePath));
            } catch (IOException e) {
                listener.exceptionEvent(e);
            }
        }
    }

    private static class URLConnectionReader {
        public static String getContent(URL url) throws IOException {
            StringBuilder buffer = new StringBuilder();
            URLConnection yc = url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(
                    yc.getInputStream(), Charset.defaultCharset()));
            String inputLine;
            while ((inputLine = in.readLine()) != null)
                buffer.append(inputLine);
            in.close();
            return buffer.toString();
        }
    }
}
