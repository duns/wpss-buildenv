#!/bin/sh
#
# CMEM Allocation
#    1x5250000            Circular buffer
#    6x829440,1x691200    Video buffers (max D1 PAL)
#    1x345600             Underlying software components (codecs, etc.)
#    1x1                  Dummy buffer used during final flush

DSP_REGION_START_ADDR="0x90000000"
DSP_REGION_END_ADDR="0x94900000"

# Insert CMEM as all heap (only a portion will actually be used as such)
CMEMK_OPTS="phys_start=$DSP_REGION_START_ADDR phys_end=$DSP_REGION_END_ADDR allowOverlap=1 pools=1x10485760,6x2097152,1x691200,1x1048576,1x1"

rmmod cmemk 2>/dev/null

modprobe cmemk ${CMEMK_OPTS}

# insert DSP/BIOS Link driver
modprobe dsplinkk

# insert Local Power Manager driver
modprobe lpm_omap3530

# insert SDMA driver
modprobe sdmak 

