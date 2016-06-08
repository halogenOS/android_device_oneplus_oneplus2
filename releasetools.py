#
# Copyright (C) 2016 Exodus Android
# Copyright (C) 2016 halogenOS
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


"""Emit commands needed for OnePlus2 during OTA installation
(installing the firmware images)."""

import common
import re
import hashlib

bootdevice_path = "/dev/block/bootdevice/by-name/"
def FullOTA_Assertions(info):
  print "FullOTA_Assertions not implemented"

def IncrementalOTA_Assertions(info):
  print "IncrementalOTA_Assertions not implemented"

def InstallImage(img_name, img_file, partition, info):
  common.ZipWriteStr(info.output_zip, img_name, img_file)
  info.script.AppendExtra(('package_extract_file("' + img_name + '","' + "/tmp/fwflash/" + partition + '");'))

image_paths = {}
image_partitions = {
   'NON-HLOS.bin'      : 'modem',
   'static_nvbk.bin'   : 'oem_stanvbk',
   'emmc_appsboot.mbn' : 'aboot',
   'rpm.mbn'           : 'rpm',
   'sdi.mbn'           : 'sdi',
   'sbl1.mbn'          : 'sbl1',
   'pmic.mbn'          : 'pmic',
   'tz.mbn'            : 'tz',
   'hyp.mbn'           : 'hyp',
   'BTFM.bin'          : 'bluetooth',
}

def FullOTA_InstallEnd(info):
  """MD5 check of firmware. On mismatch, shipped image will be flashed."""
  script = info.script
  script.AppendExtra('run_program("/sbin/busybox", "mkdir", "-p", "/tmp/fwflash");')
  for k, v in image_partitions.iteritems():
    try:
      img_file = info.input_zip.read("RADIO/" + k)
      img_file_md5sum = hashlib.md5(img_file).hexdigest()
      dev_path = bootdevice_path.join(v)
      InstallImage(k, img_file, v, info)
      script.AppendExtra('run_program("/sbin/sh", "/sbin/busybox", '
                         '"[ ' + img_file_md5sum + ' == $(/sbin/md5sum ' + bootdevice_path + v + ' | cut -d \' \' -f1) ] || '
                         'dd if=/tmp/fwflash/' + v + ' of=' + bootdevice_path + v + '");')
    except KeyError:
      print "warning: no " + k + " image in input target_files; not flashing " + k
  script.AppendExtra('run_program("/sbin/busybox", "rm", "-rf", "/tmp/fwflash");')
