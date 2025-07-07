# Recipe for building a linux kernel

# NOTES
# - In yocto, kenrnel configuration changes can be expressed using "configuration fragments"
# - Kernel modules built in tree are placed in a special package called "kernel-modules"
# - Use the KERNEL_MODULE_AUTOLOAD variable to list kernel modules that should be auto loaded on boot
#       - populates the /etc/modules-load.d/modname.conf appropriately.
# - Kernel configuration fragments in the OE build sytem need to end with ".cfg" extension
# - Yocto has a template kernel recipe for use
#       - meta-skeleton/recipes-kernel/linux/linux-yocto-custom.bb
#  

SRC_URI = "git://github.com/rockchip-linux/kernel.git;name=machine;branch=develop-6.1;protocol=https"
SRCREV_machine="cef907463922f246977813d803c440b5b2fb1765"

# Allows specifying an in-tree deconfig file to be used as the kernel configuration
# - To use a custom defconfig file, you need to place it in a file called "defconfig" in a files path directory of your recipe
KBUILD_DEFCONFIG = "rk3566"

LICENSE = "CLOSED"
