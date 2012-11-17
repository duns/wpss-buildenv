FILESEXTRAPATHS_prepend := "${THISDIR}/files:"
PRINC = "2"
pkg_postinst_${PN}-systemd_append() {
OPTS=""
if [ -n "$D" ]; then
    OPTS="--root=$D"
fi
systemctl $OPTS enable ntpdate.service
if [ -z "$D" -a ${SYSTEMD_AUTO_ENABLE} = "enable" ]; then
    systemctl start ntpdate.service
fi
}

