DESCRIPTION="Video streaming source prototype"
PR="r0"

DEPENDS="boost \
         gstreamer"

#SRC_URI="file:///home/kostas/workspace/${PN}-${PV}.tar.gz"
#SRC_URI[archive.md5sum]="de370b6148434f43ce02ddbfc93d709c"

SRC_URI = "git://github.com/chpap/ptu-software.git;branch=develop;protocol=git "

S = "${WORKDIR}/git/src/gst-testsource"

inherit autotools
