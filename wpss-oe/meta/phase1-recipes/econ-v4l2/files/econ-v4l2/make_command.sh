if [ -z $1 ]; then
	echo ""
	echo "Please select any one of the parameter as first argument in this shell script"
	echo " clean                               = Clean the build environment for you"
	echo " e-CAM50_OMAP_GSTIX                  = Build camera driver for gumstix 5MP using ov5642"
	echo " e-CAM50_OMAP35x_MICRON_JAMEERR      = Build camera driver for omap evm rev-G using ov5642"
	echo " e-CAM32_OMAP_GSTIX                  = Build camera driver for gumstix 3MP using ov3642"
	echo " e-CAM1M_CU35x                       = Build camera driver for omap evm rev-G using ov10630"
	echo ""
	echo "Example :"
	echo ". ./make_command.sh clean"
else
	if [ $1 = "clean" ]; then
		rm -rf `find ./ -name "*.o" -o -name "*.o.cmd" -o -name "*.ko"  \
			-o -name "*.sh.swp" -o -name ".tmp_versions" \
			-o -name "*.ko.cmd" -o -name "*.mod.c" \
			-o -name "*.order" -o -name "*.symvers" -o -name "*.c.swo"`
	else
		if [ Board/$1/auto_conf.h -nt auto_conf.h ]; then
			cp Board/$1/auto_conf.h ./
			touch Board/$1/auto_conf.h
		fi

		if [ Board/$1/auto_conf.h -ot auto_conf.h ]; then
			cp Board/$1/auto_conf.h ./
			touch Board/$1/auto_conf.h
		fi
		cp Board/$1/Makefile ./

		if [ $1 = "e-CAM50_OMAP_GSTIX" ]; then
			make 	KERNEL_PATH=/media/hdd1/svn/ov5642/OMAP35x/e-CAM50_OMAP_GSTIX/linux-omap3-2.6.34-r100 \
				CROSS_COMPILE=/media/hdd1/Open_embedded/overo-oe/tmp/sysroots/i686-linux/usr/armv7a/bin/arm-angstrom-linux-gnueabi-
		fi

		if [ $1 = "e-CAM50_OMAP35x_MICRON_JAMEERR" ]; then
			make KERNEL_PATH=/media/hdd1/svn/ov5642/OMAP35x/e-CAM50_CU5642_MOD/linux-02.01.03.11 \
				CROSS_COMPILE=/media/hdd1/svn/toolchain/arm-2008q1/bin/arm-none-linux-gnueabi-
		fi

		if [ $1 = "e-CAM32_OMAP_GSTIX" ]; then
			make 	KERNEL_PATH=/media/hdd1/svn/ov3640/omap35x/e-CAM32_OMAP_GSTIX/gumstix_ov3640_kernel/linux-omap3-2.6.34-r81 \
				CROSS_COMPILE=/media/hdd1/Open_embedded/overo-oe/tmp/sysroots/i686-linux/usr/armv7a/bin/arm-angstrom-linux-gnueabi-
		fi

		if [ $1 = "e-CAM1M_CU35x" ]; then
			make KERNEL_PATH=/media/hdd1/svn/ov5642/OMAP35x/e-CAM50_CU5642_MOD/linux-02.01.03.11 \
				CROSS_COMPILE=/media/hdd1/Open_embedded/overo-oe/tmp/sysroots/i686-linux/usr/armv7a/bin/arm-angstrom-linux-gnueabi-
		fi
	fi
 fi
