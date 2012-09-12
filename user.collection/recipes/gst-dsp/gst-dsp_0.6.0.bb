DESCRIPTION = "GStreamer plug-ins for TI's DSP algorithms."
AUTHOR = "Felipe Contreras <felipe.contreras@nokia.com>"
LICENSE = "LGPL"
DEPENDS = "gstreamer"
#RDEPENDS = "kernel-module-mailbox kernel-module-bridge"
RDEPENDS = "kernel-module-mailbox"
COMPATIBLE_MACHINE = "overo"
PR = "r2"

inherit pkgconfig

#TAG = "v0.6.0"
TAG = "v${PV}"
SRC_URI = "git://github.com/felipec/gst-dsp.git;protocol=git;tag=${TAG}"

S = "${WORKDIR}/git"

PACKAGES = "${PN} ${PN}-dbg"
FILES_${PN} += "${libdir}/gstreamer-0.10/libgstdsp.so"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug/"

do_unpack2() {
	echo "${TAG}" > ${S}/.version
}

do_compile() {
	oe_runmake V=
}

do_stage() {
	oe_libinstall -so libgstdsp ${STAGING_LIBDIR}/gstreamer-0.10
}

do_install() {
	install -d ${D}/${libdir}/gstreamer-0.10
	oe_libinstall -so libgstdsp ${D}/${libdir}/gstreamer-0.10
}

addtask unpack2 after do_unpack before do_patch
