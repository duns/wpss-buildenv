DESCRIPTION="TCP streaming video source"
PR="r0"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://README;md5=d41d8cd98f00b204e9800998ecf8427e"

DEPENDS="boost \
         gstreamer"

#SRC_URI="file:///home/kostas/workspace/${PN}-${PV}.tar.gz"
#SRC_URI[archive.md5sum]="de370b6148434f43ce02ddbfc93d709c"

#SRCREV = HEAD
#SRCREV = "1357cfd11ccc8dc20438b6bf0fd84cd768c29416"
SRCREV =  "${AUTOREV}"
SRC_URI = "git://github.com/chpap/ptu-software.git;branch=develop;protocol=git "

S = "${WORKDIR}/git/src/videosource"

inherit autotools
