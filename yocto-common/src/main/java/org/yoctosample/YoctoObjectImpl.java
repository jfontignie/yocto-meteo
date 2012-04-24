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

package org.yoctosample;

import org.yoctosample.common.YoctoMap;

import java.io.IOException;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 3:27 PM
 */
abstract class YoctoObjectImpl implements YoctoObject {
    protected YoctoTemplate template;
    private String relativePath;

    private String logicalName;
    private YoctoProduct product;
    private String serialNumber;

    public YoctoObjectImpl(YoctoProduct product, YoctoTemplate template, String relativePath) {
        this.template = template;
        this.relativePath = relativePath;
        this.product = product;
    }

    public YoctoObjectImpl(String serialNumber, YoctoProduct product, YoctoTemplate template, String relativePath) {
        this(product, template, relativePath);
        this.serialNumber = serialNumber;
    }


    private YoctoMap query() throws IOException {
        return template.query(relativePath);
    }

    public String getLogicalName() {
        return logicalName;
    }

    public YoctoProduct getProduct() {
        return product;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void refresh() throws IOException {
        YoctoMap map = query();
        YoctoMap module = map.getMap("module");
        logicalName = module.getValue("logicalName").toString();
        String newSerialNumber = module.getValue("serialNumber").toString();
        if (serialNumber == null || serialNumber.equals(""))
            serialNumber = newSerialNumber;
        else if (!serialNumber.equals(newSerialNumber))
            throw new IllegalStateException("Internal error");

        refreshObject(map);
    }

    protected abstract void refreshObject(YoctoMap map) throws IOException;


}
