/*
 * Copyright (C) 2017 The halogenOS Project
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

package org.halogenos.device.tweakpanel;

import android.os.Bundle;
import android.os.SystemProperties;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;

public class TweakPanel extends PreferenceActivity
                        implements OnPreferenceChangeListener {

    private static final String KEY_HOTPLUG = "hotplug";
    private static final String PROP_HALOPLUG = "service.haloplugd";

    private SwitchPreference mHotplugPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.tweakpanel_settings);

        mHotplugPreference = (SwitchPreference) findPreference(KEY_HOTPLUG);
        mHotplugPreference.setChecked(
            SystemProperties.get(PROP_HALOPLUG, "").equals("start"));
        mHotplugPreference.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference pref, Object value) {
        switch (pref.getKey()) {
            case KEY_HOTPLUG:
                SystemProperties.set(PROP_HALOPLUG,
                    (boolean)value ? "start" : "stop");
                break;
            default: break;
        }
        return true;
    }

}
