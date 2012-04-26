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

package org.yoctosample;

/**
 * Created by: Jacques Fontignie
 * Date: 4/19/12
 * Time: 10:10 AM
 */
public enum YoctoProduct {

    YOCTO_HUB(0, "VirtualHub"),
    YOCTO_METEO(24, "Yocto-meteo"),
    YOCTO_RELAY(-1, "???"), YOCTO_COLOR(20, "Yocto-color");


    private int productId;
    private String productName;

    private YoctoProduct(int productId, String productName) {
        this.productId = productId;
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    private boolean matches(int productId) {
        return this.productId == productId;
    }

    public static YoctoProduct fromProductId(int productId) {
        for (YoctoProduct p : YoctoProduct.values()) {
            if (p.matches(productId)) return p;
        }
        return null;
    }


}
