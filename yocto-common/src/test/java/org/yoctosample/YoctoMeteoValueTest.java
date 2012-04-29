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
import org.junit.Before;
import org.junit.Test;
import org.yoctosample.common.YoctoMap;

import static junit.framework.Assert.assertEquals;

/**
 * Author: Jacques Fontignie
 * Date: 4/29/12
 * Time: 7:46 PM
 */
public class YoctoMeteoValueTest {

    private YoctoMap map;

    @Before
    public void setUp() {
        map = EasyMock.createMock(YoctoMap.class);
        EasyMock.expect(map.getString("advertisedValue")).andReturn("0");
        EasyMock.expect(map.getString("logicalName")).andReturn("test");
    }

    @Test
    public void testYoctoMeteoValue() {
        EasyMock.replay(map);
        YoctoMeteoValue value = new YoctoMeteoValue(map);
        assertEquals((float) 0, value.getAdvertisedValue());

        assertEquals("test", value.getLogicalName());
    }
}
