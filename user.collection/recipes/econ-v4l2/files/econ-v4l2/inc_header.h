/**********************************************************************************************************
 *   Copyright (C) 2009 by e-con Systems.                                                                 *
 *   www.e-consystems.com                                                                                 *
 *                                                                                                        *
 *   This file is licensed under the terms of the GNU General Public License                              *
 *   version 2. This program is licensed "as is" without any warranty of any                              *
 *   kind, whether express or implied.                                                                    *
 *                                                                                                        *
 *                                                                                                        *
 *   PROJECT	           :        OMAP Camera development                                               *
 *   MODULE NAME           :                                                                              *
 *   MODULE VERSION        :        VER 3.0                                                               *
 *                                                                                                        *
 *                                                                                                        *
 *                                                                                                        *
 *   Version No	: 000-0001                                                          CODE_REV  : 0.0.1.1   *
 **********************************************************************************************************/

/*
 *==========================================================================================================
 *                                        REVISION HISTORY                                  
 *----------------------------------------------------------------------------------------------------------
 * CODE_REV  REASON FOR MODIFICATION                MODIFIED FUNCTION NAME  	            AUTHOR
 *----------------------------------------------------------------------------------------------------------
 * 
 * 0.0.1.0   ------------------------ Driver development ---------------------------- Ananthapadmanaban
 *
 * 3.0       Flash support added  
 *==========================================================================================================
 */
/*
 * include configuration code for selecting sensor and flash driver code
 */
#include "auto_conf.h"


/*
 * Define the Include header file Macro
 */
#define MODULE_NAME	"V4l2 driver module"
#define VERSION_NO	"6.371"

#define CONFIG_DRIVER
#define USE_KERNEL_THREAD
#define KERNEL_ARM_OMAP
#define KERNEL_ARM_OMAP3530
#define USE_KERNEL_MEMORY_MANAGE
#define CONFIG_KERNEL_ERR_INCLUDED

#include "resource/include/Headerfile.h"

/*
 * driver specific header files
 */

#include <media/v4l2-dev.h>
#include <mach/gpio.h>
#include <media/v4l2-ioctl.h>

/*
 * Include module specific code here
 */
#include "omap_camera_interface.h"
#include "isp.h"
#include "v4l2_driver_base.h"
#include "omap_v4l2.h"
#include "i2c.h"


/*
 * include the sensor file
 */
#if defined (CONFIG_USE_OV5642_SENSOR)
	#include "ov5642/sens_ov5642.h"
#elif defined (CONFIG_USE_OV3640_SENSOR)
	#include "ov3640/sens_ov3640.h"
#elif defined (CONFIG_USE_OV10630_SENSOR)
	#include "ov10630/sens_ov10630.h"
#endif

/*
 * include Flash related header file
 */

#if defined(CONFIG_USE_LM3553_FLASH)
	#include "lm3553/lm3553_flash.h"
#elif defined(CONFIG_USE_STCF03_FLASH)
	#include "stcf03/stcf03_flash.h"
#endif

/*
 * Include function protype here
 */

#include "fn_protype.h"
