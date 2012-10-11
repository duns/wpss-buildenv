SECTION = "libs"
PRIORITY = "optional"
DESCRIPTION = "Texas Instruments MPU/DSP Bridge libraries."
LICENSE = "LGPL"
PR = "r1"
RDEPENDS = "kernel-module-mailbox kernel-module-bridgedriver"

DEFAULT_PREFERENCE = "-1"

PACKAGES = "${PN} ${PN}-dbg ${PN}-dev ${PN}-samples"
FILES_${PN} += "${libdir}/lib*.so.* ${libdir}/lib*.so"
FILES_${PN}-dev += "${includedir} ${libdir}/lib*.a"
FILES_${PN}-samples += "/dspbridge"
FILES_${PN}-dbg += "/dspbridge/.debug"

SRCREV = "31c7e2"
SRC_URI = "git://dev.omapzoom.org/pub/scm/tidspbridge/userspace-dspbridge.git;protocol=git \
	file://gnu_hash.patch;apply=yes"

S = "${WORKDIR}/git"

do_compile() {
        oe_runmake -C source .api 'CROSS_COMPILE=${CROSS_DIR}/bin/${TARGET_PREFIX}'
}

do_stage() {
	oe_libinstall -so -C ${S}/source/mpu_api/src/bridge libbridge ${STAGING_LIBDIR}
	oe_libinstall -a -C ${S}/source/mpu_api/src/qos libqos ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/dspbridge
	install -m 0644 ${S}/source/mpu_api/inc/*.h ${STAGING_INCDIR}/dspbridge/
}

do_install() {
	oe_libinstall -so -C ${S}/source/mpu_api/src/bridge libbridge ${D}/${libdir}
	oe_libinstall -a -C ${S}/source/mpu_api/src/qos libqos ${D}//${libdir}
}
