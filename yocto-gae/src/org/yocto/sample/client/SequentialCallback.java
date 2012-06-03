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

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.yoctosample.YoctoCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Jacques Fontignie
 * Date: 4/27/12
 * Time: 10:24 AM
 * <p/>
 * <p/>
 * Make sure that the different callbacks are triggered in a certain order...
 */

public class SequentialCallback {

    private Logger logger = Logger.getLogger(this.getClass().getName());
    private List<GenericManagedCallback> callbacks;


    public SequentialCallback() {
        callbacks = new ArrayList<GenericManagedCallback>();
    }

    public AsyncCallback createCallback(AsyncCallback entry) {
        ManagedCallback callback = new ManagedCallback(entry);

        callbacks.add(callback);
        return callback;
    }

    public YoctoCallback createCallback(YoctoCallback yoctoCallback) {
        YoctoManagedCallback callback = new YoctoManagedCallback(yoctoCallback);
        callbacks.add(callback);
        return callback;
    }

    private synchronized void checkTrigger() {
        GenericManagedCallback last = null;
        for (GenericManagedCallback callback : callbacks) {
            if (callback.isFinished()) {
                callback.trigger();
            } else {
                last = callback;
                break;
            }
        }

        if (last != null)
            while (!last.equals(callbacks.get(0)))
                callbacks.remove(0);

    }

    public Runnable createCallback(Runnable runnable) {
        RunnableCallback callback = new RunnableCallback(runnable);
        callbacks.add(callback);
        return callback;
    }


    private abstract class GenericManagedCallback {
        private Throwable t;
        private Object o;
        private boolean throwableCaught;
        private boolean objectFound;

        public GenericManagedCallback() {
            throwableCaught = false;
            objectFound = false;
        }

        public void trigger() {
            logger.info("Triggering current callback: " + this);

            if (objectFound) success(o);
            if (throwableCaught) failure(t);
        }

        protected abstract void success(Object o);

        protected abstract void failure(Throwable t);

        public boolean isFinished() {
            return objectFound || throwableCaught;
        }

        public void onFailure(Throwable caught) {
            throwableCaught = true;
            t = caught;
            checkTrigger();
        }

        public void onSuccess(Object result) {
            objectFound = true;
            o = result;
            checkTrigger();
        }
    }

    private class ManagedCallback extends GenericManagedCallback implements AsyncCallback {

        private AsyncCallback callback;

        private ManagedCallback(AsyncCallback callback) {
            super();
            this.callback = callback;
        }


        @Override
        protected void success(Object o) {
            callback.onSuccess(o);
        }

        @Override
        protected void failure(Throwable t) {
            callback.onFailure(t);
        }

        @Override
        public String toString() {
            return callback.toString();
        }
    }

    private class YoctoManagedCallback extends GenericManagedCallback implements YoctoCallback {

        private YoctoCallback callback;

        private YoctoManagedCallback(YoctoCallback callback) {
            super();
            this.callback = callback;
        }


        @Override
        protected void success(Object o) {
            callback.onSuccess(o);
        }

        @Override
        protected void failure(Throwable t) {
            callback.onError(t);
        }

        public void onError(Throwable t) {
            super.onFailure(t);
        }


        @Override
        public String toString() {
            return callback.toString();
        }
    }

    private class RunnableCallback extends GenericManagedCallback implements Runnable {

        private Runnable runnable;

        public RunnableCallback(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        protected void success(Object o) {
            runnable.run();
        }

        @Override
        protected void failure(Throwable t) {
            throw new IllegalStateException("Should never be there");
        }

        public void run() {
            onSuccess(null);
        }


        @Override
        public String toString() {
            return runnable.toString();
        }
    }
}
