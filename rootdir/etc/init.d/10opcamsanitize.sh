#!/system/bin/sh

# Check if exists
if [ -e /data/app/com.oneplus.camera* ]; then
  rm -rf /data/app/com.oneplus.camera*
fi
