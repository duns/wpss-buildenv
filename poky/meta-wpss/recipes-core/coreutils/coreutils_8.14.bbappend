
#do_install_append() {
#	ln -s ${D}${libdir}/coreutils/libstdbuf.so ${D}${libexecdir}/coreutils/libstdbuf.so
#}

pkg_postinst_${PN}_append() {
	ln -s ${libdir}/coreutils/libstdbuf.so "$D"${libexecdir}/coreutils/libstdbuf.so
	
}
