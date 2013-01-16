DESCRIPTION = "Additional files"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"

#PV = "${DISTRO_VERSION}"
PR = "r1"
PACKAGE_ARCH = "${MACHINE_ARCH}"



#do_compile() {
#}

SRC_URI += " file://streamscripts.tgz"


do_install () {
	install -d ${D}/home/root/streamscripts/sdp
        install -m 0755  ${WORKDIR}/streamscripts/*.sh  ${D}/home/root/streamscripts
        install -m 0644  ${WORKDIR}/streamscripts/sdp/*  ${D}/home/root/streamscripts/sdp
}

FILES_${PN} = "/home/root/* \
	"

#CONFFILES_${PN} += "${sysconfdir}/opkg/base-feed.conf \
#					${sysconfdir}/opkg/${MACHINE_ARCH}-feed.conf \
#					${sysconfdir}/opkg/noarch-feed.conf \
#					"

