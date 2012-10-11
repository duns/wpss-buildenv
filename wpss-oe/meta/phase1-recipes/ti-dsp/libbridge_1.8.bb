SECTION = "libs"
PRIORITY = "optional"
DESCRIPTION = "Texas Instruments MPU/DSP Bridge libraries."
LICENSE = "LGPL"
PR = "r0"
RDEPENDS = "kernel-module-dspbridge kernel-module-bridgedriver"

PACKAGES = "${PN} ${PN}-dbg ${PN}-dev"
FILES_${PN} = "${libdir}/lib*.so.* ${libdir}/lib*.so"
FILES_${PN}-dev = "${includedir} ${libdir}/lib*.a"

TAG="v${PV}"
SRC_URI = "git://dev.omapzoom.org/pub/scm/tidspbridge/userspace-dspbridge.git;protocol=git;tag=${TAG}"

S = "${WORKDIR}/git"
TARGET_CC_ARCH += "${LDFLAGS}"

do_compile() {
	unset CFLAGS
	oe_runmake -C source .api 'DEPOT=/tmp' 'MAKE=make -e'
}

do_stage() {
	oe_libinstall -so -C ${S}/source/target/lib libbridge ${STAGING_LIBDIR}
	oe_libinstall -so -a -C ${S}/source/target/lib libqos ${STAGING_LIBDIR}
	install -d ${STAGING_INCDIR}/dspbridge
	install -m 0644 ${S}/source/mpu_api/inc/*.h ${STAGING_INCDIR}/dspbridge/
}

do_install() {
	oe_libinstall -so -C ${S}/source/target/lib libbridge ${D}${libdir}
	oe_libinstall -so -a -C ${S}/source/target/lib libqos ${D}${libdir}
	install -d ${D}${includedir}/dspbridge
	install -m 0644 ${S}/source/mpu_api/inc/*.h ${D}${includedir}/dspbridge/
}
