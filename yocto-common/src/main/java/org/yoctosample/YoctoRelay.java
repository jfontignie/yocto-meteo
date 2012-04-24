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
import java.util.Map;

/**
 * Created by: Jacques Fontignie
 * Date: 4/7/12
 * Time: 11:41 PM
 */
public class YoctoRelay extends YoctoObjectImpl implements YoctoObject {


    public YoctoRelay(YoctoTemplate template, String relativePath) throws IOException {
        super(YoctoProduct.YOCTO_RELAY, template, relativePath + ".json");
    }


    public String describe() {
        throw new IllegalStateException("Not IMplemented yet");
    }

    public boolean isOnline() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void load(int ms) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getLogicalName() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void refreshObject(Map<String, Object> result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setLogicalName(String name) {

    }


}