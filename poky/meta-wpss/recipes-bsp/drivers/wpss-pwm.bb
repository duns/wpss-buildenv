DESCRIPTION = "WPSS PWM driver"
HOMEPAGE = "https://github.com/scottellis/omap3-pwm"
LICENSE = "BSD"
LIC_FILES_CHKSUM = "file://pwm.c;endline=28;md5=c8448052329697f9239ac2d919535901"
PR="r2"
DEPENDS = "bc"
RDEPENDS += " update-modules"

inherit module

SRCREV =  "${AUTOREV}"
SRC_URI = "git://github.com/chpap/ptu-software.git;branch=master;protocol=git \
	"

S = "${WORKDIR}/git/src/pwm-mod/"

do_compile() {
  unset CFLAGS CPPFLAGS CXXFLAGS LDFLAGS
  oe_runmake 'KERNELDIR=${STAGING_KERNEL_DIR}'
}

do_install() {
  install -d ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/${PN}
  install -m 0644 pwm${KERNEL_OBJECT_SUFFIX} ${D}${base_libdir}/modules/${KERNEL_VERSION}/kernel/drivers/${PN}
  install -d ${D}${bindir}
  install -m 0755 ${S}/tunes.sh ${D}${bindir}
  install -m 0755 ${S}/beep.sh ${D}${bindir}
  install -d ${D}${sysconfdir}/modules-load.d
  echo pwm > ${D}${sysconfdir}/modules-load.d/wpss-pwm.conf
  echo options pwm timers=8  >> ${D}${sysconfdir}/modules-load.d/wpss-pwm.conf
  install -d ${D}${sysconfdir}/modprobe.d
  echo options pwm timers=8  >> ${D}${sysconfdir}/modprobe.d/wpss-pwm.conf
}

pkg_postinst_append() {
	update-modules
}

FILES_${PN} += "${sysconfdir}/modules-load.d/wpss-pwm.conf ${bindir}/beep.sh  ${bindir}/tunes.sh ${sysconfdir}/modprobe.d/wpss-pwm.conf "
