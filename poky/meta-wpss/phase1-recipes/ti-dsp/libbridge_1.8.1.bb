SECTION = "libs"
PRIORITY = "optional"
DESCRIPTION = "Texas Instruments MPU/DSP Bridge libraries."
LICENSE = "LGPL"
PR = "r1"
RDEPENDS = "kernel-module-mailbox kernel-module-bridgedriver"

PACKAGES = "${PN} ${PN}-dbg ${PN}-dev ${PN}-samples"
FILES_${PN} += "${libdir}/lib*.so.* ${libdir}/lib*.so"
FILES_${PN}-dev += "${includedir} ${libdir}/lib*.a"
FILES_${PN}-samples += "/dspbridge"
FILES_${PN}-dbg += "/dspbridge/.debug"

TAG="${PV}"
SRC_URI = "git://gitorious.org/vjaquez-beagleboard/libbridge.git;protocol=git;tag=${TAG}"

S = "${WORKDIR}/git"

do_compile() {
        oe_runmake
}

do_stage() {
	oe_libinstall -so -C ${S}/libbridge libbridge ${STAGING_LIBDIR}
	oe_libinstall -so -C ${S}/libqos libqos ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/dspbridge
	install -m 0644 ${S}/inc/*.h ${STAGING_INCDIR}/dspbridge/
}

do_install() {
	oe_runmake DESTDIR=${D} install
}
