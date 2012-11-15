DESCRIPTION = "Texas Instruments Socket Nodes"
SECTION = "libs"
PRIORITY = "optional"
RDEPENDS = "kernel-module-bridgedriver"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r0"

BLOB = "TI_DSPbinaries_RLS23.i3.8-3.12-Linux-x86-Install"

SRC_URI = "https://gforge.ti.com/gf/download/frsrelease/352/3680/${BLOB}"

SRC_URI[md5sum] = "a1e3a6a57f9d20e549a40650c8681e6b"
SRC_URI[sha256sum] = "b77587334efd436a81846d3df87e576b4d30d8ce07e94d87be114b32d9aef2b1"

S = "${WORKDIR}"

do_compile_append () {
	chmod +x ./${BLOB}
	./${BLOB}  --mode silent --prefix ${S}
}

do_install () {
	install -d ${D}/lib/dsp
	install -m 0644 ${S}/binaries/baseimage.dof ${D}/lib/dsp
	install -m 0644 ${S}/binaries/*.dll64P ${D}/lib/dsp
}

FILES_${PN} += "/lib/dsp"
