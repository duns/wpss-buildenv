DESCRIPTION="Video streaming source prototype"
PR="r0"

DEPENDS="boost \
         gstreamer"

#SRC_URI="file:///home/kostas/workspace/${PN}-${PV}.tar.gz"
#SRC_URI[archive.md5sum]="de370b6148434f43ce02ddbfc93d709c"

#SRCREV = HEAD
SRCREV = "3a4bc604c1e280fb65ac4a99e0c5ec7f56745f2e"
SRC_URI = "git://github.com/chpap/ptu-software.git;branch=develop;protocol=git "

S = "${WORKDIR}/git/src/gst-testsource"

inherit autotools
