DESCRIPTION = "Software and configuration package for WPSS phase2"
PACKAGES ="${PN}"
PR="r0"
RDEPENDS="sed"

#FILES_${PN} = "${bindir}/autologin"
SRC_URI = "git://chpap.dyndns.org/git/PTU-software.git;protocol=http \
	              "

S = "${WORKDIR}"

do_compile () {
	mkimage -T script -C none -n 'WPSS phase2 FireSTORM' -d WPSS-FireSTORM-bootcmds.txt boot.scr
#        ${CC} ${CFLAGS} ${LDFLAGS} -o autologin autologin.c
}

#do_install () {
#	  install -d ${D}${bindir}/
#	  install -m 0755 ${WORKDIR}/autologin ${D}${bindir}/
#}

pkg_postinst_${PN} () {
	sed 's_ttyS2_ttyO2_' /etc/inittab > inittab.tmp
	mv inittab.tmp /etc/inittab
	passwd -d root
	rm inittab.tmp
}

pkg_prerm_${PN} () {
}


