DESCRIPTION = "Software and configuration package for WPSS phase2"
#PACKAGES ="${PN}"
PACKAGES = " ptu-forwarder wpssconf ptu-forwarder-dev libertas-wpss-config wlan-network-connected"
PR="r3"
DEPENDS="start-stop-daemon"
RDEPENDS="start-stop-daemon"
LICENSE = "LGPLv2"

#FILES_${PN} = "${bindir}/autologin"
#FILES_${PN} = ""
#FILES_${PN} = " ${sbindir}/commandserver \
#		${sysconfdir}/commandserver.conf \
#		${sysconfdir}/init.d/commandserver \
#		"

FILES_libertas-wpss-config = "${sysconfdir}/modprobe.d/* \
		"

FILES_wlan-network-connected = " "

FILES_wpssconf = "${sysconfdir}/profile.d/* \
		${sbindir}/wpss-system.sh \
		/boot/boot.scr \
		${sysconfdir}/init.d/wpssconf \
		"

FILES_ptu-forwarder = "${bindir}/PTU_forwarder_TCP_RS232 \
		${sysconfdir}/ptu_forwarder.conf \
		${sysconfdir}/init.d/ptu_forwarder \
		"

FILES_ptu-forwarder-dev = " /usr/src/* "

SRCREV =  "${AUTOREV}"
SRC_URI = "git://github.com/chpap/ptu-software.git;branch=master;protocol=git \
	file://ptu_forwarder.service \
	file://wpssconf.service \
	file://libertas-module.service \
	file://wlan-network-connected.service \
	file://wpss-system.sh \
	file://ptu_forwarder.conf \
	file://blacklist.conf \
	file://libertas.conf \
	file://timezone.sh \
"
LIC_FILES_CHKSUM = "file://README.md;md5=0cf88e25d6f970bc6d959af1763e288c"

S = "${WORKDIR}/git"

inherit systemd

INITSCRIPT_PACKAGES="ptu-forwarder wpssconf"
INITSCRIPT_NAME_ptu-forwarder="ptu_forwarder"
INITSCRIPT_PARAMS_ptu-forwarder="defaults 99 1"
INITSCRIPT_NAME_wpssconf="wpssconf"
INITSCRIPT_PARAMS_wpssconf="defaults 98 2"

SYSTEMD_PACKAGES="ptu-forwarder-systemd wpssconf-systemd libertas-wpss-config-systemd  wlan-network-connected-systemd"
#SYSTEMD_SERVICE_${PN}-systemd="commandserver.service"
SYSTEMD_SERVICE_ptu-forwarder-systemd="ptu_forwarder.service"
SYSTEMD_SERVICE_wpssconf-systemd="wpssconf.service"
SYSTEMD_SERVICE_libertas-wpss-config-systemd="libertas-module.service"
SYSTEMD_SERVICE_wlan-network-connected-systemd="wlan-network-connected.service"

#INITSCRIPT_NAME="wpssconf"
#INITSCRIPT_PARAMS="defaults 98 2"
inherit autotools update-rc.d

DEPENDS_wlan-network-connected-systemd= " systemd-systemctl-native"
RDEPENDS_wlan-network-connected-systemd= " systemd"

do_compile () {
	cd ${S}/src/PTU_forwarder_TCP_RS232
	make
	cd ${S}/conf
	mkimage -T script -C none -n 'WPSS phase2 FireSTORM' -d WPSS-FireSTORM-bootcmds.txt boot.scr
#        ${CC} ${CFLAGS} ${LDFLAGS} -o autologin autologin.c
}

do_install () {
	cd ${S}/src/PTU_forwarder_TCP_RS232
	  oe_runmake  install DESTDIR=${D}/usr
	  install -d ${D}${sysconfdir}/profile.d/
	  install -d ${D}${sysconfdir}/init.d/
	  install -d ${D}${sbindir}
	  install -d ${D}${bindir}

#wpssconf
# needed to overcome slow loading in kernel 3.2
	  install -d ${D}/etc/modprobe.d/
	  install -m 0644 ${WORKDIR}/libertas.conf ${D}/etc/modprobe.d/
	  install -m 0644 ${WORKDIR}/blacklist.conf ${D}/etc/modprobe.d/
	  install -m 0644 ${S}/conf/wpa_supplicant_wpss.conf ${D}${sysconfdir}/
	  install -m 0755 ${WORKDIR}/wpss-system.sh ${D}${sbindir}/
	  install -m 0755 ${S}/scripts/wpssconf ${D}${sysconfdir}/init.d/
	  install -m 0755 ${WORKDIR}/timezone.sh ${D}${sysconfdir}/profile.d/
	  install -m 0755 ${S}/conf/pinouts.sh ${D}${sysconfdir}/profile.d/
	  install -m 0755 ${S}/scripts/gpio_functions.sh ${D}${sysconfdir}/profile.d/
	  install -d ${D}/boot/
	  install -m 0644 ${S}/conf/boot.scr ${D}/boot/
#ptu-forwarder
	  install -m 0644 ${WORKDIR}/ptu_forwarder.conf  ${D}${sysconfdir}/
#	  install -m 0644 ${S}/src/*/ptu_forwarder.conf  ${D}${sysconfdir}/
	  install -m 0755 ${S}/src/PTU_forwarder_TCP_RS232/ptu_forwarder  ${D}${sysconfdir}/init.d/
#commandserver
#	  install -m 0755 ${S}/src/commandserver/commandserver.conf ${D}${sysconfdir}/
#	  install -m 0755 ${S}/src/commandserver/commandserver-initscript ${D}${sysconfdir}/init.d/commandserver
}


#pkg_postinst_${PN}_append () {
#	sed 's_ttyS2_ttyO2_' /etc/inittab > inittab.tmp
#	mv inittab.tmp /etc/inittab
#	cp /boot/boot.scr /media/mmcblk0p1/
#	passwd -d root
#	cp /etc/wpa_supplicant.conf /etc/wpa_supplicant.conf.orig
#	cp /etc/network/interfaces /etc/network/interfaces.orig
#	systemctl enable ntpdate.service
#	cp /etc/wpa_supplicant_wpss.conf /etc/wpa_supplicant.conf
#	echo 'allow-hotplug wlan0
#iface wlan0 inet dhcp
#      pre-up ifconfig wlan0 up
#      pre-up iwlist wlan0 scan
#      pre-up wpa_supplicant -Dwext -iwlan0 -c/etc/wpa_supplicant.conf -B
#      down killall wpa_supplicant
#	' >> /etc/network/interfaces
#	init q
#	update-rc.d ptu_forwarder defaults 99 1
#}

#pkg_postrm_${PN}() {
#	mv /etc/wpa_supplicant.conf.orig /etc/wpa_supplicant.conf
#	mv /etc/network/interfaces.orig /etc/network/interfaces
#	update-rc.d ptu_forwarder remove
#}
#pkg_prerm_${PN} () {
#}


