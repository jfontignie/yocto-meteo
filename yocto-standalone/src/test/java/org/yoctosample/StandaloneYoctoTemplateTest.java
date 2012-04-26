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
import org.yoctosample.common.YoctoMap;

import java.io.IOException;
import java.net.URL;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 10:05 AM
 */
public class StandaloneYoctoTemplateTest {

    private StandaloneYoctoTemplate yoctoTemplate;
    private URLConnectionReader reader;
    private URL url;


    @Before
    public void setUp() throws IOException {
        url = new URL("http://127.0.0.1:4444");
        reader = EasyMock.createMock(URLConnectionReader.class);
        yoctoTemplate = new StandaloneYoctoTemplate(url, reader);
    }

    @Test
    public void testQuery() throws IOException {
        EasyMock.expect(reader.getContent(new URL(url, "api.json"))).andReturn(
                RestYoctoMock.latency(100, RestYoctoMock.MAIN_JSON));
        EasyMock.replay(reader);
        assertNotNull(yoctoTemplate.query("api.json"));
        EasyMock.verify(reader);
    }

    @Test
    public void testAsyncQuery() throws IOException, InterruptedException {
        EasyMock.expect(reader.getContent(new URL(url, "api.json"))).andReturn(
                RestYoctoMock.latency(100, RestYoctoMock.MAIN_JSON));
        EasyMock.replay(reader);
        final boolean[] success = {false};
        yoctoTemplate.aSyncQuery("api.json", new YoctoCallback<YoctoMap>() {

            public void onSuccess(YoctoMap map) {
                success[0] = true;
            }

            public void onError(Throwable t) {
                assertTrue(false);
            }
        });

        Thread.sleep(3000);
        if (success[0])
            System.out.println("success");
        else
            throw new IllegalStateException("Wrong result");
        EasyMock.verify(reader);
    }


}
