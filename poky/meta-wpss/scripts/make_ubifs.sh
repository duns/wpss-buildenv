#!/bin/bash

#
# This script flashes an image onto a connected SDHC card
#
RANDN=`echo $RANDOM|md5sum|awk '{print $1}'`
FATDIR=/tmp/BOOT-$RANDN
ROOTDIR=/tmp/ROOT-$RANDN

KERNELFILE=uImage-3.2-r103-overo.bin
MODULEFILES=modules-3.2-r103-overo.tgz
UBIIMGBNAME=wpss-console-image-overo.ubi
ROOTTARBNAME=wpss-console-image-overo.tar.bz2
IMAGEDIR=/mnt/datadisk0/phase2/wpss-buildenv/build/tmp/deploy/images
DESTDIR=/mnt/datadisk0/phase2/wpss-buildenv/poky-overo/tmp/deploy/images
DESTDIR=/tmp/
UBICFG=/tmp/ubinize.cfg

#LOADERDIR=$OVEROTOP/user.collection/loaders
UBIIMG=${DESTDIR}/${UBIIMGBNAME}
ROOTTAR=${IMAGEDIR}/${ROOTTARBNAME}
[ -n "$1" ] && ROOTTAR="$1"
[ -n "$2" ] && UBIIMG="$2"

echo untaring root dir
sudo mkdir -p  ${ROOTDIR}
echo untaring the root file system
sudo tar xjf ${ROOTTAR} -C ${ROOTDIR}
cat > $UBICFG << EOF
[ubifs] 
mode=ubi
image=${UBIIMG}.img
vol_id=0 
vol_type=dynamic 
vol_name=rootfs 
vol_flags=autoresize 
vol_size=250MiB
EOF

sudo mkfs.ubifs -m 2048 -e 129024 -c 4000 -o $UBIIMG.img -r $ROOTDIR
sudo ubinize -o $UBIIMG -m 2048 -p 128KiB -s 512 $UBICFG

sudo rm -rf ${ROOTDIR}
exit

# Remove old files (if any)
#sudo rm -rf ${ROOTDIR}/* && sudo mkdir ${ROOTDIR}/lost+found
# Flash the root file system
# Copy the kernel image and modules
echo copying the kernel image 
sudo cp -f ${IMAGEDIR}/${KERNELFILE} ${ROOTDIR}/boot/${KERNELFILE}
echo copying the kernel modules
sudo tar xzf ${IMAGEDIR}/${MODULEFILES} -C ${ROOTDIR}
