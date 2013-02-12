DESCRIPTION="commandserver"
PR="r2"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://README;md5=d41d8cd98f00b204e9800998ecf8427e"

DEPENDS="boost \
         gstreamer"

#SRCREV = HEAD
#SRCREV = "4b34bf79070ee3524b2a28864883a1c1e9e3530f"
SRCREV =  "${AUTOREV}"
SRC_URI = "git://github.com/chpap/ptu-software.git;branch=develop;protocol=git \
	   file://commandserver.service"

S = "${WORKDIR}/git/src/commandserver"

inherit autotools

inherit  systemd
SYSTEMD_PACKAGES="${PN}-systemd"
SYSTEMD_SERVICE_${PN}-systemd="commandserver.service"
SYSTEMD_AUTO_ENABLE_${PN}-systemd = "enable"

FILES_${PN} += " ${sysconfdir}/commandserver.conf \
		${sysconfdir}/init.d/commandserver \
		${sbindir}/commandserver"
CONFFILES_${PN} += " ${sysconfdir}/commandserver.conf"

do_install () {
         install -d ${D}${sysconfdir}/init.d/
         install -d ${D}${sbindir}
         install -m 0755 ${S}/commandserver ${D}${sbindir}/
         install -m 0644 ${S}/config/commandserver.conf ${D}${sysconfdir}/
         install -m 0755 ${S}/scripts/commandserver-initscript ${D}${sysconfdir}/init.d/commandserver
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
