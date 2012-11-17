DESCRIPTION="TCP streaming video source"
PR="r1"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://README;md5=d41d8cd98f00b204e9800998ecf8427e"

DEPENDS="boost \
         gstreamer"

#SRC_URI="file:///home/kostas/workspace/${PN}-${PV}.tar.gz"
#SRC_URI[archive.md5sum]="de370b6148434f43ce02ddbfc93d709c"

#SRCREV = HEAD
#SRCREV = "1357cfd11ccc8dc20438b6bf0fd84cd768c29416"
SRCREV =  "${AUTOREV}"
SRC_URI = "git://github.com/chpap/ptu-software.git;branch=develop;protocol=git \
	file://videosrcloop.sh \
	file://videosource.service "

S = "${WORKDIR}/git/src/videosource"

inherit autotools

inherit  systemd
SYSTEMD_PACKAGES="${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd="videosource.service"
SYSTEMD_AUTO_ENABLE_${PN}-systemd = "disable"

do_install_append () {
        install -d ${D}${bindir}
        install -m 0755 ${WORKDIR}/videosrcloop.sh ${D}${bindir}
}

#pkg_postinst_${PN}-systemd_append () {
# if [ x"$D" = "x" ]; then
#	systemctl disable videosource.service
#        # Actions to carry out on the device go here
#else
#        exit 1
#fi
#
#}
