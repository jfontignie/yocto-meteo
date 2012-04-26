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

package org.yocto.sample.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.yocto.sample.client.WorldMapService;
import org.yocto.sample.client.common.DataMeteo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/25/12
 * Time: 9:18 PM
 */
public class WorldMapServiceImpl extends RemoteServiceServlet implements WorldMapService {

    //
    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final PersistenceManagerFactory PMF =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");

    //
    public void addMeteo(DataMeteo dataMeteo) {
        logger.info("Adding a new meteo in the Database");
        PersistenceManager pm = getPersistenceManager();
        try {
            pm.makePersistent(dataMeteo);
        } finally {
            pm.close();
        }
    }

    public List<DataMeteo> listMeteos() {
        logger.info("Listing all the meteos");
        PersistenceManager pm = getPersistenceManager();
        List<DataMeteo> list;
        try {
            Query q = pm.newQuery(DataMeteo.class);
            list = (List<DataMeteo>) q.execute();

            List<DataMeteo> result = new ArrayList<DataMeteo>();
            for (DataMeteo dm : list)
                result.add(dm);
            logger.info("Server has fetched: " + list.size());
            return result;
        } finally {
            pm.close();
        }

    }

    private PersistenceManager getPersistenceManager() {
        return PMF.getPersistenceManager();
    }
}
