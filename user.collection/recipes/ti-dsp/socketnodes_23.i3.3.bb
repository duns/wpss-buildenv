DESCRIPTION = "Texas Instruments Socket Nodes"
SECTION = "libs"
PRIORITY = "optional"
RDEPENDS = "kernel-module-bridgedriver"
PACKAGE_ARCH = "${MACHINE_ARCH}"

PR = "r1"

BLOB = "DSPbinaries-3.09-Linux-x86-Install"

SRC_URI = "https://gforge.ti.com/gf/download/frsrelease/285/3260/${BLOB}"

SRC_URI[md5sum] = "0d81872466aade5fa5f68d3b69d5704c"
SRC_URI[sha256sum] = "780d63c08ced0d8cdca041b39de65d8f3e47263757777510b89b864c6a44b721"

S = "${WORKDIR}"

do_compile_append () {
	chmod +x ./${BLOB}
	./${BLOB}  --mode console --prefix ${S}
}

do_install () {
	install -d ${D}/lib/dsp
	install -m 0644 ${S}/Binaries/baseimage.dof ${D}/lib/dsp
	install -m 0644 ${S}/Binaries/*.dll64P ${D}/lib/dsp
}

FILES_${PN} += "/lib/dsp"
