DESCRIPTION = "Latest wireless drivers"
HOMEPAGE = "http://wireless.kernel.org/en/users/Download"
SECTION = "kernel/modules"
LICENSE = "GPL"
RDEPENDS = "wireless-tools"
PR = "r0"

COMPAT_WIRELESS_VERSION = "2.6.34.1"

SRC_URIDSDSDS = "http://wireless.kernel.org/download/compat-wireless-2.6/compat-wireless-${COMPAT_WIRELESS_VERSION}.tar.bz2 \
	"
SRC_URI = "http://www.orbit-lab.org/kernel/compat-wireless-2.6-stable/v2.6.34/compat-wireless-2.6.34.1.tar.bz2 \
	"

TAG = "${@'${COMPAT_WIRELESS_VERSION}'.replace('-', '')}"
PV = "0.${TAG}"

S = "${WORKDIR}/compat-wireless-${COMPAT_WIRELESS_VERSION}"

inherit module

EXTRA_OEMAKE = "KLIB_BUILD=${STAGING_KERNEL_DIR} KLIB=${D}"

do_configure_append() {
	sed -i "s#@./scripts/update-initramfs## " Makefile
}

do_install() {
	oe_runmake DEPMOD=echo DESTDIR="${D}" INSTALL_MOD_PATH="${D}" LDFLAGS="" install-modules
}

SRC_URI[md5sum] = "80ceb675aeba45847258770441352af6"
SRC_URI[sha256sum] = "2a83c8c37c775cee8f34f0328ed8be3fb128252a6da1adb65f419ba445d7937a"
