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

import org.yocto.sample.client.dto.DataHub;
import org.yoctosample.YoctoHub;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Jacques Fontignie
 * Date: 4/27/12
 * Time: 9:03 AM
 */
public class Hub extends AbstractFunction<Hub, DataHub, YoctoHub> {


    private ArrayList<AbstractFunction> list;

    public Hub(DataHub dataHub, YoctoHub hub) {
        super(dataHub, hub);
        this.list = new ArrayList<AbstractFunction>();
    }

    @Override
    protected DataHub createDTO(final YoctoHub result) {
        DataHub newHub = new DataHub(result.getSerialNumber(), 0, 0, new Date());
        return newHub;
    }

    public Hub(YoctoHub hub) {
        this(null, hub);
    }

    public Hub(DataHub hub) {
        this(hub, null);
    }

    public void add(AbstractFunction function) {
        list.add(function);
    }

    public List<AbstractFunction> getChildren() {
        return list;
    }
}
