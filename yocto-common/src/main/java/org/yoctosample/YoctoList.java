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

package org.yoctosample;import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 10:16 AM
 */
public class YoctoList implements Iterable<YoctoObject> {
    private HashMap<YoctoProduct, HashMap<String, YoctoObject>> map;
    private HashMap<String, YoctoObject> serials;

    public YoctoList() {
        map = new HashMap<YoctoProduct, HashMap<String, YoctoObject>>();
        serials = new HashMap<String, YoctoObject>();
    }


    public YoctoObject get(YoctoProduct product, String name) {
        HashMap<String, YoctoObject> objects = map.get(product);
        if (objects == null) return null;
        return objects.get(name);
    }

    protected void add(YoctoObject object) {
        serials.put(object.getSerialNumber(), object);
        HashMap<String, YoctoObject> objects = map.get(object.getProduct());
        if (objects == null) {
            objects = new HashMap<String, YoctoObject>();
            map.put(object.getProduct(), objects);
        }
        objects.put(object.getLogicalName(), object);
    }


    public HashMap<String, YoctoObject> findAll(YoctoProduct product) {
        return map.get(product);
    }

    public YoctoObject findBySerialNumber(String serialNumber) {
        return serials.get(serialNumber);
    }

    public Iterator<YoctoObject> iterator() {
        return serials.values().iterator();
    }

    public void remove(YoctoObject object) {
        serials.remove(object.getSerialNumber());
        HashMap<String, YoctoObject> objects = map.get(object.getProduct());
        objects.remove(object.getLogicalName());
    }
}
