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

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.yocto.sample.client.dto.DataColor;
import org.yocto.sample.client.dto.DataHub;
import org.yocto.sample.client.dto.DataMeteo;

import java.util.List;

/**
 * Author: Jacques Fontignie
 * Date: 4/25/12
 * Time: 9:19 PM
 */

@RemoteServiceRelativePath("worldmap")
public interface WorldMapService extends RemoteService {

    public void addMeteo(DataMeteo dataMeteo);

    public List<DataMeteo> listMeteos();

    public void addHub(DataHub dataHub);

    public List<DataHub> listHubs();

    public List<DataColor> listColors();

    public void addColor(DataColor color);
}
