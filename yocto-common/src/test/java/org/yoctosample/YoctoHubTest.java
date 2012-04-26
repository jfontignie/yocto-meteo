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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;

import java.io.IOException;
import java.util.Collection;

import static junit.framework.Assert.assertEquals;
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
                content);

        content = RestYoctoMock.getMap(RestYoctoMock.COLOR_JSON);
        EasyMock.expect(yoctoTemplate.query("/bySerial/YRGBLED1-01934/api.json")).andReturn(
                content);

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

    }

    @Test
    public void testFindAllMeteo() throws IOException {
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
        if (objects != null)
            for (YoctoObject object : objects)
                assertNotNull(hub.findMeteo(object.getLogicalName()));
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
    public void testFindAllRelay() throws IOException {
        EasyMock.replay(yoctoTemplate);
        YoctoHub hub = new YoctoHub(yoctoTemplate);
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_RELAY);
        if (objects != null)
            for (YoctoObject object : objects)
                assertNotNull(hub.findRelay(object.getLogicalName()));
    }

}
