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

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 10:05 AM
 */
public class StandaloneYoctoTemplateTest {

    private StandaloneYoctoTemplate yoctoTemplate;
    private URL fullUrl;
    private URL url;


    @Before
    public void setUp() throws MalformedURLException {
        url = new URL("http://127.0.0.1:4444");
        yoctoTemplate = new StandaloneYoctoTemplate(url);
        fullUrl = new URL(url, "api.json");
    }

    @Test
    public void testQuery() throws IOException {
        assertNotNull(yoctoTemplate.query("api.json"));
    }


}
