#!/bin/bash
#
# Do some OE cleanup before switching kernels
#

if [[ -z "${OETMP}" ]]; then
        OETMP=${OVEROTOP}/tmp
fi

packages=( ti-dsplink \
	   ti-codec-engine \
	   ti-local-power-manager \
	   ti-c6accel \
	   ti-codecs-omap3530 \
           ti-dmai \
           ti-dspbios \
           ti-linuxutils \
	   gstreamer-ti-3730 \
)

ASD="
sysvinit-2.86 \
gstreamer-ti \
ti-c6accel-apps \
ti-c6accel \
ti-codecs-omap3530-server \
ti-codecs-omap3530 \
ti-framework-components \ 
ti-codec-engine-examples \
ti-codec-engine \
ti-xdctools \
ti-cgt6x \
ti-xdais \
ti-edma3lld \
ti-biosutils \
virtual/kernel \
kernel-module-econ \
)
"




for pkg in ${packages[@]}
do
    bitbake -c cleanall $pkg
done

#rm -rf $OETMP/cache/*
#for pkg in ${packages[@]}
#do
#    bitbake $pkg
#done

