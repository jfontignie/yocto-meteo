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

package org.yoctosample;import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: jacques
 * Date: 4/7/12
 * Time: 11:22 PM
 */
class YoctoHub extends YoctoObjectImpl implements YoctoObject {

    private YoctoList yoctoList;

    public YoctoHub(YoctoTemplate template) throws IOException {
        super(YoctoProduct.YOCTO_HUB, template, "api.json");
        if (template == null) {
            throw new IllegalStateException("Template is null");
        }
        refresh();
    }


    public YoctoRelay findRelay(String name) {
        return (YoctoRelay) yoctoList.get(YoctoProduct.YOCTO_RELAY, name);
    }

    public YoctoMeteo findMeteo(String name) {
        return (YoctoMeteo) yoctoList.get(YoctoProduct.YOCTO_METEO, name);
    }


    public Collection<YoctoObject> findAll(YoctoProduct product) {
        Map<String, YoctoObject> result = yoctoList.findAll(product);
        if (result == null) return null;
        return result.values();
    }

    @Override
    protected void refreshObject(Map<String, Object> result) throws IOException {
        yoctoList = new YoctoList();

        Map<String, Object> services = (Map<String, Object>) result.get("services");
        List<Map<String, Object>> whitePages = (List) services.get("whitePages");

        HashMap<String, String> serials = new HashMap<String, String>();

        for (Map<String, Object> service : whitePages) {
            YoctoProduct product = YoctoProduct.getFromProductId((Integer) service.get("productId"));
            YoctoObject object = createObject(product, service);
            serials.put(object.getSerialNumber(), "");
        }

        //TODO remove the objects which are in yoctoList but not in serials...
    }

    private YoctoObject createObject(YoctoProduct product, Map<String, Object> service) throws IOException {
        String networkUrl = service.get("networkUrl").toString();
        String serialNumber = service.get("serialNumber").toString();
        YoctoObject result = yoctoList.findBySerialNumber(serialNumber);

        if (result == null) {
            switch (product) {
                case YOCTO_HUB:
                    result = this;
                    break;
                case YOCTO_METEO:
                    result = new YoctoMeteo(serialNumber, template, networkUrl);
                    break;
                default:
                    throw new IllegalStateException("Not implemented yet");
            }
            yoctoList.add(result);
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
