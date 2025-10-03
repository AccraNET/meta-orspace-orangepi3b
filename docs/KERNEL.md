# VARIABLES
## KERNEL_IMAGETYPE
The type of kernel to build for a device, usually set by the machine configuration files and defaults to “zImage”. This variable is used when building the kernel and is passed to make as the target to build.  

## KBUILD_DEFCONFIG
When used with the kernel-yocto class, specifies an “in-tree” kernel configuration file for use during a kernel build.

Typically, when using a defconfig to configure a kernel during a build, you place the file in your layer in the same manner as you would place patch files and configuration fragment files (i.e. “out-of-tree”). However, if you want to use a defconfig file that is part of the kernel tree (i.e. “in-tree”), you can use the KBUILD_DEFCONFIG variable and append the KMACHINE variable to point to the defconfig file.  

## KERNEL_DEVICETREE



# CLASSES
## kernel-devicetree
NB: This class is inherited by the "kernel" class.  

This class is responsible for devicetree generation.