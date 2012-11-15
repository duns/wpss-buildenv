DESCRIPTION = "A cross platform audio visualization library"
HOMEPAGE = "http://libvisual.sf.net"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "LGPL"
LIC_FILES_CHKSUM = "file://COPYING;md5=1b83fd9e43985ce0c01e7b2a65d6432c"

PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/libvisual/libvisual-${PV}.tar.gz \
           file://no-libdir.patch \
          "

inherit autotools pkgconfig gettext

SRC_URI[md5sum] = "f4e78547c79ea8a8ad111cf8b85011bb"
SRC_URI[sha256sum] = "0b4dfdb87125e129567752089e3c8b54cefed601eef169d2533d8659da8dc1d7"
