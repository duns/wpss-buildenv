DESCRIPTION = "Linux Driver for e-con cameras"
SECTION = "base"
PRIORITY = "optional"
LICENSE = "GPL"
RDEPENDS_${PN} = "kernel-${KERNEL_VERSION}"
DEPENDS = "virtual/kernel"

PR = "r1"

#INITSCRIPT_NAME = "robostix"
#INITSCRIPT_PARAMS = "defaults 10"

SRC_URI = "\
  file://econ-v4l2-src.tar.gz \
 "

S = "${WORKDIR}/econ-v4l2"

inherit module
# update-rc.d

do_install() {	
	install         -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/media/video/
	install -m 0644 ${WORKDIR}/econ-v4l2/v4l2_driver.ko ${D}${base_libdir}/modules/${KERNEL_VERSION}/media/video/
#
#	install         -d ${D}${sysconfdir}/init.d/
#	install -m 0755 ${WORKDIR}/robostix.init ${D}${sysconfdir}/init.d/robostix
}

PACKAGES = "${PN}"

FILES_${PN} = "${base_libdir}/modules/"
# FILES_${PN} += "${sysconfdir}/"

