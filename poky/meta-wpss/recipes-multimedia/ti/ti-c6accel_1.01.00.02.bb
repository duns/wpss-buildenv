require ti-c6accel.inc

#SRC_URI[c6accelbin.md5sum] = "4cfb9ae9d1b69954a22b454726d4d6cb"
#SRC_URI[c6accelbin.sha256sum] = "9fac3295c5c8533535e18f96c9a31c446f5a275798ace8c145f9ed51fa3e3ece"
SRC_URI = "file://c6accel_1_01_00_02.tgz "

LIC_FILES_CHKSUM = "file://docs/ReleaseNotes_C6Accel.txt;md5=9101f2f6aff046e123799ce9c5fdc99f"

SSRC_URI_append = " \
                  file://0001-soc-honour-buildsystem-CFLAGS-and-LDFLAGS-when-set.patch \
                 "

BINFILE=""
PV = "1_01_00_02"


CFLAGS += "-fPIC"

