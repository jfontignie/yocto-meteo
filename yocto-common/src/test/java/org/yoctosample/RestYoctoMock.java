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

import org.codehaus.jackson.map.ObjectMapper;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.utils.StandaloneYoctoMap;

import java.io.IOException;
import java.util.Map;

/**
 * Author: Jacques Fontignie
 * Date: 4/25/12
 * Time: 9:39 AM
 */
class RestYoctoMock {

    public static final String MAIN_JSON = "{\n" +
            "\"module\":{\"productName\":\"VirtualHub\",\"serialNumber\":\"VIRTHUB0-480741bdd3\",\"logicalName\":\"\",\"productId\":0,\"productRelease\":1,\"firmwareRelease\":\"6019\",\"persistentSettings\":0,\"luminosity\":50,\"beacon\":0,\"upTime\":4034097505,\"usbCurrent\":0,\"realmHTTP\":\"YoctoDevices\",\"adminPassword\":\"\",\"userPassword\":\"\",\"rebootCountdown\":0,\"usbBandwidth\":0},\n" +
            "\"services\":{\n" +
            "\"whitePages\":[\n" +
            "{\"serialNumber\":\"VIRTHUB0-480741bdd3\",\"logicalName\":\"\",\"productName\":\"VirtualHub\",\"productId\":0,\"networkUrl\":\"/api\",\"beacon\":0,\"index\":0},\n" +
            "{\"serialNumber\":\"METEOMK1-0268C\",\"logicalName\":\"\",\"productName\":\"Yocto-Meteo\",\"productId\":24,\"networkUrl\":\"/bySerial/METEOMK1-0268C/api\",\"beacon\":0,\"index\":1},\n" +
            "{\"serialNumber\":\"YRGBLED1-01934\",\"logicalName\":\"\",\"productName\":\"Yocto-Color\",\"productId\":20,\"networkUrl\":\"/bySerial/YRGBLED1-01934/api\",\"beacon\":0,\"index\":2}],\n" +
            "\"yellowPages\":{\n" +
            "\"DataLogger\":[\n" +
            "{\"hardwareId\":\"METEOMK1-0268C.dataLogger\",\"logicalName\":\"\",\"advertisedValue\":\"OFF\",\"index\":0}],\n" +
            "\"Temperature\":[\n" +
            "{\"hardwareId\":\"METEOMK1-0268C.temperature\",\"logicalName\":\"\",\"advertisedValue\":\"22.9\",\"index\":1}],\n" +
            "\"Pressure\":[\n" +
            "{\"hardwareId\":\"METEOMK1-0268C.pressure\",\"logicalName\":\"\",\"advertisedValue\":\"963.0\",\"index\":2}],\n" +
            "\"Humidity\":[\n" +
            "{\"hardwareId\":\"METEOMK1-0268C.humidity\",\"logicalName\":\"\",\"advertisedValue\":\"47.0\",\"index\":3}],\n" +
            "\"ColorLed\":[\n" +
            "{\"hardwareId\":\"YRGBLED1-01934.colorLed2\",\"logicalName\":\"\",\"advertisedValue\":\"006a00\",\"index\":0},\n" +
            "{\"hardwareId\":\"YRGBLED1-01934.colorLed1\",\"logicalName\":\"\",\"advertisedValue\":\"433d00\",\"index\":1}]}}}";

    public static final String METEO_JSON = "{\n" +
            "\"module\":{\"productName\":\"Yocto-Meteo\",\"serialNumber\":\"METEOMK1-0268C\",\"logicalName\":\"\",\"productId\":24,\"productRelease\":1,\"firmwareRelease\":\"5991\",\"persistentSettings\":2,\"luminosity\":50,\"beacon\":0,\"upTime\":34325,\"usbCurrent\":26,\"realmHTTP\":\"YoctoDevices\",\"adminPassword\":\"\",\"userPassword\":\"\",\"rebootCountdown\":0,\"usbBandwidth\":0},\n" +
            "\"humidity\":{\"logicalName\":\"\",\"advertisedValue\":\"40.0\",\"currentValue\":2621440,\"lowestValue\":2621440,\"highestValue\":3342336},\n" +
            "\"pressure\":{\"logicalName\":\"\",\"advertisedValue\":\"956.0\",\"currentValue\":62652416,\"lowestValue\":62586880,\"highestValue\":62652416},\n" +
            "\"temperature\":{\"logicalName\":\"\",\"advertisedValue\":\"24.4\",\"currentValue\":1601536,\"lowestValue\":1396736,\"highestValue\":1601536},\n" +
            "\"dataLogger\":{\"logicalName\":\"\",\"advertisedValue\":\"OFF\",\"oldestRunIndex\":0,\"currentRunIndex\":1,\"samplingInterval\":1,\"timeUTC\":0,\"recording\":0,\"autoStart\":0,\"clearHistory\":0}}";

    public static final String COLOR_JSON = "{\n" +
            "\"module\":{\"productName\":\"Yocto-Color\",\"serialNumber\":\"YRGBLED1-01934\",\"logicalName\":\"\",\"productId\":20,\"productRelease\":1,\"firmwareRelease\":\"5175\",\"persistentSettings\":2,\"luminosity\":50,\"beacon\":0,\"upTime\":54561,\"usbCurrent\":24,\"realmHTTP\":\"YoctoDevices\",\"adminPassword\":\"\",\"userPassword\":\"\",\"rebootCountdown\":0,\"usbBandwidth\":0},\n" +
            "\"colorLed1\":{\"logicalName\":\"\",\"advertisedValue\":\"433d00\",\"rgbColor\":4406528,\"hslColor\":2621218,\"rgbMove\":{\"moving\":0,\"target\":0,\"ms\":0},\"hslMove\":{\"moving\":0,\"target\":0,\"ms\":0},\"rgbColorAtPowerOn\":0},\n" +
            "\"colorLed2\":{\"logicalName\":\"\",\"advertisedValue\":\"006a00\",\"rgbColor\":27136,\"hslColor\":5635893,\"rgbMove\":{\"moving\":0,\"target\":0,\"ms\":0},\"hslMove\":{\"moving\":0,\"target\":0,\"ms\":0},\"rgbColorAtPowerOn\":0}}";


    public static YoctoMap getMap(String content) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return new StandaloneYoctoMap(mapper.readValue(content, Map.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
