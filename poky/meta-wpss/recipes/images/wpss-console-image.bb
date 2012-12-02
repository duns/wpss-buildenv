DESCRIPTION = "WPSS console image"

inherit image
IMAGE_FEATURES += "package-management"
IMAGE_EXTRA_INSTALL ?= ""

DEPENDS = "virtual/kernel"

AUDIO_INSTALL = " \
  alsa-utils-aplay \
  alsa-utils-alsactl \
  alsa-utils-alsamixer \
  alsa-utils-amixer \
  alsa-utils-speakertest \
 "


BASE_INSTALL = " \
  ${MACHINE_EXTRA_RRECOMMENDS} \
  ${@base_contains("DISTRO_FEATURES", "bluetooth", "bluez4", "", d)} \
  avahi-systemd avahi-utils \
  base-files \
  base-passwd \
  bash \
  coreutils \
  dbus \
  devmem2 \
  man \	
  man-pages \
  memtester \
  netbase \
  ntp-systemd \
  net-tools \
  polkit \
  rsyslog-systemd \
  sed \
  shadow tinylogin \
  systemd systemd-compat-units \
  u-boot-mkimage \
  udev \
  udisks udisks-systemd \
  upower \
  update-alternatives-cworth \
  util-linux \
  which \
 "

FIRMWARE_INSTALL = " \
  linux-firmware-sd8686 \
  linux-firmware-rtl8192cu \
  linux-firmware-rtl8192ce \
  linux-firmware-rtl8192su \
  linux-firmware-wl12xx \
 "
NETWORK_INSTALL = " \
  networkmanager \
  networkmanager-tests \
  rfkill \
  wireless-tools \
  ${@base_contains("DISTRO_FEATURES", "wifi", "iw wpa-supplicant", "", d)} \
 "

TOOLS_INSTALL = " \
  bzip2 \
  cpufrequtils \
  dosfstools \
  e2fsprogs \
  evtest \
  findutils \
  iputils \
  grep \
  gzip \
  htop \
  nano \
  openssh-ssh openssh-keygen openssh-scp openssh-sshd \
  sudo \
  systemd-analyze \
  tar \
  vim \
  wget \
  zip \
 "

IMAGE_INSTALL += " \
  ${BASE_INSTALL} \
  ${AUDIO_INSTALL} \
  ${FIRMWARE_INSTALL} \
  ${NETWORK_INSTALL} \
  ${ROOTFS_PKGMANAGE} \
  ${TOOLS_INSTALL} \
 "

# this section removes remnants of legacy sysvinit support
# for packages installed above
IMAGE_FILE_BLACKLIST += " \
                        /etc/init.d/NetworkManager \
                        /etc/init.d/avahi-daemon \
                        /etc/init.d/dbus-1 \
                        /etc/init.d/dnsmasq \
                        /etc/init.d/networking \
                        /etc/init.d/ntpd \
                        /etc/init.d/udev \
                        /etc/init.d/udev-cache \
                       "

remove_blacklist_files() {
	for i in ${IMAGE_FILE_BLACKLIST}; do
		rm -rf ${IMAGE_ROOTFS}$i
	done

}

MODULES_START_AT_BOOT= " ehci_hcd \
"

add_modules_at_boot() {
	mkdir -p ${IMAGE_ROOTFS}/etc/modules-load.d
	for i in ${MODULES_START_AT_BOOT}; do
		[ ! -e ${IMAGE_ROOTFS}/etc/modules-load.d/$i.conf ] && echo $i > ${IMAGE_ROOTFS}/etc/modules-load.d/$i.conf 
	done
	true

}
ROOTFS_POSTPROCESS_COMMAND =+ "remove_blacklist_files ; add_modules_at_boot ; "


LICENSE = "LGPLv2"
#PREFERRED_PROVIDER_virtual/kernel = "linux-wpss"
#PREFERRED_PROVIDER_virtual/kernel = "linux-omap3-econ"
#PREFERRED_VERSION_linux-libc-headers = "2.6.34"
#PREFERRED_VERSION_gcc-cross-sdk = "4.3.3"
#PREFERRED_VERSION_ti-linuxutils = "3_22_00_02"


IMAGE_INSTALL += " \
  kernel-module-twl4030-pwrbutton \
  kernel-module-gpio-keys \
  libgsm \
  gst-ffmpeg \
  gst-plugins-base-meta \
  gst-plugins-good-meta \
  gst-plugins-ugly-meta \
  gst-plugins-bad-meta \
  gst-plugins-base-apps \
  gst-plugins-good-apps \
  gst-plugins-ugly-apps \
  task-native-sdk \
  pkgconfig \
  libdc1394 \
  grep \
  git \
  ncurses-dev \
  nano \
  vim \
  screen \
  lighttpd \
  lighttpd-module-fastcgi \
  php \
  php-cgi \
  php-cli \
  php-pear \
  openvpn-systemd \
  v4l-utils \
  boost \
  linphonec-systemd \
  ti-linuxutils \
  gstreamer-ti-3730 \
  usbutils \
  tcpvideosource-systemd \
  busybox \
ptu-forwarder-dev \
  ptu-software-systemd \
ptu-forwarder-systemd \
 wpssconf-systemd \
libertas-wpss-config-systemd \
wlan-network-connected-systemd  \
wpss-pwm \
mtd-utils \
wpss-feed-conf \
cronie-systemd \
watchdog \
wpss-vpn-keys-systemd \
config-fs-storage-systemd \
tzdata \
 "
#  gst-omapfb \
#  gsl-dev \
#  gst-rtsp \
#  task-gstreamer-ti \
#  linux-input \

export IMAGE_BASENAME = "wpss-console-image"
