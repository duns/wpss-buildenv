# PIXHAWK desktop image
# Includes all packages and setting for the PIXHAWK system

require pixhawk-desktop-image.bb

IMAGE_INSTALL += " \
   dsplink-module \
   ti-cmemk-module \
   "

export IMAGE_BASENAME = "pixhawk-desktop-dsp-image"
