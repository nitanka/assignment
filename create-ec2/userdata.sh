#!/bin/bash

DEVICE=/dev/$(lsblk -n | awk '$NF != "/" {print $1}')
FS_TYPE=$(file -s $DEVICE | awk '{print $2}')
MOUNT_POINT=/data

# If no FS, then this output contains "data"
if [ "$FS_TYPE" = "data" ]
then
    echo "Creating file system on $DEVICE"
    mkfs -t ext4 $DEVICE
fi

mkdir $MOUNT_POINT
mount $DEVICE $MOUNT_POINT
