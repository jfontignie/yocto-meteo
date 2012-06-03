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

package org.yocto.sample.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.yocto.sample.client.WorldMapService;
import org.yocto.sample.client.dto.DataColor;
import org.yocto.sample.client.dto.DataHub;
import org.yocto.sample.client.dto.DataMeteo;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/25/12
 * Time: 9:18 PM
 */

public class WorldMapServiceImpl extends RemoteServiceServlet implements WorldMapService {


    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private static final PersistenceManagerFactory PMF =
            JDOHelper.getPersistenceManagerFactory("transactions-optional");
    private DAO<DataMeteo> meteoDAO;
    private DAO<DataHub> hubDAO;
    private DAO<DataColor> colorDAO;


    public WorldMapServiceImpl() {
        meteoDAO = new DAO<DataMeteo>();
        hubDAO = new DAO<DataHub>();
        colorDAO = new DAO<DataColor>();
    }

    //
    public void addMeteo(DataMeteo dataMeteo) {
        logger.info("Adding a new meteo in the Database");
        meteoDAO.add(dataMeteo);
    }

    public List<DataMeteo> listMeteos() {
        logger.info("Listing all the meteos");
        return meteoDAO.list(DataMeteo.class);
    }

    //
    private class DAO<T> {
        public List<T> list(Class clazz) {
            PersistenceManager pm = getPersistenceManager();
            List<T> list;
            try {
                Query q = pm.newQuery(clazz);
                list = (List<T>) q.execute();

                List<T> result = new ArrayList<T>();
                for (T dto : list)
                    result.add(dto);
                logger.info("Server has fetched: " + result.size());
                return result;
            } finally {
                pm.close();
            }
        }

        public void add(T dto) {
            PersistenceManager pm = getPersistenceManager();
            try {
                pm.makePersistent(dto);
            } finally {
                pm.close();
            }
        }
    }


    public void addHub(DataHub dataHub) {
        logger.info("Adding a new hub in the Database");
        hubDAO.add(dataHub);
    }

    public List<DataHub> listHubs() {
        logger.info("Listing all the hubs");
        return hubDAO.list(DataHub.class);
    }

    public List<DataColor> listColors() {
        logger.info("Listing all the colors");
        return colorDAO.list(DataColor.class);
    }

    public void addColor(DataColor color) {
        logger.info("Adding a new Hub in the Database");
        colorDAO.add(color);
    }

    private List<DataHub> hubStubs(int count) {
        Random r = new Random(count);
        List<DataHub> hubs = new ArrayList<DataHub>();
        for (int i = 0; i < count; i++) {
            DataHub hub = new DataHub("stub " + i,
                    r.nextDouble() * 360 - 180,
                    r.nextDouble() * 180 - 90, new Date());
            hubs.add(hub);
        }
        return hubs;
    }

    private PersistenceManager getPersistenceManager() {
        return PMF.getPersistenceManager();
    }


}
