# INTRODUCTION
Initial RAM Filesystem.  
This is basically a compressed CPIO archive.  

CPIO is an old UNIX archive format, but is easier to decode and so requires less code in the kernel.  

Kernel support for Initramfs is enabled by the following kernel configuration.  
`CONFIG_BLK_DEV_INITRD`  

NB: Not all bootloaders have the facility to load a seperate ramdisk. UBOOT fortunatly has this capability.

An initramfs can be embedded directly into the kernel at compile time. This is a solution for bootloaders that do not support booting linux with an external initramfs.  
To enable this set the kernel configuration `CONFIG_INITRAMFS_SOURCE` to the full path of a compressed cpio archive of your iitramfs.  


## DEVICE TABLE
A device table is a text file that lists the files, directories, device nodes, and  links that go into an archive or filesystem image.  
At compile time for a kernel, you can specify a path to a device table file with the `CONFIG_INITRAMFS_SOURCE` configuration.  

There is also a kernel source script that you can use to generate an initramfs.cpio archive directly by specifying a device table file as one of its parameters. this script is called `gen_initramfs.sh`
