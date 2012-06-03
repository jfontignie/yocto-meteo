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

package org.yoctosample;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;

import java.io.IOException;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

/**
 * Created by: Jacques Fontignie
 * Date: 4/8/12
 * Time: 12:03 AM
 */
public class YoctoHubTest {

    private YoctoTemplate yoctoTemplate;

    @Before
    public void setUp() throws IOException {
        yoctoTemplate = EasyMock.createMock(YoctoTemplate.class);


        YoctoMap content = RestYoctoMock.getMap(RestYoctoMock.MAIN_JSON);
        EasyMock.expect(yoctoTemplate.query("/api.json")).andReturn(
                content).once();

        content = RestYoctoMock.getMap(RestYoctoMock.COLOR_JSON);
        EasyMock.expect(yoctoTemplate.query("/bySerial/YRGBLED1-01934/api.json")).andReturn(
                content).anyTimes();


    }


    @Test
    public void testRefresh() throws Exception {

        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        hub.refresh();
        hub.findAll();

        Collection<YoctoObject> list = hub.findAll(YoctoProduct.YOCTO_METEO);
        for (YoctoObject object : list)
            System.out.println(object);

        EasyMock.verify(yoctoTemplate);
    }

    @Test
    public void testFindAllMeteo() throws IOException {
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
        if (objects != null)
            for (YoctoObject object : objects)
                assertNotNull(hub.findMeteoBySerialNumber(object.getSerialNumber()));
    }

    @Test
    public void testFindAllColor() {
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_COLOR);
        assertEquals(1, objects.size());
        YoctoColor color = (YoctoColor) objects.iterator().next();
        color.refresh();

        Assert.assertEquals(color.getColorLed1().getRgbColor(), 0x433d00);
        Assert.assertEquals(color.getColorLed2().getRgbColor(), 0x006a00);


    }

    @Test
    public void testAsynchronous() {


        YoctoTemplate template = new YoctoTemplate() {
            public YoctoMap query(String relativePath) {
                throw new IllegalStateException("Should not be called");
            }

            public void aSyncQuery(String relativePath, YoctoCallback<YoctoMap> listener) {
                if (relativePath.equals("/api.json")) {
                    listener.onSuccess(RestYoctoMock.getMap(RestYoctoMock.MAIN_JSON));
                    return;
                }
                if (relativePath.equals("/bySerial/YRGBLED1-01934/api.json")) {
                    listener.onSuccess(RestYoctoMock.getMap(RestYoctoMock.COLOR_JSON));
                    return;
                }
                throw new IllegalStateException("Should not be there: " + relativePath);
            }
        };


        final YoctoHub hub = new YoctoHub(template);
        hub.refresh(new YoctoCallback<Void>() {
            public void onSuccess(Void result) {
                Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_COLOR);
                assertEquals(1, objects.size());
                final YoctoColor color = (YoctoColor) objects.iterator().next();
                color.refresh(new YoctoCallback<Void>() {
                    public void onSuccess(Void result) {
                        Assert.assertEquals(color.getColorLed1().getRgbColor(), 0x433d00);
                        Assert.assertEquals(color.getColorLed2().getRgbColor(), 0x006a00);
                    }

                    public void onError(Throwable t) {
                        //To change body of implemented methods use File | Settings | File Templates.
                    }
                });


            }

            public void onError(Throwable t) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
    }

    @Test(expected = IllegalStateException.class)
    public void testEmptyHub() {
        new YoctoHub(null);
    }


    @Test
    public void testFindColor() throws IOException {
        String serial = "YRGBLED1-01934";
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        hub.refresh();
        YoctoColor object = hub.findColorBySerialNumber(serial);
        assertEquals(new YoctoColor(hub, serial, yoctoTemplate, null), object);
    }


    @Test
    public void testFindMeteo() throws IOException {
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        YoctoMeteo object = hub.findMeteoBySerialNumber("test");
        assertNull(object);
    }


    @Test
    public void testFindAllMeteos() throws IOException {
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_RELAY);
        if (objects != null)
            for (YoctoObject object : objects)
                assertNotNull(hub.findMeteoBySerialNumber(object.getSerialNumber()));
    }

    @Test
    public void testGetAdvertisedValues() {
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        Collection<AdvertisedValue> values = hub.findAllAdvertisedValues();
        assertEquals(6, values.size());
    }

    @Test
    public void testGetAdvertisedValue() {
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        AdvertisedValue value = hub.getAdvertisedValue("METEOMK1-0268C.dataLogger");
        assertEquals("OFF", value.getAdvertisedValue());

    }

}
