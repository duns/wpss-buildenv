
DESCRIPTION = "Texas Instruments Socket Nodes"
SECTION = "libs"
PRIORITY = "optional"
RDEPENDS = "kernel-module-dspbridge kernel-module-bridgedriver"

SRC_URI = "https://gforge.ti.com/gf/download/frsrelease/170/1399/tiopenmax-${PV}.tar.gz"

S = "${WORKDIR}/tiopenmax-${PV}"

do_compile_append () {
	./TI-OMX-Sample-Firmware-0.3.5-Linux-x86-Install 
}

do_compile () {
	   # do nothing
}

do_install () {
	install -d ${D}/lib/dsp
	install -m 0644 ${S}/lib/dsp/* ${D}/lib/dsp
}

FILES_${PN} += "/lib/dsp"
