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

package org.yocto.sample.client.common;

import com.google.gwt.core.client.JavaScriptObject;
import org.yoctosample.common.StandaloneYoctoList;
import org.yoctosample.common.StandaloneYoctoMap;
import org.yoctosample.common.YoctoList;

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 10:37 PM
 */
public class JsonYoctoList extends JavaScriptObject implements YoctoList {
    protected JsonYoctoList() {
    }

    public final native int size() /*-{
        return this.length;
    }-*/;

    public final native Object getValue(int index) /*-{
        return this[index];
    }-*/;

    public final native StandaloneYoctoMap getMap(int index) /*-{
        return this[index];
    }-*/;

    public final native StandaloneYoctoList getList(int index) /*-{
        return this[index];
    }-*/;
}
