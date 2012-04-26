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

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.yoctosample.common.YoctoTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 12:54 PM
 */
public class StandaloneYoctoHubTest {

    private static final String API_JSON = "api.json";
    private static final String BY_SERIAL_METEOMK1_0268_C_API_JSON = "/bySerial/METEOMK1-0268C/api.json";
    private YoctoHub hub;
    private URLConnectionReader reader;
    private URL url;

    @Before
    public void setUp() throws IOException {
        url = new URL("http://127.0.0.1:4444");
        reader = EasyMock.createMock(URLConnectionReader.class);
        EasyMock.expect(reader.getContent(new URL(url, API_JSON))).andReturn(
                RestYoctoMock.latency(100, RestYoctoMock.MAIN_JSON));
        EasyMock.expect(reader.getContent(new URL(url, BY_SERIAL_METEOMK1_0268_C_API_JSON))).andReturn(
                RestYoctoMock.latency(100, RestYoctoMock.METEO_JSON)).anyTimes();


        YoctoTemplate template = new StandaloneYoctoTemplate(url, reader);
        hub = new YoctoHub(template);

    }


    @Test
    public void testMeteo() throws IOException {


        EasyMock.replay(reader);
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
        for (YoctoObject object : objects) {
            YoctoMeteo meteo = (YoctoMeteo) object;
            meteo.refresh();
            assertTrue(meteo.getTemperature().getCurrentValue() > 0);
        }
        EasyMock.verify(reader);
    }

    @Test
    public void testLoop() throws IOException, InterruptedException {


        EasyMock.replay(reader);
        for (int i = 0; i < 10; i++) {
            Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
            for (YoctoObject object : objects) {
                YoctoMeteo meteo = (YoctoMeteo) object;
                meteo.refresh();
                assertTrue(meteo.getTemperature().getCurrentValue() > 0);
                System.out.println(meteo.getTemperature());
            }
            Thread.sleep(10);
        }
        EasyMock.verify(reader);
    }

    @Test
    public void testLoop2() throws IOException, InterruptedException {
        EasyMock.replay(reader);
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
        for (int i = 0; i < 10; i++) {
            for (YoctoObject object : objects) {
                YoctoMeteo meteo = (YoctoMeteo) object;
                meteo.refresh();
                assertTrue(meteo.getTemperature().getCurrentValue() > 0);
                System.out.println(meteo.getTemperature());
            }
            Thread.sleep(10);
        }
        EasyMock.verify(reader);
    }

    @Test
    public void testHub() throws IOException {
        EasyMock.replay(reader);
        try {
            new YoctoHub(null);
        } catch (IllegalStateException e) {
            System.out.println("Normal state");
        }
    }
}
