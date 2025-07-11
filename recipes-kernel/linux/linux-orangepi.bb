SUMMARY = "An example kernel recipe that uses the linux-yocto and oe-core"
# linux-yocto-custom.bb:
#
#   kernel classes to apply a subset of yocto kernel management to git
#   managed kernel repositories.
#
#   To use linux-yocto-custom in your layer, copy this recipe (optionally
#   rename it as well) and modify it appropriately for your machine. i.e.:
#
#     COMPATIBLE_MACHINE:yourmachine = "yourmachine"
#
#   You must also provide a Linux kernel configuration. The most direct
#   method is to copy your .config to files/defconfig in your layer,
#   in the same directory as the copy (and rename) of this recipe and
#   add file://defconfig to your SRC_URI.
#
#   To use the yocto kernel tooling to generate a BSP configuration
#   using modular configuration fragments, see the yocto-bsp and
#   yocto-kernel tools documentation.
#
# Warning:
#
#   Building this example without providing a defconfig or BSP
#   configuration will result in build or boot errors. This is not a
#   bug.
#
#
# Notes:
#
#   patches: patches can be merged into to the source git tree itself,
#            added via the SRC_URI, or controlled via a BSP
#            configuration.
#
#   defconfig: When a defconfig is provided, the linux-yocto configuration
#              uses the filename as a trigger to use a 'allnoconfig' baseline
#              before merging the defconfig into the build. 
#
#              If the defconfig file was created with make_savedefconfig, 
#              not all options are specified, and should be restored with their
#              defaults, not set to 'n'. To properly expand a defconfig like
#              this, specify: KCONFIG_MODE="--alldefconfig" in the kernel
#              recipe.
#   
#   example configuration addition:
#            SRC_URI += "file://smp.cfg"
#   example patch addition (for kernel v4.x only):
#            SRC_URI += "file://0001-linux-version-tweak.patch"
#   example feature addition (for kernel v4.x only):
#            SRC_URI += "file://feature.scc"
#

require recipes-kernel/linux/linux-yocto.inc

# Override SRC_URI in a copy of this recipe to point at a different source
# tree if you do not want to build from Linus' tree.
SRC_URI = "git://github.com/orangepi-xunlong/linux-orangepi.git;name=machine;branch=orange-pi-6.1-rk35xx;protocol=https \
           file://0001-Makefile-not-supports-out-of-tree-builds.patch \
           "
# SRC_URI += "file://defconfig"

LINUX_VERSION ?= "6.1"
LINUX_VERSION_EXTENSION:append = "-custom"

KERNEL_IMAGETYPE = "Image"

# Modify SRCREV to a different commit hash in a copy of this recipe to
# build a different release of the Linux kernel.
# tag: v4.2 64291f7db5bd8150a74ad2036f1037e6a0428df2
SRCREV="fb528a6014381c12a129e4f5e33c8034d46ad25e"

PV = "${LINUX_VERSION}.43"

# Override COMPATIBLE_MACHINE to include your machine in a copy of this recipe
# file. Leaving it empty here ensures an early explicit build failure.
COMPATIBLE_MACHINE = "opi3b"
KBUILD_DEFCONFIG = "rockchip_linux_defconfig"

LICENSE = "CLOSED"

KERNEL_EXTRA_ARGS += "KCFLAGS='-Wno-error'"

KCONFIG_MODE = "alldefconfig"

EXTRA_OEMAKE += "V=1"

INSANE_SKIP:${PN}-src += "buildpaths"