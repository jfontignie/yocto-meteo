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

package org.yoctosample.utils;

import org.yoctosample.common.YoctoList;
import org.yoctosample.common.YoctoMap;

import java.util.List;
import java.util.Map;

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 9:57 PM
 */
public class StandaloneYoctoMap implements YoctoMap {

    private Map<String, Object> map;
    private Object[] values;
    private Object[] keys;

    public StandaloneYoctoMap(Map<String, Object> stringObjectMap) {
        if (stringObjectMap == null) throw new IllegalStateException("the content is empty");
        this.map = stringObjectMap;
    }

    public YoctoMap getMap(String name) {
        return new StandaloneYoctoMap((Map<String, Object>) map.get(name));
    }

    public String getString(String name) {
        return map.get(name).toString();
    }

    public int getInt(String name) {
        return Integer.valueOf(map.get(name).toString());
    }

    public YoctoList getList(String name) {
        return new StandaloneYoctoList((List) map.get(name));
    }

    public int size() {
        return map.size();
    }

    public String getString(int index) {
        checkValues();
        return values[index].toString();
    }

    public int getInt(int index) {
        checkValues();
        return Integer.valueOf(values[index].toString());
    }

    public YoctoMap getMap(int index) {
        checkValues();
        return new StandaloneYoctoMap((Map<String, Object>) values[index]);
    }

    public YoctoList getList(int index) {
        checkValues();
        return new StandaloneYoctoList((List) values[index]);
    }

    private void checkValues() {
        if (values == null) {
            values = map.values().toArray();
            keys = map.keySet().toArray();
        }
    }

    public String getKey(int index) {
        return keys[index].toString();
    }
}
