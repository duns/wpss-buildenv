SECTION = "console/utils"
PRIORITY = "optional"
DESCRIPTION = "DSP samples"
COMPATIBLE_MACHINE = "beagleboard"
PR = "r2"

require ti-paths.inc
require ti-staging.inc

DEPENDS = "ti-cgt6x ti-dofftools"
RDEPENDS = "kernel-module-bridgedriver"

SRCREV = "8a9242a"

SRC_URI = "git://gitorious.org/vjaquez-beagleboard/dsp-samples.git;protocol=git;branch=master"

S = "${WORKDIR}/git"

PARALLEL_MAKE = ""

do_compile () {
	   oe_runmake NEW=1 DSP_TOOLS=${CODEGEN_INSTALL_DIR} DSP_DOFFBUILD=${DOFFTOOLS_INSTALL_DIR}
}

do_install() {
	oe_runmake DESTDIR=${D} install
}

FILES_${PN} += "/lib/dsp/ /dspbridge/"
FILES_${PN}-dbg += "/dspbridge/.debug"
