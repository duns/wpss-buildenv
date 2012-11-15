SECTION = "console/utils"
PRIORITY = "optional"
DESCRIPTION = "Miscellaneous utilities for TI's C64x+ DSP"
AUTHOR = "Felipe Contreras <felipe.contreras@nokia.com>"
LICENSE = "LGPL"

PR = "r4"
PV = "2.0+gitr${SRCREV}"
SRCREV = "ca0d2b3"

SRC_URI = "git://github.com/felipec/dsp-tools.git;protocol=git;branch=master \
	   https://github.com/felipec/dsp-tools/raw/firmware/firmware/test.dll64P"

S = "${WORKDIR}/git"

do_compile () {
	   oe_runmake V=1
}

do_install() {
	oe_runmake DESTDIR=${D} install
	mkdir -p ${D}/lib/dsp/
	install -m 644 ${WORKDIR}/test.dll64P -D ${D}/lib/dsp/test.dll64P
}

FILES_${PN} += "/lib/dsp/"

SRC_URI[md5sum] = "7370a0fba5601355962d4dadb184fc2f"
SRC_URI[sha256sum] = "752152e1fa7f1a08f384c8d5644c0d0ea7d3f330cdd43c3f64003db378ef0a9e"
