#!/system/bin/sh

# Check if exists
if [ ! -z "$(ls /data/app/com.oneplus.camera*)" ]; then
  rm -rf /data/app/com.oneplus.camera*
fi
