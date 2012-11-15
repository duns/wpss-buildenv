FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PRINC = "2"
pkg_postinst_${PN}-systemd_append() {
	systemctl enable ntpdate.service
}

