DESCRIPTION="Configuration through USB mass storage"
PR="r1"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"
PACKAGES += " ${PN}-systemd"

DEPENDS_${PN} += " bzip2 dosfstools"
RDEPENDS_${PN} += " rsync "
RDEPENDS_${PN}-systemd += " ${PN} rsync "


SRC_URI = " file://fstab \
	    file://g_mass_storage.conf \
	    file://config-fs-storage.service \
	"

#S = "${WORKDIR}/git/src/videosource"

#inherit  systemd
#SYSTEMD_PACKAGES="${PN}-systemd"
#SYSTEMD_SERVICE_${PN}-systemd="config-fs-storage.service"
#SYSTEMD_AUTO_ENABLE_${PN}-systemd = "disable"

FILES_${PN} += " ${sysconfdir}/fstab.orig /mnt/config ${sysconfdir}/modprobe.d/g_mass_storage.conf "
FILES_${PN}-systemd += " ${systemd_unitdir}/system/config-fs-storage.service "
CONFFILES_${PN} += " ${sysconfdir}/modprobe.d/g_mass_storage.conf" 
#CONFFILES_${PN} += " ${sysconfdir}/fstab.orig "
DEPENDS_${PN} += " bzip2"
RDEPENDS_${PN} += " bzip2"

do_compile() {
	dd if=/dev/zero bs=512 count=32768 of=blankfilesystem.img
	dd if=/dev/zero bs=512 count=32760 of=part.img
	/bin/echo -e  'x\ns\n8\nh\n16\nc\n256\nr\nc\nn\np\n1\n\n\nt\nb\nw\n' | fdisk blankfilesystem.img
	mkfs.msdos -n WPSS-CONFIG  part.img
	dd if=blankfilesystem.img of=filesystem.img bs=512 count=8
	dd if=part.img >> filesystem.img
	rm -f part.img blankfilesystem.img
	rm -f filesystem.img.bz2
	bzip2 filesystem.img
}


do_install() {
        install -d ${D}${sysconfdir}
        install -m 0644 ${WORKDIR}/fstab ${D}${sysconfdir}/fstab.orig
        install -d ${D}/mnt/config
        install -d ${D}/var/local/config-fs-storage
        install -m 0644 ${S}/filesystem.img.bz2 ${D}/var/local/config-fs-storage
        install -d ${D}${sysconfdir}/modprobe.d
	install -m 0644 ${WORKDIR}/g_mass_storage.conf  ${D}${sysconfdir}/modprobe.d/g_mass_storage.conf
	install -d ${D}${systemd_unitdir}/system
	install -m 0644 ${WORKDIR}/config-fs-storage.service  ${D}${systemd_unitdir}/system/config-fs-storage.service
}

pkg_prerm_${PN}_prepend () {

	cp "$D"${sysconfdir}/fstab.orig "$D"${sysconfdir}/fstab
	touch  "$D"/var/local/config-fs-storage/filesystem.img.bz2
 if [ x"$D" = "x" ]; then
	TMPDIR=/tmp/"$RANDOM"
	mkdir "$TMPDIR"
	mount -o bind / "$TMPDIR"
	cp ${sysconfdir}/fstab.orig "$TMPDIR"/${sysconfdir}/fstab
	rm -f "$TMPDIR"/${sysconfdir}/fstab.orig
	umount "$TMPDIR"

 fi
	true
}
pkg_postinst_${PN}_prepend () {
	
	RND="$RANDOM"
	cp "$D"${sysconfdir}/fstab "$D"${sysconfdir}/fstab."$RND"-tmp
	mv "$D"${sysconfdir}/fstab.orig "$D"${sysconfdir}/fstab 
	mv "$D"${sysconfdir}/fstab."$RND"-tmp "$D"${sysconfdir}/fstab.orig 
	[ ! -e "$D"/var/local/config-fs-storage/filesystem.img ] && bzip2 -d -c  "$D"/var/local/config-fs-storage/filesystem.img.bz2 > "$D"/var/local/config-fs-storage/filesystem.img
	true
}
pkg_postinst_${PN}-systemd_append () {
 if [ x"$D" = "x" ]; then
	systemctl enable config-fs-storage.service
else
        exit 1
fi

}
