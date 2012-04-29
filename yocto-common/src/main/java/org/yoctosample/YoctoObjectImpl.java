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

package org.yoctosample;

import org.yoctosample.common.YoctoMap;
import org.yoctosample.common.YoctoTemplate;

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
    private YoctoHub hub;

    public YoctoObjectImpl(YoctoHub hub, String serialNumber, YoctoProduct product, YoctoTemplate template, String relativePath) {
        this.template = template;
        this.relativePath = relativePath;
        this.product = product;
        this.serialNumber = serialNumber;
        this.hub = hub;
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

    public void refresh(final YoctoCallback<Void> callback) {
        template.aSyncQuery(relativePath, new YoctoCallback<YoctoMap>() {
            public void onSuccess(YoctoMap result) {
                internalRefresh(result);
                callback.onSuccess(null);
            }

            public void onError(Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void refresh() {
        YoctoMap map = template.query(relativePath);
        internalRefresh(map);
    }

    private void internalRefresh(YoctoMap map) {
        YoctoMap module = map.getMap("module");
        logicalName = module.getString("logicalName");
        String newSerialNumber = module.getString("serialNumber");
        if (serialNumber == null || serialNumber.equals(""))
            serialNumber = newSerialNumber;
        else if (!serialNumber.equals(newSerialNumber))
            throw new IllegalStateException("Internal error");

        refreshObject(map);
    }

    protected abstract void refreshObject(YoctoMap map);

    public YoctoHub getHub() {
        return hub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof YoctoObjectImpl)) return false;

        YoctoObjectImpl that = (YoctoObjectImpl) o;

        if (serialNumber != null ? !serialNumber.equals(that.serialNumber) : that.serialNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return serialNumber != null ? serialNumber.hashCode() : 0;
    }
}
