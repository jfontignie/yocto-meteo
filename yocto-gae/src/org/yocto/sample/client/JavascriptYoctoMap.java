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

package org.yocto.sample.client;

import com.google.gwt.core.client.JavaScriptObject;
import org.yoctosample.common.YoctoMap;

/**
 * Author: Jacques Fontignie
 * Date: 4/24/12
 * Time: 10:25 PM
 */
public class JavascriptYoctoMap extends JavaScriptObject implements YoctoMap {


    protected JavascriptYoctoMap() {
    }

    public final native JavascriptYoctoMap getMap(String name) /*-{
        return this[name];
    }-*/;

    public final native String getString(String name) /*-{
        return this[name];
    }-*/;

    public final native int getInt(String name) /*-{
        return this[name];
    }-*/;

    public final native JavascriptYoctoList getList(String name) /*-{
        return this[name];
    }-*/;

}
