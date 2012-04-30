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

package org.yoctosample.common;

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 9:55 PM
 */
public interface YoctoMap {

    public YoctoMap getMap(String name);

    public String getString(String name);

    public int getInt(String name);

    public YoctoList getList(String name);

    public int size();

    public String getString(int index);

    public int getInt(int index);

    public YoctoMap getMap(int index);

    public YoctoList getList(int index);

    public String getKey(int index);
}
