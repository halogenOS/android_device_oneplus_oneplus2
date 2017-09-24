#
# Copyright (C) 2015 The CyanogenMod Project
# Copyright (C) 2015-2016 OnePlus Ltd.
# Copyright (C) 2016-2017 The halogenOS Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

# Provides overrides to configure the HWUI memory limits

# These are taken from OOS3
PRODUCT_PROPERTY_OVERRIDES += \
	ro.hwui.texture_cache_size=112 \
	ro.hwui.layer_cache_size=72 \
	ro.hwui.r_buffer_cache_size=16 \
	ro.hwui.path_cache_size=44 \
	ro.hwui.gradient_cache_size=2 \
	ro.hwui.drop_shadow_cache_size=9 \
	ro.hwui.texture_cache_flushrate=0.4 \
	ro.hwui.text_small_cache_width=3072 \
	ro.hwui.text_small_cache_height=3072 \
	ro.hwui.text_large_cache_width=4096 \
	ro.hwui.text_large_cache_height=3072

# This is from OP5
PRODUCT_PROPERTY_OVERRIDES += \
    dalvik.vm.heapstartsize=16m \
    dalvik.vm.heapgrowthlimit=256m \
    dalvik.vm.heapsize=512m \
    dalvik.vm.heaptargetutilization=0.75 \
    dalvik.vm.heapminfree=4m \
    dalvik.vm.heapmaxfree=8m

# Taken from here https://review.lineageos.org/#/c/184969/3/device.mk
