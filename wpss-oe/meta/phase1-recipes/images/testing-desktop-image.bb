# Personal testing console image

require recipes/images/omap3-desktop-image.bb

#PREFERRED_PROVIDER_virtual/kernel = "linux-omap3-econ"
#PREFERRED_VERSION_linux-libc-headers = "2.6.34"
#PREFERRED_VERSION_gcc-cross-sdk = "4.3.3"

# If you need additional packages by default, you can add them to the installation image here.
# This example installs everything to make OpenCV work on BeagleBoard and Gumstix
# with native support for Point Grey Firefly MV USB 2.0 machine vision cameras.
# (run "opencv-cvcap 0" to test, but to do so you have to change the line above
# to "require recipes/images/omap3-desktop-image.bb")
#
# It also contains GCC and binutils, so you can compile code on the board itself
# opencv \
# opencv-dev \
# opencv-bin \
# boost-dev \
#  gst-plugins-bad \
#  gst-ffmpeg \
#  gst-omapfb \
#  gst-plugins-base \
#  gst-plugins-good \
#  gst-plugins-ugly \
#  gstreamer-ti \

IMAGE_INSTALL += " \
  libgsm \
  linphone \
  linphonec \
  ti-linuxutils \
  gst-ffmpeg \
  gst-plugins-base-meta \
  gst-plugins-good-meta \
  gst-plugins-ugly-meta \
  gst-plugins-base-apps \
  gst-plugins-good-apps \
  gst-plugins-ugly-apps \
  gstreamer-ti \
  gst-omapfb \
  gsl-dev \
  task-native-sdk \
  task-gstreamer-ti \
  pkgconfig \
  libdc1394 \
  grep \
  dropbear \
  git \
  ncurses-dev \
  kernel-module-econ \
  nano \
  vim \
  screen \
  networkmanager \
  lighttpd \
  lighttpd \
  lighttpd-module-fastcgi \
  php \
  php-cgi \
 php-cli \
    php-pear \
  openvpn \
  ifplugd \
  v4l-utils \
 "

export IMAGE_BASENAME = "testing-desktop-image"
