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

import org.yoctosample.common.YoctoList;
import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;
import org.yoctosample.utils.YoctoDeviceList;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * User: jacques
 * Date: 4/7/12
 * Time: 11:22 PM
 */
public class YoctoHub extends YoctoObjectImpl implements YoctoObject {

    private YoctoDeviceList yoctoDeviceList;
    private boolean needRefresh;

    public YoctoHub(YoctoTemplate template) throws IOException {
        super(YoctoProduct.YOCTO_HUB, template, "api.json");
        if (template == null) {
            throw new IllegalStateException("Template is null");
        }
        needRefresh = true;
    }


    public YoctoRelay findRelay(String name) throws IOException {
        if (needRefresh) refresh();
        return (YoctoRelay) yoctoDeviceList.get(YoctoProduct.YOCTO_RELAY, name);
    }

    public YoctoMeteo findMeteo(String name) throws IOException {
        if (needRefresh) refresh();
        return (YoctoMeteo) yoctoDeviceList.get(YoctoProduct.YOCTO_METEO, name);
    }

    public YoctoColor findColor(String name) throws IOException {
        if (needRefresh) refresh();
        return (YoctoColor) yoctoDeviceList.get(YoctoProduct.YOCTO_COLOR, name);
    }


    public Collection<YoctoObject> findAll(YoctoProduct product) throws IOException {
        if (needRefresh) refresh();
        Map<String, YoctoObject> result = yoctoDeviceList.findAll(product);
        if (result == null) return null;
        return result.values();
    }

    @Override
    protected void refreshObject(YoctoMap map) throws IOException {
        needRefresh = false;
        yoctoDeviceList = new YoctoDeviceList();

        YoctoMap services = map.getMap("services");
        YoctoList whitePages = services.getList("whitePages");

        HashMap<String, String> serials = new HashMap<String, String>();

        for (int i = 0; i < whitePages.size(); i++) {
            YoctoMap service = whitePages.getMap(i);
            YoctoProduct product = YoctoProduct.getFromProductId((Integer) service.getValue("productId"));
            YoctoObject object = createObject(product, service);
            serials.put(object.getSerialNumber(), "");
        }

        //TODO remove the objects which are in yoctoDeviceList but not in serials...
    }

    private YoctoObject createObject(YoctoProduct product, YoctoMap service) throws IOException {
        String networkUrl = service.getValue("networkUrl").toString();
        String serialNumber = service.getValue("serialNumber").toString();
        YoctoObject result = yoctoDeviceList.findBySerialNumber(serialNumber);

        if (result == null) {
            switch (product) {
                case YOCTO_HUB:
                    result = this;
                    break;
                case YOCTO_METEO:
                    result = new YoctoMeteo(serialNumber, template, networkUrl);
                    break;
                case YOCTO_COLOR:
                    result = new YoctoColor(serialNumber, template, networkUrl);
                    break;
                default:
                    throw new IllegalStateException("Not implemented yet");
            }
            yoctoDeviceList.add(result);
        }
        return result;
    }

    public String describe() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean isOnline() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void load(int ms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

}
