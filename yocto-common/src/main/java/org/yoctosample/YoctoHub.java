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

import org.yoctosample.common.YoctoList;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;
import org.yoctosample.utils.YoctoDeviceList;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: jacques
 * Date: 4/7/12
 * Time: 11:22 PM
 */
public class YoctoHub extends YoctoObjectImpl {

    private YoctoDeviceList yoctoDeviceList;
    private boolean needRefresh;
    private HashMap<String, AdvertisedValue> advertisedValues;


    public YoctoHub(YoctoTemplate template) {
        super(null, null, YoctoProduct.YOCTO_HUB, template, "/api.json");
        advertisedValues = new HashMap<String, AdvertisedValue>();
        if (template == null) {
            throw new IllegalStateException("Template is null");
        }
        needRefresh = true;
    }

    public YoctoMeteo findMeteoBySerialNumber(String name) {
        if (needRefresh) refresh();
        return (YoctoMeteo) yoctoDeviceList.get(YoctoProduct.YOCTO_METEO, name);
    }

    public YoctoColor findColorBySerialNumber(String name) {
        if (needRefresh) refresh();
        return (YoctoColor) yoctoDeviceList.get(YoctoProduct.YOCTO_COLOR, name);
    }


    public Collection<YoctoObject> findAll(YoctoProduct product) {
        if (needRefresh) refresh();
        Map<String, YoctoObject> result = yoctoDeviceList.findAll(product);
        if (result == null) return null;
        return result.values();
    }

    @Override
    protected void refreshObject(YoctoMap map) {
        needRefresh = false;
        yoctoDeviceList = new YoctoDeviceList();

        YoctoMap services = map.getMap("services");
        YoctoList whitePages = services.getList("whitePages");

        HashMap<String, String> serials = new HashMap<String, String>();

        int size = whitePages.size();
        for (int i = 0; i < size; i++) {
            YoctoMap service = whitePages.getMap(i);
            int productId = service.getInt("productId");
            YoctoProduct product = YoctoProduct.fromProductId(productId);
            YoctoObject object = createObject(product, service);
            serials.put(object.getSerialNumber(), "");
        }
        //TODO remove the objects which are in yoctoDeviceList but not in serials...

        YoctoMap yelloPages = services.getMap("yellowPages");
        size = yelloPages.size();
        boolean loop = true;
        for (int i = 0; i < size; i++) {
            YoctoList function = null;
            try {
                function = yelloPages.getList(i);
            } catch (Exception e) {
                //Todo: dirty hack as GWT does not find the proper size, we will hit an exception
                loop = false;
            }
            if (!loop) break;
            for (int j = 0; j < function.size(); j++) {
                YoctoMap service = function.getMap(j);
                AdvertisedValue value = new AdvertisedValue(service);
                advertisedValues.put(value.getHardwareId(), value);
            }
        }
    }

    private YoctoObject createObject(YoctoProduct product, YoctoMap service) {
        String networkUrl = service.getString("networkUrl");
        String serialNumber = service.getString("serialNumber");
        YoctoObject result = yoctoDeviceList.findBySerialNumber(serialNumber);

        if (result == null) {
            switch (product) {
                case YOCTO_HUB:
                    result = this;
                    break;
                case YOCTO_METEO:
                    result = new YoctoMeteo(this, serialNumber, template, networkUrl);
                    break;
                case YOCTO_COLOR:
                    result = new YoctoColor(this, serialNumber, template, networkUrl);
                    break;
                default:
                    throw new IllegalStateException("Not implemented yet");
            }
            yoctoDeviceList.add(result);
        }
        return result;
    }

    public Collection<AdvertisedValue> findAllAdvertisedValues() {
        if (needRefresh) refresh();
        return advertisedValues.values();
    }

    public AdvertisedValue getAdvertisedValue(String name) {
        if (needRefresh) refresh();
        return advertisedValues.get(name);
    }

    public Collection<YoctoObject> findAll() {
        if (needRefresh) refresh();
        return yoctoDeviceList.findAll();
    }
}
