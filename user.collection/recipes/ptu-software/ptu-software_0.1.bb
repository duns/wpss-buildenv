DESCRIPTION = "Software and configuration package for WPSS phase2"
PACKAGES ="${PN}"
PR="r0"
RDEPENDS="sed"

#FILES_${PN} = "${bindir}/autologin"
FILES_${PN} += "/etc/* \
	       /etc/modutils/* \
	       /etc/profile.d/* \
	       /usr/local/bin/* \
	       /usr/local/sbin/* \
		"
#SRC_URI = "git://chpap.dyndns.org:20480/git/PTU-software.git;protocol=http \
SRC_URI = "git://10.8.1.6/git/ptu-software.git;tag=master;protocol=http \
"

#SRC_URI = "git://git@10.8.1.1/srv/git/PTU-software.git;protocol=ssh \

S = "${WORKDIR}/git"

#INITSCRIPT_PACKAGES="ptuforwarder startstopscripts"
#INITSCRIPT_NAME_ptuforwarder="ptu_forwarder"
#INITSCRIPT_PARAMS_ptuforwarder="defaults 99 1"
#INITSCRIPT_NAME_startstopscripts="startstopscripts"
#INITSCRIPT_PARAMS_startstopscripts="defaults 98 2"

INITSCRIPT_NAME="startstopscripts"
INITSCRIPT_PARAMS="defaults 98 2"
inherit autotools update-rc.d


do_compile () {
	make
	cd ${S}/conf
	mkimage -T script -C none -n 'WPSS phase2 FireSTORM' -d WPSS-FireSTORM-bootcmds.txt boot.scr
#        ${CC} ${CFLAGS} ${LDFLAGS} -o autologin autologin.c
}

do_install () {
	  install -d ${D}/etc/profile.d/
	  install -d ${D}/etc/modprobe.d/
	  install -d ${D}/etc/init.d/
	  install -d ${D}/usr/local/sbin/
	  install -d ${D}/etc/modutils/
	  install -m 0644 ${S}/conf/wpss-modules ${D}/etc/modutils/
	  install -m 0644 ${S}/conf/wpss-modules.conf ${D}/etc/modutils/
	  install -m 0644 ${S}/conf/libertas.conf ${D}/etc/modprobe.d/
	  install -m 0644 ${S}/conf/wpa_supplicant_wpss.conf ${D}/etc/
	  install -m 0755 ${S}/scripts/wpss-system.sh ${D}/usr/local/sbin/
	  install -m 0755 ${S}/scripts/startstopscripts ${D}/etc/init.d/
	  install -m 0755 ${S}/conf/pinouts.sh ${D}/etc/profile.d/
	  install -m 0755 ${S}/scripts/gpio_functions.sh ${D}/etc/profile.d/
	  install -d ${D}/boot/
	  install -m 0644 ${S}/conf/boot.scr ${D}/boot/
	  install -m 0644 ${S}/src/*/ptu_forwarder.conf  ${D}/etc/
	  oe_runmake  install DESTDIR=${D}
}

pkg_postinst_${PN} () {
	sed -i 's_#PermitEmptyPasswords no_PermitEmptyPasswords yes_' /etc/ssh/sshd_config 
#	sed 's_ttyS2_ttyO2_' /etc/inittab > inittab.tmp
#	mv inittab.tmp /etc/inittab
#	cp /boot/boot.scr /media/mmcblk0p1/
#	passwd -d root
	cp /etc/wpa_supplicant.conf /etc/wpa_supplicant.conf.orig
	cp /etc/network/interfaces /etc/network/interfaces.orig

	cp /etc/wpa_supplicant_wpss.conf /etc/wpa_supplicant.conf
	echo 'allow-hotplug wlan0
iface wlan0 inet dhcp
      pre-up ifconfig wlan0 up
      pre-up iwlist wlan0 scan
      pre-up wpa_supplicant -Dwext -iwlan0 -c/etc/wpa_supplicant.conf -B
      down killall wpa_supplicant
	' >> /etc/network/interfaces
	init q
	update-rc.d ptu_forwarder defaults 99 1
	update-rc.d commandserver defaults 99 1
	update-modules
}

pkg_postrm_${PN}() {
	mv /etc/wpa_supplicant.conf.orig /etc/wpa_supplicant.conf
	mv /etc/network/interfaces.orig /etc/network/interfaces
	update-rc.d ptu_forwarder remove
	update-rc.d commandserver remove
	update-modules
}
#pkg_prerm_${PN} () {
#}


