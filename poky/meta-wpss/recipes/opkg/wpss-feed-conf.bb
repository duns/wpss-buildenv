DESCRIPTION = "Configuration files for online package repositories aka feeds"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/LICENSE;md5=3f40d7994397109285ec7b81fdeb3b58"
REPO_URI = "http://wpss-opkg-repo.wpss-vpn"

#PV = "${DISTRO_VERSION}"
PR = "r1"
PACKAGE_ARCH = "${MACHINE_ARCH}"

FEED_BASEPATH ?= "ipk/"
BASE_ARCH ?= "armv7a-vfp-neon"


do_compile() {
	mkdir -p ${S}/${sysconfdir}/opkg

#	for feed in base ; do
#		  echo "src/gz ${feed} ${REPO_URI}/${FEED_BASEPATH}${feed}" > ${S}/${sysconfdir}/opkg/${feed}-feed.conf
#	done
	echo "src/gz base ${REPO_URI}/${FEED_BASEPATH}${BASE_ARCH}" > ${S}/${sysconfdir}/opkg/base-feed.conf
	echo "src/gz no-arch ${REPO_URI}/${FEED_BASEPATH}all" > ${S}/${sysconfdir}/opkg/noarch-feed.conf
echo "src/gz ${MACHINE_ARCH} ${REPO_URI}/${FEED_BASEPATH}${MACHINE_ARCH}" >  ${S}/${sysconfdir}/opkg/${MACHINE_ARCH}-feed.conf
		
}


do_install () {
	install -d ${D}${sysconfdir}/opkg
	install -m 0644  ${S}/${sysconfdir}/opkg/* ${D}${sysconfdir}/opkg/
}

FILES_${PN} = "${sysconfdir}/opkg/base-feed.conf \
					${sysconfdir}/opkg/${MACHINE_ARCH}-feed.conf \
					${sysconfdir}/opkg/noarch-feed.conf \
					"

CONFFILES_${PN} += "${sysconfdir}/opkg/base-feed.conf \
					${sysconfdir}/opkg/${MACHINE_ARCH}-feed.conf \
					${sysconfdir}/opkg/noarch-feed.conf \
					"

# Get rid of opkg-collateral
#RCONFLICTS_${PN} = "opkg-collateral"
RREPLACES_${PN} = "opkg-collateral"
RPROVIDES_${PN} = "opkg-collateral"

RRECOMMENDS_${PN} += "opkg"


