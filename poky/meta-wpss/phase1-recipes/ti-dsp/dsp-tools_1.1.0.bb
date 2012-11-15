SECTION = "console/utils"
PRIORITY = "optional"
DESCRIPTION = "Bunch of DSP tools."
PR = "r1"
RDEPENDS = "kernel-module-dspbridge kernel-module-bridgedriver"

TAG="v${PV}"
SRC_URI = "git://gitorious.org/maemo-multimedia/dsp-tools.git;protocol=git;tag=${TAG} \
	   file:///dsp-manager"

inherit update-rc.d

INITSCRIPT_NAME = "dsp-manager"
INITSCRIPT_PARAMS = "start 70 S . stop 60 0 6 ."

S = "${WORKDIR}/git"

do_compile () {
	   oe_runmake V= SYSLOG=1
}

do_install() {
	oe_runmake DESTDIR=${D} install

	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/dsp ${D}${sysconfdir}/init.d
}
