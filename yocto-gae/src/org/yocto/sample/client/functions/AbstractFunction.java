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

package org.yocto.sample.client.functions;

import com.google.gwt.core.client.GWT;
import org.yocto.sample.client.WorldMapService;
import org.yocto.sample.client.WorldMapServiceAsync;
import org.yocto.sample.client.dto.DataObject;
import org.yoctosample.YoctoCallback;
import org.yoctosample.YoctoObject;

import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/27/12
 * Time: 9:09 AM
 */
public abstract class AbstractFunction<X extends AbstractFunction, T extends DataObject, S extends YoctoObject> {

    protected final Logger logger = Logger.getLogger(this.getClass().getName());
    protected static final WorldMapServiceAsync worldMapService = GWT.create(WorldMapService.class);


    private S yocto;
    private T dto;

    public AbstractFunction(T dto, S yocto) {
        this.dto = dto;
        this.yocto = yocto;
    }

    public T getDto() {
        return dto;
    }

    public S getYocto() {
        return yocto;
    }

    public boolean matches(T dto) {
        if (this.dto == null) return dto == null;
        if (dto == null) return false;
        return this.dto.equals(dto);
    }

    public boolean matches(X other) {
        return matches((T) other.getDto()) || matches((S) other.getYocto());
    }

    public boolean matches(S yocto) {
        if (this.yocto == null) return yocto == null;
        if (yocto == null) return false;
        return this.yocto.equals(yocto);
    }

    public void refresh(final YoctoCallback<X> callback) {
        if (yocto == null) callback.onError(new NullPointerException("YoctoObject does not exist"));

        yocto.refresh(new YoctoCallback<S>() {
            public void onSuccess(S result) {
                dto = createDTO(result);
                callback.onSuccess((X) AbstractFunction.this);
            }

            public void onError(Throwable t) {
                callback.onError(t);
            }
        });
    }

    public void setDto(T dto) {
        this.dto = dto;
    }

    protected abstract T createDTO(S result);
}
