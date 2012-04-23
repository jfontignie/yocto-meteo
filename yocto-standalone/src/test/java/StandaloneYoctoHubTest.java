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

import org.junit.Test;

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

    private YoctoHub hub;

    //@Before
    public void setUp() throws IOException {
        YoctoTemplate template = new StandaloneYoctoTemplate(new URL("http://127.0.0.1:4444"));
        hub = new YoctoHub(template);
    }

    //@Test
    public void testMeteo() throws IOException {
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
        for (YoctoObject object : objects) {
            YoctoMeteo meteo = (YoctoMeteo) object;
            meteo.refresh();
            assertTrue(meteo.getTemperature().getCurrentValue() > 0);
        }
    }

    //@Test
    public void testLoop() throws IOException, InterruptedException {
        for (int i = 0; i < 10; i++) {
            Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
            for (YoctoObject object : objects) {
                YoctoMeteo meteo = (YoctoMeteo) object;
                meteo.refresh();
                assertTrue(meteo.getTemperature().getCurrentValue() > 0);
                System.out.println(meteo.getTemperature());
            }
            Thread.sleep(1000);
        }
    }

    //@Test
    public void testLoop2() throws IOException, InterruptedException {
        Collection<YoctoObject> objects = hub.findAll(YoctoProduct.YOCTO_METEO);
        for (int i = 0; i < 10; i++) {
            for (YoctoObject object : objects) {
                YoctoMeteo meteo = (YoctoMeteo) object;
                meteo.refresh();
                assertTrue(meteo.getTemperature().getCurrentValue() > 0);
                System.out.println(meteo.getTemperature());
            }
            Thread.sleep(1000);
        }
    }

    @Test
    public void testHub() throws IOException {
        try {
            new YoctoHub(null);
        } catch (IllegalStateException e) {
            System.out.println("Normal state");
        }
    }
}
