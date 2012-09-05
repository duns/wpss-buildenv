#!/bin/bash

#
# This script flashes an image onto a connected SDHC card
#
DEVICE=/dev/sdc
FATDIR=/tmp/BOOT
ROOTDIR=/tmp/ROOT

XLOADER=MLO-overo
UBOOT=u-boot-overo.bin
KERNELFILE=uImage-overo.bin
MODULEFILES=modules-3.2-r103-overo.tgz

ROOTTAR=phase2-console-image-overo.tar.bz2

IMAGEDIR=$OVEROTOP/tmp/deploy/glibc/images/overo
LOADERDIR=$OVEROTOP/user.collection/loaders
IMAGEDIR=/tmp/tmp/overo
LOADERDIR=/tmp/tmp/overo
#IMAGEDIR=/tmp/tt2/overo

sudo mkdir -p ${FATDIR} ${ROOTDIR}
sudo sfdisk -l ${DEVICE}
echo are you sure you want to wipe device ${DEVICE} \[y\|N\]
read answer
[ $answer != 'y' ] && exit -1

#sudo dd if=/dev/zero of=${DEVICE} bs=1M count=10

DISKSIZE=`sudo sfdisk -s "${DEVICE}" -uS`
BOOTPARTSIZE=81920
SECTORSIZE=`echo ${DISKSIZE}\*2-2*2048-$BOOTPARTSIZE|bc`
echo $DEVICE ${DEVICE}${PART} $SECTORSIZE


echo  ",$BOOTPARTSIZE,0xC 
,$SECTORSIZE,L" | sudo sfdisk -uS ${DEVICE}
sleep 2

sudo mkfs.msdos ${DEVICE}1
sudo mkfs.ext3 ${DEVICE}2


sudo mount ${DEVICE}1 ${FATDIR}
sudo mount ${DEVICE}2 ${ROOTDIR}



# Copy the x-loader
sudo cp -f ${LOADERDIR}/${XLOADER} ${FATDIR}/MLO

# Copy the u-boot bootloader
sudo cp -f ${LOADERDIR}/${UBOOT} ${FATDIR}/u-boot.bin

# Copy the kernel image
sudo cp -f ${IMAGEDIR}/${KERNELFILE} ${FATDIR}/uImage






# Remove old files (if any)
#sudo rm -rf ${ROOTDIR}/* && sudo mkdir ${ROOTDIR}/lost+found
# Flash the root file system
sudo tar xjf ${IMAGEDIR}/${ROOTTAR} -C ${ROOTDIR}
# Copy the kernel image and modules
sudo cp -f ${IMAGEDIR}/${KERNELFILE} ${ROOTDIR}/boot/${KERNELFILE}
sudo tar xvzf ${IMAGEDIR}/${MODULEFILES} -C ${ROOTDIR}

sudo umount ${FATDIR} ${ROOTDIR}
echo TODO .scr
