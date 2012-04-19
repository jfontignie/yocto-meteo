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

import java.util.HashMap;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 10:16 AM
 */
public class YoctoList {
    private HashMap<YoctoProduct, HashMap<String, YoctoObject>> map;

    public YoctoList() {
        clear();
    }


    public YoctoObject get(YoctoProduct product, String name) {
        HashMap<String, YoctoObject> objects = map.get(product);
        if (objects == null) return null;
        return objects.get(name);
    }

    protected void add(YoctoObject object) {
        HashMap<String, YoctoObject> objects = map.get(object.getProduct());
        if (objects == null) {
            objects = new HashMap<String, YoctoObject>();
            map.put(object.getProduct(), objects);
        }
        objects.put(object.getLogicalName(), object);
    }

    protected void clear() {
        map = new HashMap<YoctoProduct, HashMap<String, YoctoObject>>();
    }

    public HashMap<String, YoctoObject> findAll(YoctoProduct product) {
        return map.get(product);
    }
}
