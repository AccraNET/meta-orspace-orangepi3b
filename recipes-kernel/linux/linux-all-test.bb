require recipes-kernel/linux/linux-yocto.inc

# #####################################
# linux-rockchip.inc
# #####################################

inherit python3-dir

DEPENDS:append = " openssl-native lz4-native ${PYTHON_PN}-native"

# LINUX_VERSION_EXTENSION ?= "-rockchip-${LINUX_KERNEL_TYPE}"

PATCHPATH = "${THISDIR}/${BPN}_${LINUX_VERSION}"
# inherit auto-patch

INSANE_SKIP:${PN}-src += "buildpaths"

KCONFIG_MODE ?= "--alldefconfig"

# Make sure we use /usr/bin/env ${PYTHON_PN} for scripts
do_patch:append() {
	for s in `grep -rIl python ${S}/scripts`; do
		sed -i -e '1s|^#!.*python[23]*|#!/usr/bin/env ${PYTHON_PN}|' $s
	done
}

do_compile:prepend() {
	export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${STAGING_LIBDIR_NATIVE}
}

do_compile_kernelmodules:prepend() {
	export PKG_CONFIG_DIR="${STAGING_DIR_NATIVE}${libdir_native}/pkgconfig"
	export PKG_CONFIG_PATH="$PKG_CONFIG_DIR:${STAGING_DATADIR_NATIVE}/pkgconfig"
	export PKG_CONFIG_LIBDIR="$PKG_CONFIG_DIR"
	export PKG_CONFIG_SYSROOT_DIR=""
	export LD_LIBRARY_PATH=${LD_LIBRARY_PATH}:${STAGING_LIBDIR_NATIVE}
}

# Hack for rockchip style images
KERNEL_IMAGETYPES:append = \
	"${@' boot.img zboot.img' if d.getVar('ROCKCHIP_KERNEL_IMAGES') == '1' else ''}"
python () {
    if not d.getVar('KERNEL_DEVICETREE'):
        raise bb.parse.SkipPackage('KERNEL_DEVICETREE is not specified!')

    if d.getVar('ROCKCHIP_KERNEL_IMAGES'):
        # Use rockchip stype target, which is '<dts(w/o suffix)>.img'
        d.setVar('KERNEL_IMAGETYPE_FOR_MAKE', ' ' + d.getVar('KERNEL_DEVICETREE').replace('rockchip/', '').replace('.dtb', '.img'));
}

do_kernel_metadata:prepend() {
	# Force regenerating defconfig
	rm -f ${UNPACKDIR}/defconfig

	# Support defconfig fragments
	cd "${S}/arch/${ARCH}/configs"
	if [ ! -f "${KBUILD_DEFCONFIG}" ]; then
		DEFCONFIGS="rockchip_linux_defconfig ${RK_SOC_FAMILY}_linux.config"
		[ "${KBUILD_DEFCONFIGS}" ] || KBUILD_DEFCONFIGS="$DEFCONFIGS"

		echo "Merging ${KBUILD_DEFCONFIGS} into ${KBUILD_DEFCONFIG}..."
		cat ${KBUILD_DEFCONFIGS:-$CONFIGS} > ${KBUILD_DEFCONFIG} || true
	fi
	cd -
}

# Link rockchip style images
do_install:prepend() {
	for image in $(ls "${B}/" | grep ".img$"); do
		ln -rsf ${B}/${image} ${B}/arch/${ARCH}/boot/
	done

	if [ "${ROCKCHIP_KERNEL_COMPRESSED}" = "1" ]; then
		if [ -r "${B}/zboot.img" ]; then
			ln -rsf ${B}/zboot.img ${B}/arch/${ARCH}/boot/boot.img
		fi
	fi
}





# #####################################################################
# linux-rockchip_6.1.bb
# #####################################################################

# inherit local-git

SRCREV = "ea9e2a9344bfe7f1130dee8100173b6cb95445d2"
SRC_URI = " \
	git://github.com/JeffyCN/mirrors.git;protocol=https;nobranch=1;branch=kernel-6.1-2024_04_14; \
	file://${THISDIR}/files/cgroups.cfg \
"

LIC_FILES_CHKSUM = "file://COPYING;md5=6bc538ed5bd9a7fc9398086aedcd7e46"

KERNEL_VERSION_SANITY_SKIP = "1"
LINUX_VERSION ?= "6.1"

SRC_URI:append = " ${@bb.utils.contains('IMAGE_FSTYPES', 'ext4', \
		   'file://${THISDIR}/files/ext4.cfg', \
		   '', \
		   d)}"