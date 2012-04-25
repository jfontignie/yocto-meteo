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

package org.yoctosample.utils;

import org.yoctosample.common.YoctoList;
import org.yoctosample.common.YoctoMap;

import java.util.List;
import java.util.Map;

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 10:12 PM
 */
public class StandaloneYoctoList implements YoctoList {

    private List list;

    public StandaloneYoctoList(List list) {
        this.list = list;
    }

    public int size() {
        return list.size();
    }

    public String getString(int index) {
        return list.get(index).toString();
    }

    public int getInt(int index) {
        return Integer.valueOf(list.get(index).toString());
    }

    public YoctoMap getMap(int index) {
        return new StandaloneYoctoMap((Map<String, Object>) list.get(index));
    }

    public YoctoList getList(int index) {
        return new StandaloneYoctoList((List) list.get(index));
    }
}
