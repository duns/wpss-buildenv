DESCRIPTION = "Software and configuration package for WPSS phase2"
PACKAGES ="${PN}"
PR="r0"
RDEPENDS="sed"

#FILES_${PN} = "${bindir}/autologin"
#SRC_URI = "git://chpap.dyndns.org:20480/git/PTU-software.git;protocol=http \
SRC_URI = "git://10.8.1.1/git/ptu-software.git;tag=master;protocol=http \
"

#SRC_URI = "git://git@10.8.1.1/srv/git/PTU-software.git;protocol=ssh \

S = "${WORKDIR}"

do_compile () {
	cd ${WORKDIR}/git/conf
	mkimage -T script -C none -n 'WPSS phase2 FireSTORM' -d WPSS-FireSTORM-bootcmds.txt boot.scr
#        ${CC} ${CFLAGS} ${LDFLAGS} -o autologin autologin.c
}

do_install () {
	  install -d ${D}/etc/
	  install -m 0755 ${WORKDIR}/git/conf/wpa_supplicant_wpss.conf ${D}/etc/
	  install -d ${D}/boot/
	  install -m 0755 ${WORKDIR}/git/conf/boot.scr ${D}/boot/
}

pkg_postinst_${PN} () {
	sed 's_ttyS2_ttyO2_' /etc/inittab > inittab.tmp
	mv inittab.tmp /etc/inittab
	passwd -d root
	cat /etc/wpa_supplicant_wpss.conf >> /etc/wpa_supplicant.conf
	cp /boot/boot.scr /media/mmcblk0p1/
	init q
}

#pkg_prerm_${PN} () {
#}


