require ti-c6accel.inc

#SRC_URI[c6accelbin.md5sum] = "4cfb9ae9d1b69954a22b454726d4d6cb"
#SRC_URI[c6accelbin.sha256sum] = "9fac3295c5c8533535e18f96c9a31c446f5a275798ace8c145f9ed51fa3e3ece"
require recipes-ti/includes/ti-eula-unpack.inc
SRC_URI[c6accelbin.md5sum] = "d9e3943efbae2b4f85df936bae4f03fd"
#SRC_URI[c6accelbin.sha256sum] = "9fac3295c5c8533535e18f96c9a31c446f5a275798ace8c145f9ed51fa3e3ece"

LIC_FILES_CHKSUM = "file://docs/ReleaseNotes_C6Accel.txt;md5=a1adcd27b7152a4916afae75e2b33472"

SSRC_URI_append = " \
                  file://0001-soc-honour-buildsystem-CFLAGS-and-LDFLAGS-when-set.patch \
                 "

PV = "1_01_00_07"


CFLAGS += "-fPIC"

