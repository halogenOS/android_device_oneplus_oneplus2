/*
 * Copyright (C) 2016 halogenOS
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.halogenos.hw.init;

import android.os.SystemProperties;

/**
 * Class to initialize device-specific stuff correctly.
 */
public class DeviceInit {
    
    /**
     * Initialize the device. This is called on boot, before anything else is loaded.
     * The system server will call this method right after it started up.
     * That means that you can do anything you want here without having to worry
     * about whether it is too late or not.
     */
    public static void initDevice() {
        String rfVer = SystemProperties.get("ro.boot.rf_v1");
        
        // This is only for debug purposes and won't be needed otherwise
        SystemProperties.set("ro.hw.device_init_started", "1");
        
        // Set some properties based on the phone model (country)
        switch(Integer.parseInt(rfVer)) {
            case 14:
                /* China */
                SystemProperties.set("ro.product.model", "ONE A2001");
                SystemProperties.set("ro.rf_version", "TDD_FDD_Ch_All");
                SystemProperties.set("ro.telephony.default_network", "20,20");
                break;
            case 24:
                /* Asia & Europe */
                SystemProperties.set("ro.product.model", "ONE A2003");
                SystemProperties.set("ro.rf_version", "TDD_FDD_Eu");
                SystemProperties.set("ro.telephony.default_network", "9,9");
                break;
            case 34:
                /* America */
                SystemProperties.set("ro.product.model", "ONE A2005");
                SystemProperties.set("ro.rf_version", "TDD_FDD_Am");
                SystemProperties.set("ro.telephony.default_network", "9,9");
                break;
            default:
                // This should not happen, but handle it anyways
                SystemProperties.set("ro.product.model", "ONE A2");
                SystemProperties.set("ro.telephony.default_network", "9,9");
                break;
        }
        
        // Of course LTE on any device
        SystemProperties.set("telephony.lteOnCdmaDevice", "1");
        SystemProperties.set("ro.telephony.lteOnCdmaDevice", "1");
        
        // This is only for debug purposes and won't be needed otherwise
        SystemProperties.set("ro.hw.device_init_done", "1");
    }
    
}